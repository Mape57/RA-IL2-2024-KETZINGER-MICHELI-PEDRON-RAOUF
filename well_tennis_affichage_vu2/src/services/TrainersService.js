// src/services/trainersService.js
import apiService from './apiService';

export default {
	getAllTrainers() {
		return apiService.getData('/coachs/all');
	},

	getTrainerById(id) {
		return apiService.getData(`/trainers/${id}`);
	},

	createTrainer(trainerData) {
		return apiService.post('/trainers', trainerData);
	},

	updateTrainer(id, trainerData) {
		return apiService.put(`/trainers/${id}`, trainerData);
	},

	deleteTrainer(id) {
		return apiService.delete(`/trainers/${id}`);
	},
};