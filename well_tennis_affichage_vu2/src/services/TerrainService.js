// src/services/trainersService.js
import apiService from './apiService';

export default {
	getAllPlayers() {
		return apiService.getData('/court-controller');
	},

	getPlayerById(id) {
		return apiService.getData(`/court-controller/${id}`);
	},

	createPlayer(playerData) {
		return apiService.post('/court-controller', playerData);
	},

	updatePlayer(id, playerData) {
		return apiService.patch(`/court-controller/${id}`, playerData);
	},

	deletePlayer(id) {
		return apiService.delete(`/court-controller/${id}`);
	},
};