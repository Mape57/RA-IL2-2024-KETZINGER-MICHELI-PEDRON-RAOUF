// src/services/DisponibilityPlayerService.js
import apiService from "./apiService";
export default {
	getAllDisponibilityPlayers() {
		return apiService.getData("/disponibilityPlayer");
	},

	getDisponibilityPlayerByPlayerId(idPlayer) {
		return apiService.getData(`/disponibilityPlayer/${idPlayer}`);
	},

	createDisponibilityPlayer(data) {
		return apiService.post("/disponibilityPlayer", data);
	},

	deleteDisponibilityPlayer(idPlayer, idDisponibility) {
		return apiService.delete(`/disponibilityPlayer/${idPlayer}/${idDisponibility}`);
	},
};