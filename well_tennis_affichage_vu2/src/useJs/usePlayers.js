// src/composables/usePlayers.js
import { ref } from "vue";
import playersService from "../services/PlayersService.js";

export default function usePlayers() {
	const players = ref([]);

	const fetchPlayers = async () => {
		try {
			const response = await playersService.getAllPlayers();
			players.value = response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération des joueurs :", error);
		}
	};

	const deletePlayer = async (id) => {
		try {
			await playersService.deletePlayer(id);
			players.value = players.value.filter((p) => p.id !== id);
		} catch (error) {
			console.error("Erreur lors de la suppression :", error);
		}
	};

	// Mettre à jour un joueur existant
	const updatePlayer = async (player) => {
		try {
			const response = await playersService.updatePlayer(player.id, player);
			// Met à jour la liste des joueurs localement
			const index = players.value.findIndex((p) => p.id === player.id);
			if (index !== -1) {
				players.value[index] = response.data;
			}
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la mise à jour :", error);
			throw error; // Propage l'erreur pour la gestion dans le composant
		}
	};



	// Calcul de l'âge
	const computeAge = (birthday) => {
		if (!birthday) return "N/A";

		const birthDate = new Date(birthday);
		const today = new Date();

		let age = today.getFullYear() - birthDate.getFullYear();

		const hasBirthdayPassed =
			today.getMonth() > birthDate.getMonth() ||
			(today.getMonth() === birthDate.getMonth() &&
				today.getDate() >= birthDate.getDate());

		if (!hasBirthdayPassed) {
			age--;
		}
		return age;
	};



	return {
		computeAge,
		deletePlayer,
		fetchPlayers,
		players,
		updatePlayer,
	};
}
