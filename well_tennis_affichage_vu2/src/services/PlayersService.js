// src/services/PlayersService.js
import apiService from './apiService';

export default {
	getAllPlayers() {
		return apiService.getData('/players');
	},

	getPlayerById(id) {
		return apiService.getData(`/players/${id}`);
	},

	createPlayer(playerData) {
		return apiService.post('/players', playerData);
	},

	updatePlayer(id, playerData) {
		return apiService.patch(`/players/${id}`, playerData);
	},

	deletePlayer(id) {
		return apiService.delete(`/players/${id}`);
	},
};