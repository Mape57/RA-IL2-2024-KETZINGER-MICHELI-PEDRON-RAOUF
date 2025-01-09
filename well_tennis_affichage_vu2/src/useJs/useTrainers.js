// src/composables/useTrainers.js
import { ref } from "vue";
import trainersService from "../services/trainersService.js";

export default function useTrainers() {
	const trainers = ref([]);

	const fetchTrainers = async () => {
		try {
			const response = await trainersService.getAllTrainers();
			trainers.value = response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération des entraîneurs :", error);
		}
	};

	const deleteTrainer = async (id) => {
		try {
			await trainersService.deleteTrainer(id);
			trainers.value = trainers.value.filter((t) => t.id !== id);
		} catch (error) {
			console.error("Erreur lors de la suppression :", error);
		}
	};


	// Mettre à jour un joueur existant
	const updateTrainer = async (trainer) => {
		try {
			// Nettoyage des disponibilités avant envoi
			trainer.disponibilities = trainer.disponibilities.map(slot => ({
				id: slot.id && typeof slot.id === "string" ? slot.id : undefined, // Conserver les IDs valides ou les exclure
				day: slot.day,
				open: slot.open,
				close: slot.close,
			}));

			const response = await trainersService.updateTrainer(trainer.id, trainer);
			// Met à jour la liste des joueurs localement
			const index = trainers.value.findIndex((p) => p.id === trainer.id);
			if (index !== -1) {
				trainers.value[index] = response.data;
			}
			console.log("Joueur mis à jour :", response.data);
			return response.data;
		} catch (error) {
			if (error.response) {
				console.error("Erreur de réponse du backend :", error.response.data);
			} else {
				console.error("Erreur lors de la mise à jour :", error.message);
			}
			throw error;
		}
	};

	const createTrainer = async (trainer) => {
		try {
			// Nettoyage des disponibilités avant envoi
			trainer.disponibilities = trainer.disponibilities.map(slot => ({
				id: slot.id && typeof slot.id === "string" ? slot.id : undefined, // Conserver les IDs valides ou les exclure
				day: slot.day,
				open: slot.open,
				close: slot.close,
			}));

			const response = await trainersService.createTrainer(trainer);
			trainers.value.push(response.data);
			console.log("Joueur créé :", response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la création :", error);
			throw error;
		}
	};

	return {
		trainers,
		fetchTrainers,
		deleteTrainer,
		createTrainer,
		updateTrainer
	};
}
