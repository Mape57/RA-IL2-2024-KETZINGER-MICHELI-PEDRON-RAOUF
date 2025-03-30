import apiService from "./apiService.js";

export default {
	startSolver() {
		return apiService.post('/solver', {});
	},

	stopSolver() {
		return apiService.getData('/solver/stop');
	},

	statusSolver() {
		return apiService.getData('/solver');
	},

	saveSolver() {
		return apiService.getData('/solver/save');
	},

	justifications() {
		return apiService.getData('/solver/justifications');
	},

	rmpkGet() {
		return apiService.getData('/solver/rmpk');
	},

	rmpkPost(data) {
		return apiService.post('/solver/rmpk', data, {
			headers: {
				'Content-Type': 'text/plain',
			},
		});
	},
}