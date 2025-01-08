// src/composables/usePlayers.js
import {ref, toRaw} from "vue";
import playersService from "../services/PlayersService.js";

export default function usePlayers() {
	const players = ref([]);

	const fetchPlayers = async () => {
		try {
			const response = await playersService.getAllPlayers();
			players.value = response.data;
		} catch (error) {
			console.error("Erreur lors de la récupération des joueurs :", error);
		}
	};

	const deletePlayer = async (id) => {
		try {
			await playersService.deletePlayer(id);
			players.value = players.value.filter((p) => p.id !== id);
		} catch (error) {
			console.error("Erreur lors de la suppression :", error);
		}
	};

	// Mettre à jour un joueur existant
	const updatePlayer = async (player) => {
		try {
			const rawPlayer = toRaw(player);

			rawPlayer.disponibilities = rawPlayer.disponibilities.map((d) => ({
				id: typeof d.id === "string" ? d.id : undefined, // Exclure les id non valides
				day: d.day,
				open: d.open,
				close: d.close,
			}));

			console.log("Données envoyées au backend :", rawPlayer);

			const response = await playersService.updatePlayer(rawPlayer.id, rawPlayer);
			const index = players.value.findIndex((p) => p.id === player.id);
			if (index !== -1) {
				players.value[index] = response.data;
			}
			console.log("Joueur mis à jour :", response.data);
			return response.data;
		} catch (error) {
			if (error.response) {
				console.error("Erreur de réponse du backend :", error.response.data);
			} else {
				console.error("Erreur lors de la mise à jour :", error.message);
			}
			throw error;
		}
	};

	const createPlayer = async (player) => {
		try {
			const response = await playersService.createPlayer(player);
			players.value.push(response.data);
			console.log("Joueur créé :", response.data);
			return response.data;
		} catch (error) {
			console.error("Erreur lors de la création :", error);
			throw error;
		}
	};


	// Calcul de l'âge
	const computeAge = (birthday) => {
		if (!birthday) return "N/A";

		const birthDate = new Date(birthday);
		const today = new Date();

		let age = today.getFullYear() - birthDate.getFullYear();

		const hasBirthdayPassed =
			today.getMonth() > birthDate.getMonth() ||
			(today.getMonth() === birthDate.getMonth() &&
				today.getDate() >= birthDate.getDate());

		if (!hasBirthdayPassed) {
			age--;
		}
		return age;
	};



	return {
		computeAge,
		createPlayer,
		deletePlayer,
		fetchPlayers,
		players,
		updatePlayer,
	};
}
