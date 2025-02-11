import apiService from "./apiService.js";

export default {
	getAllDisponibilityTrainers() {
		return apiService.getData("/disponibilityTrainer");
	},

	getDisponibilityTrainerByTrainerId(idTrainer) {
		return apiService.getData(`/disponibilityTrainer/${idTrainer}`);
	},

	createDisponibilityTrainer(data) {
		return apiService.post("/disponibilityTrainer", data);
	},

	deleteDisponibilityTrainer(idTrainer, idDisponibility) {
		return apiService.delete(`/disponibilityTrainer/${idTrainer}/${idDisponibility}`);
	},
}