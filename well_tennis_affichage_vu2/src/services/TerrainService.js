import apiService from './apiService';

export default {
	getAllTerrain() {
		return apiService.getData('/courts');
	},

	getTerrainById(id) {
		return apiService.getData(`/courts/${id}`);
	},

	createTerrain(terrainData) {
		// Utilisation de terrainData (et non TerrainData)
		return apiService.post('/courts', terrainData);
	},

	updateTerrain(id, terrainData) {
		// Utilisation de terrainData (et non TerrainData)
		return apiService.patch(`/courts/${id}`, terrainData);
	},

	deleteTerrain(id) {
		return apiService.delete(`/courts/${id}`);
	},
};
