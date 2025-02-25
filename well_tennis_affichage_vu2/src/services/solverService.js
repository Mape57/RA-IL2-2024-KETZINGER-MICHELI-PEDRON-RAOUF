import apiService from "./apiService.js";

export default {
	startSolver() {
		return apiService.getData('/solver/start');
	},

	stopSolver() {
		return apiService.getData('/solver/stop');
	},

	statusSolver() {
		return apiService.getData('/solver/status');
	},

	saveSolver() {
		return apiService.getData('/solver/save');
	},
}