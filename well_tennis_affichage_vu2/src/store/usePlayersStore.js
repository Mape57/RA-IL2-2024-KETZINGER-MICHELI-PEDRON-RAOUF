import { defineStore } from "pinia";
import { ref } from "vue";
import playersService from "../services/PlayersService";

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
			console.error("Error creating player:", error);
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

	return {
		players,
		loading,
		fetchPlayers,
		fetchPlayersByIds,
		getPlayerById,
		createPlayer,
		updatePlayer,
		deletePlayer
	};
});