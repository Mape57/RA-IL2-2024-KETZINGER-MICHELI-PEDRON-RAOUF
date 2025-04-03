import {defineStore} from "pinia";
import {ref} from "vue";
import playersService from "../services/PlayersService";
import PlayersService from "../services/PlayersService";

export const usePlayersStore = defineStore("players", () => {
	const players = ref([]);
	const loading = ref(false);

	const fetchPlayers = async () => {
		try {
			loading.value = true;
			const response = await playersService.getAllPlayers();
			players.value = response.data;
		} catch (error) {
			console.error("Error fetching players:", error);
		} finally {
			loading.value = false;
		}
	};

	const getPlayerById = (id) => {
		return players.value.find(player => player.id === id);
	};

	/**
	 * Fetch specific players by their IDs
	 * @param {Array} playerIds - Array of player IDs to fetch
	 * @returns {Promise<Array>} - Array of player objects
	 */
	const fetchPlayersByIds = async (playerIds) => {
		if (!playerIds || playerIds.length === 0) return [];

		try {
			loading.value = true;
			const fetchPromises = playerIds.map(id => playersService.getPlayerById(id));
			const responses = await Promise.all(fetchPromises);

			// Update the players in the store
			responses.forEach(response => {
				const playerData = response.data;
				const index = players.value.findIndex(p => p.id === playerData.id);

				if (index !== -1) {
					// Update existing player
					players.value[index] = playerData;
				} else {
					// Add new player
					players.value.push(playerData);
				}
			});

			return responses.map(r => r.data);
		} catch (error) {
			console.error("Error fetching players by IDs:", error);
			return [];
		} finally {
			loading.value = false;
		}
	};

	const createPlayer = async (playerData) => {
		try {
			const response = await playersService.createPlayer(playerData);
			players.value.push(response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la création :", error);
			throw error;
		}
	};

	const updatePlayer = async (id, playerData) => {
		try {
			const response = await playersService.updatePlayer(id, playerData);
			const index = players.value.findIndex(player => player.id === id);
			if (index !== -1) {
				players.value[index] = response.data;
			}
			return response.data;
		} catch (error) {
			console.error("Error updating player:", error);
			throw error;
		}
	};


	const deletePlayer = async (id) => {
		try {
			await playersService.deletePlayer(id);
			players.value = players.value.filter(player => player.id !== id);
			return true;
		} catch (error) {
			console.error("Error deleting player:", error);
			throw error;
		}
	};
// Méthode pour incrémenter le nombre de sessions d'un joueur
	const incrementPlayerSessionCount = (playerId) => {
		const playerIndex = players.value.findIndex((player) => player.id === playerId);
		if (playerIndex !== -1) {
			players.value[playerIndex].nombreDeSeances += 1;
		}
	};

// Méthode pour décrémenter le nombre de sessions d'un joueur
	const decrementPlayerSessionCount = (playerId) => {
		const playerIndex = players.value.findIndex((player) => player.id === playerId);
		if (playerIndex !== -1 && players.value[playerIndex].nombreDeSeances > 0) {
			players.value[playerIndex].nombreDeSeances -= 1;
		}
	};

	const validatePlayer = async (playerId, updatedPlayer) => {
		try {
			const response = await playersService.getPlayerById(playerId);
			const player = response.data;

			player.validate = true;
			player.level = updatedPlayer.level; // Mise à jour du niveau sélectionné

			const updatedResponse = await playersService.updatePlayer(playerId, player);

			pendingPlayers.value = pendingPlayers.value.filter(p => p.id !== playerId);

			return updatedResponse.data;
		} catch (error) {
			throw error;
		}
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

	const deleteAllPlayer = async () => {
		try {
			await playersService.deleteAllPlayers();
			players.value = [];
		} catch (error) {
			console.error("Erreur lors de la suppression de tous les joueurs :", error);
		}
	};







	return {
		players,
		loading,
		fetchPlayers,
		fetchPlayersByIds,
		getPlayerById,
		createPlayer,
		updatePlayer,
		deletePlayer,
		incrementPlayerSessionCount,
		decrementPlayerSessionCount,
		validatePlayer,
		fetchPendingPlayers,
		computeAge,
		deleteAllPlayer,
		pendingPlayers
	};
});