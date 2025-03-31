import { defineStore } from "pinia";
import { ref } from "vue";
import trainersService from "../services/TrainersService";

export const useTrainersStore = defineStore("trainers", () => {
	const trainers = ref([]);
	const loading = ref(false);

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
		changePassword
	};
});