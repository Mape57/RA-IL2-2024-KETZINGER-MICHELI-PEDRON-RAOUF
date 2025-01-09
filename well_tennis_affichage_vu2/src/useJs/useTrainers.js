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

	return {
		trainers,
		fetchTrainers,
		deleteTrainer,
	};
}
