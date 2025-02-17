import {ref} from "vue";
import DisponibilityTrainerService from "../services/DisponibilityTrainerService.js";
import useDisponibility from "./useDisponibility.js";

export default function useDisponibilityTrainer() {

	const disponibilityTrainers = ref([]);
	const { createDisponibility } = useDisponibility(); // Récupérer la fonction de création

	// Récupérer toutes les associations
	const fetchDisponibilityTrainers = async () => {
		try {
			const response = await DisponibilityTrainerService.getAllDisponibilityTrainers();
			disponibilityTrainers.value = response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération des disponibilités des entraineurs :", error);
		}
	};

	// Associer une disponibilité à un entraineur
	const addDisponibilityToTrainer = async (idTrainer, disponibilityData) => {
		try {
			const disponibilityResponse = await createDisponibility(disponibilityData);
			const idDisponibility = disponibilityResponse.id;

			const response = await DisponibilityTrainerService.createDisponibilityTrainer({
				idTrainer,
				idDisponibility,
			});
			disponibilityTrainers.value.push(response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de l'association de la disponibilité :", error);
			throw error;
		}
	};


	// Supprimer une disponibilité d'un entraineur
	const removeDisponibilityFromTrainer = async (idTrainer, idDisponibility) => {
		try {
			await DisponibilityTrainerService.deleteDisponibilityTrainer(idTrainer, idDisponibility);
			disponibilityTrainers.value = disponibilityTrainers.value.filter(
				(dp) => dp.idTrainer !== idTrainer || dp.idDisponibility !== idDisponibility
			);
		} catch (error) {
			console.error("Erreur lors de la suppression de la disponibilité de l'entraineur :", error);
		}
	};


	return {
		disponibilityTrainers,
		fetchDisponibilityTrainers,
		addDisponibilityToTrainer,
		removeDisponibilityFromTrainer,
	};

}