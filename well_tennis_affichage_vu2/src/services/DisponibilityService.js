// src/services/DisponibilityService.js
import apiService from './apiService';

export default {
	getAllDisponibility() {
		return apiService.getData('/disponibility');
	},

	getDisponibilityById(id) {
		return apiService.getData(`/disponibility/${id}`);
	},

	 createDisponibility(disponibilityData) {
		return apiService.post("/disponibility", disponibilityData);
	},

	updateDisponibility(id, DisponibilityData) {
		return apiService.patch(`/disponibility/${id}`, DisponibilityData);
	},

	deleteDisponibility(id) {
		return apiService.delete(`/disponibility/${id}`);
	},
};

