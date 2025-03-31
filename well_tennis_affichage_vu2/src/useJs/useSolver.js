import solverService from "../services/solverService.js";

export default function useSolver() {
	const startSolver = async () => {
		try {
			const response = await solverService.startSolver();
			return response.data;
		} catch (error) {
			console.error("Erreur lors du lancement du solveur :", error.response?.data || error.message);
			throw error;
		}
	}

	const stopSolver = async () => {
		try {
			const response = await solverService.stopSolver();
			return response.data;
		} catch (error) {
			console.error("Erreur lors de l'arrêt du solveur :", error.response?.data || error.message);
			throw error;
		}
	}

	const statusSolver = async () => {
		try {
			const response = await solverService.statusSolver();
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération du status du solveur :", error.response?.data || error.message);
			throw error;
		}
	}

	const saveSolver = async () => {
		try {
			const response = await solverService.saveSolver();
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la sauvegarde du solveur :", error.response?.data || error.message);
			throw error;
		}
	}

	const solverJustifications = async () => {
		try {
			const response = await solverService.justifications();
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération des justifications du solveur :", error.response?.data || error.message);
			throw error;
		}
	}

	const rmpkGet = async () => {
		try {
			const response = await solverService.rmpkGet();
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération du rmpk :", error.response?.data || error.message);
			throw error;
		}
	}

	const rmpkPost = async (data) => {
		try {
			const response = await solverService.rmpkPost(data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de l'envoi du rmpk :", error.response?.data || error.message);
			throw error;
		}
	}

	return {
		startSolver,
		stopSolver,
		statusSolver,
		saveSolver,
		solverJustifications,
		rmpkGet,
		rmpkPost,
	}
}