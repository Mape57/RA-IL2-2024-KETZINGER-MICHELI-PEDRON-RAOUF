// src/services/TrainersService.js
import apiService from './apiService';

export default {
	getAllTrainers() {
		return apiService.getData('/coachs');
	},

	getTrainerById(id) {
		return apiService.getData(`/coachs/${id}`);
	},

	createTrainer(trainerData) {
		console.log("Données envoyées pour créer un entraîneur :", trainerData);
		return apiService.post('/coachs', trainerData);
	},


	updateTrainer(id, trainerData) {
		return apiService.patch(`/coachs/${id}`, trainerData);
	},

	deleteTrainer(id) {
		return apiService.delete(`/coachs/${id}`);
	},
};