// src/services/trainersService.js
import apiService from './apiService';

export default {
	getAllPlayers() {
		return apiService.getData('/players/all');
	},

	getTrainerById(id) {
		return apiService.getData(`/players/${id}`);
	},

	createTrainer(trainerData) {
		return apiService.post('/players', trainerData);
	},

	updateTrainer(id, trainerData) {
		return apiService.put(`/players/${id}`, trainerData);
	},

	deleteTrainer(id) {
		return apiService.delete(`/players/${id}`);
	},
};