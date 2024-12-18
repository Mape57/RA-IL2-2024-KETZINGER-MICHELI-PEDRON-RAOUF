// src/services/trainersService.js
import apiService from './apiService';

export default {
	getAllPlayers() {
		return apiService.getData('/players/all');
		// A MODIFIER AVEC L'ITERATION 2 en /players/
	},

	getPlayerById(id) {
		return apiService.getData(`/players/${id}`);
	},

	createPlayer(playerData) {
		return apiService.post('/players', playerData);
	},

	updatePlayer(id, playerData) {
		return apiService.put(`/players/${id}`, playerData);
	},

	deletePlayer(id) {
		return apiService.delete(`/players/${id}`);
	},
};