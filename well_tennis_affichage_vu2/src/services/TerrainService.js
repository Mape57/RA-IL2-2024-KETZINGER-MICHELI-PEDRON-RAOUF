// src/services/terrainService.js
import apiService from './apiService';

export default {
	getAllTerrain() {
		return apiService.getData('/courts');
	},

	getTerrainById(id) {
		return apiService.getData(`/courts/${id}`);
	},

	createTerrain(playerData) {
		return apiService.post('/courts', TerrainData);
	},

	updateTerrain(id, playerData) {
		return apiService.patch(`/courts/${id}`, TerrainData);
	},

	deleteTerrain(id) {
		return apiService.delete(`/courts/${id}`);
	},
};