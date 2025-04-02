// src/services/TrainersService.js
import apiService from './apiService';

export default {
	getAllTrainers() {
		return apiService.getData('/trainers');
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

	resetPassword(email) {
		return apiService.post('/trainers/reset-password', email, {
				headers: {
					'Content-Type': 'text/plain',
				},
			});
	},

	changePassword(data) {
		return apiService.patch('/trainers/change-password', data);
	},
};