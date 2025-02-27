// src/services/PlayersService.js
import apiService from './apiService';

export default {
	getAllPlayers() {
		return apiService.getData('/players?validate=true');
	},

	getAllPlayersOfValidateStatus(status) {
		return apiService.getData(`/players?validate=${status}`);
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

	deleteAllPlayers() {
		return apiService.delete('/players');
	}

};