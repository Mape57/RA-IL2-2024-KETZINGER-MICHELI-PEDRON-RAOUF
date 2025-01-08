// src/composables/usePlayers.js
import {ref} from "vue";
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
			// Nettoyage des disponibilités avant envoi
			player.disponibilities = player.disponibilities.map(slot => ({
				id: slot.id && typeof slot.id === "string" ? slot.id : undefined, // Conserver les IDs valides ou les exclure
				day: slot.day,
				open: slot.open,
				close: slot.close,
			}));

			const response = await playersService.updatePlayer(player.id, player);
			// Met à jour la liste des joueurs localement
			const index = players.value.findIndex((p) => p.id === player.id);
			if (index !== -1) {
				players.value[index] = response.data;
			}
			console.log("Joueur mis à jour :", response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la mise à jour :", error);
			throw error; // Propage l'erreur pour la gestion dans le composant
		}
	};

	const createPlayer = async (player) => {
		try {
			// Nettoyage des disponibilités avant envoi
			player.disponibilities = player.disponibilities.map(slot => ({
				id: slot.id && typeof slot.id === "string" ? slot.id : undefined, // Conserver les IDs valides ou les exclure
				day: slot.day,
				open: slot.open,
				close: slot.close,
			}));

			const response = await playersService.createPlayer(player);
			players.value.push(response.data);
			console.log("Joueur créé :", response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la création :", error);
			throw error;
		}
	};


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


	return {
		computeAge,
		createPlayer,
		deletePlayer,
		fetchPlayers,
		players,
		updatePlayer,
	};
}
