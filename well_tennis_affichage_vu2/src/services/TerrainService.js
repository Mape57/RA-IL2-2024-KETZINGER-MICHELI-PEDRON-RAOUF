// src/services/trainersService.js
import apiService from './apiService';

export default {
	getAllTerrain() {
		return apiService.getData('/court-controller');
	},

	getTerrainById(id) {
		return apiService.getData(`/court-controller/${id}`);
	},

	createTerrain(playerData) {
		return apiService.post('/court-controller', TerrainData);
	},

	updateTerrain(id, playerData) {
		return apiService.patch(`/court-controller/${id}`, TerrainData);
	},

	deleteTerrain(id) {
		return apiService.delete(`/court-controller/${id}`);
	},
};