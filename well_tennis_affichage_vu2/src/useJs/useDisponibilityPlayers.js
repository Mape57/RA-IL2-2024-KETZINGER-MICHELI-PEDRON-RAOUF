// src/composables/useDisponibilityPlayer.js
import {ref} from "vue";
import DisponibilityPlayerService from "../services/DisponibilityPlayerService.js";
import useDisponibility from "./useDisponibility.js";

export default function useDisponibilityPlayer() {

	const disponibilityPlayers = ref([]);
	const { createDisponibility } = useDisponibility(); // Récupérer la fonction de création


	// Récupérer toutes les associations
	const fetchDisponibilityPlayers = async () => {
		try {
			const response = await DisponibilityPlayerService.getAllDisponibilityPlayers();
			disponibilityPlayers.value = response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération des disponibilités des joueurs :", error);
		}
	};


	// Associer une disponibilité à un joueur
	const addDisponibilityToPlayer = async (idPlayer, disponibilityData) => {
		try {
			const disponibilityResponse = await createDisponibility(disponibilityData);
			const idDisponibility = disponibilityResponse.id;

			const response = await DisponibilityPlayerService.createDisponibilityPlayer({
				idPlayer,
				idDisponibility,
			});
			disponibilityPlayers.value.push(response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de l'association de la disponibilité :", error);
			throw error;
		}
	};


	// Supprimer une disponibilité d'un joueur
	const removeDisponibilityFromPlayer = async (idPlayer, idDisponibility) => {
		try {
			await DisponibilityPlayerService.deleteDisponibilityPlayer(idPlayer, idDisponibility);
			disponibilityPlayers.value = disponibilityPlayers.value.filter(
				(dp) => dp.idPlayer !== idPlayer || dp.idDisponibility !== idDisponibility
			);
		} catch (error) {
			console.error("Erreur lors de la suppression de la disponibilité du joueur :", error);
		}
	};

	return {
		disponibilityPlayers,
		fetchDisponibilityPlayers,
		addDisponibilityToPlayer,
		removeDisponibilityFromPlayer,
	};
}