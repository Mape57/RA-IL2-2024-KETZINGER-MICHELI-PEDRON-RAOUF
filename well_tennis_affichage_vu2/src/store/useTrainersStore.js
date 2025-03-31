import { defineStore } from "pinia";
import { ref } from "vue";
import trainersService from "../services/TrainersService";

export const useTrainersStore = defineStore("trainers", () => {
	const trainers = ref([]);
	const loading = ref(false);
	const hoursInitialized = ref(false);

	const fetchTrainers = async () => {
		try {
			loading.value = true;
			const response = await trainersService.getAllTrainers();
			trainers.value = response.data;
		} catch (error) {
			console.error("Error fetching trainers:", error);
		} finally {
			loading.value = false;
		}
	};

	const getTrainerById = (id) => {
		return trainers.value.find(trainer => trainer.id === id);
	};
	
	/**
	 * Fetch specific trainers by their IDs
	 * @param {Array} trainerIds - Array of trainer IDs to fetch
	 * @returns {Promise<Array>} - Array of trainer objects
	 */
	const fetchTrainersByIds = async (trainerIds) => {
		if (!trainerIds || trainerIds.length === 0) return [];
		
		try {
			loading.value = true;
			const fetchPromises = trainerIds.map(id => trainersService.getTrainerById(id));
			const responses = await Promise.all(fetchPromises);
			
			// Update the trainers in the store
			responses.forEach(response => {
				const trainerData = response.data;
				const index = trainers.value.findIndex(t => t.id === trainerData.id);
				
				if (index !== -1) {
					// Update existing trainer
					trainers.value[index] = trainerData;
				} else {
					// Add new trainer
					trainers.value.push(trainerData);
				}
			});
			
			return responses.map(r => r.data);
		} catch (error) {
			console.error("Error fetching trainers by IDs:", error);
			return [];
		} finally {
			loading.value = false;
		}
	};

	const createTrainer = async (trainerData) => {
		try {
			const response = await trainersService.createTrainer(trainerData);
			trainers.value.push(response.data);
			return response.data;
		} catch (error) {
			console.error("Error creating trainer:", error);
			throw error;
		}
	};

	const updateTrainer = async (id, trainerData) => {
		try {
			const response = await trainersService.updateTrainer(id, trainerData);
			const index = trainers.value.findIndex(trainer => trainer.id === id);
			if (index !== -1) {
				trainers.value[index] = response.data;
			}
			return response.data;
		} catch (error) {
			console.error("Error updating trainer:", error);
			throw error;
		}
	};

	const deleteTrainer = async (id) => {
		try {
			await trainersService.deleteTrainer(id);
			trainers.value = trainers.value.filter(trainer => trainer.id !== id);
			return true;
		} catch (error) {
			console.error("Error deleting trainer:", error);
			throw error;
		}
	};

	const resetPassword = async (email) => {
		try {
			const response = await trainersService.resetPassword(email);
			return response.data;
		} catch (error) {
			console.error("Error resetting password:", error);
			throw error;
		}
	};

	const changePassword = async (data) => {
		try {
			const response = await trainersService.changePassword(data);
			return response.data;
		} catch (error) {
			console.error("Error changing password:", error);
			throw error;
		}
	};

	// Méthode pour incrémenter le nombre d'heures d'un entraîneur
	const incrementTrainerHours = (trainerId, hours = 1) => {
		const trainerIndex = trainers.value.findIndex((trainer) => trainer.id === trainerId);
		if (trainerIndex !== -1) {
			if (!trainers.value[trainerIndex].nombreHeures) {
				trainers.value[trainerIndex].nombreHeures = 0;
			}
			trainers.value[trainerIndex].nombreHeures += hours;
			console.log(`Coach ${trainers.value[trainerIndex].name} ${trainers.value[trainerIndex].surname}: heures incrémentées de ${hours}, nouveau total: ${trainers.value[trainerIndex].nombreHeures}`);
		}
	};

	// Méthode pour décrémenter le nombre d'heures d'un entraîneur
	const decrementTrainerHours = (trainerId, hours = 1) => {
		const trainerIndex = trainers.value.findIndex((trainer) => trainer.id === trainerId);
		if (trainerIndex !== -1 && trainers.value[trainerIndex].nombreHeures) {
			trainers.value[trainerIndex].nombreHeures = Math.max(0, trainers.value[trainerIndex].nombreHeures - hours);
			console.log(`Coach ${trainers.value[trainerIndex].name} ${trainers.value[trainerIndex].surname}: heures décrémentées de ${hours}, nouveau total: ${trainers.value[trainerIndex].nombreHeures}`);
		}
	};

	// Méthode pour réinitialiser les heures d'un entraîneur
	const resetTrainerHours = (trainerId) => {
		const trainerIndex = trainers.value.findIndex((trainer) => trainer.id === trainerId);
		if (trainerIndex !== -1) {
			trainers.value[trainerIndex].nombreHeures = 0;
		}
	};

	// Méthode pour calculer et mettre à jour les heures d'un entraîneur basé sur la durée d'une session
	const updateTrainerHoursFromSession = (trainerId, startTime, endTime) => {
		if (!trainerId || !startTime || !endTime) return;
		
		// Calculer la durée en heures
		const start = new Date(`2000-01-01T${startTime}`);
		const end = new Date(`2000-01-01T${endTime}`);
		const durationHours = (end - start) / (1000 * 60 * 60);
		
		if (durationHours > 0) {
			incrementTrainerHours(trainerId, durationHours);
		}
	};

	// Méthode pour initialiser les heures des coachs en fonction des sessions
	const initializeTrainerHours = (sessions) => {
		if (!sessions || sessions.length === 0 || hoursInitialized.value) return;
		
		console.log("Initialisation des heures des coachs...");
		
		// Réinitialiser les heures de tous les coachs
		trainers.value.forEach(trainer => {
			if (trainer.nombreHeures === undefined) {
				trainer.nombreHeures = 0;
			} else {
				trainer.nombreHeures = 0;
			}
		});
		
		// Calculer les heures pour chaque coach en fonction des sessions
		sessions.forEach(session => {
			if (session.idTrainer && session.start && session.stop) {
				const trainerId = typeof session.idTrainer === 'object' ? session.idTrainer.id : session.idTrainer;
				
				if (trainerId) {
					const start = new Date(`2000-01-01T${session.start}`);
					const end = new Date(`2000-01-01T${session.stop}`);
					const durationHours = (end - start) / (1000 * 60 * 60);
					
					if (durationHours > 0) {
						incrementTrainerHours(trainerId, durationHours);
					}
				}
			}
		});
		
		hoursInitialized.value = true;
		console.log("Initialisation des heures des coachs terminée");
	};

	return {
		trainers,
		loading,
		fetchTrainers,
		fetchTrainersByIds,
		getTrainerById,
		createTrainer,
		updateTrainer,
		deleteTrainer,
		resetPassword,
		changePassword,
		initializeTrainerHours,
		resetTrainerHours,
		incrementTrainerHours,
		decrementTrainerHours,
		updateTrainerHoursFromSession
	};
});