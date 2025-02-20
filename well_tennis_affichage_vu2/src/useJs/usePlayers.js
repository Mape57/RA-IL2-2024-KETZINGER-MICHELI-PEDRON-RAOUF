// src/composables/usePlayers.js
import {ref} from "vue";
import playersService from "../services/PlayersService.js";
import PlayersService from "../services/PlayersService.js";

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

	const createPlayer = async (player) => {
		try {

			const response = await playersService.createPlayer(player);
			players.value.push(response.data);
			console.log("Joueur créé :", response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la création :", error);
			throw error;
		}
	};


	// Calcul de l'âge
	const computeAge = (birthday) => {
		if (!birthday) return "N/A";

		const birthDate = new Date(birthday);
		const currentDate = new Date();
		let currentYear = currentDate.getFullYear();
		if (currentDate.getMonth() < 9) currentYear--;

		let sportsAge = currentYear - birthDate.getFullYear();

		// Si la personne est née avant septembre, elle a un an de plus pour l'année sportive
		if (birthDate.getMonth() < 9) { // Mois en JavaScript : 0 = janvier, 9 = septembre
			sportsAge++;
		}

		return sportsAge;
	};

	const pendingPlayers = ref([]);

	// Récupérer les joueurs qui ne sont pas encore validés
	const fetchPendingPlayers = async () => {
		try {
			const response = await PlayersService.getAllPlayersOfValidateStatus(false);
			pendingPlayers.value = response.data;
			console.log("Joueurs en attente récupérés :", pendingPlayers.value);
		} catch (error) {
			console.error("Erreur lors de la récupération des joueurs en attente :", error);
		}
	};


	return {
		computeAge,
		createPlayer,
		deletePlayer,
		fetchPlayers,
		players,
		updatePlayer,
		pendingPlayers,
		fetchPendingPlayers,
	};
}
