import { ref } from "vue";
import {useTrainersStore} from "../store/useTrainersStore.js";
import {usePlayersStore} from "../store/usePlayersStore.js";

export default function useLeftPanel() {
	// États
	const { trainers, fetchTrainers, deleteTrainer } = useTrainersStore();
	const { players, fetchPlayers, deletePlayer } = usePlayersStore();
	const selectedTab = ref("data");
	const searchQuery = ref("");

	// Fonction pour mettre à jour la liste des joueurs
	const updatePlayers = (updatedPlayers) => {
		if (!Array.isArray(updatedPlayers)) {
			console.error("updatePlayers : mauvaise donnée reçue", updatedPlayers);
			return;
		}
		players.value = updatedPlayers;
	};

	// Fonction pour mettre à jour la liste des joueurs
	const updateTrainers = (updatedTrainers) => {
		if (!Array.isArray(updatedTrainers)) {
			console.error("updateTrainers : mauvaise donnée reçue", updatedTrainers);
			return;
		}
		trainers.value = updatedTrainers;
	};


	// Fonction de sélection de l'onglet
	const selectTab = (tab) => {
		selectedTab.value = tab;
	};

	// Retour des états et actions
	return {
		trainers,
		players,
		selectedTab,
		searchQuery,
		selectTab,
		fetchTrainers,
		fetchPlayers,
		deleteTrainer,
		deletePlayer,
		updatePlayers,
		updateTrainers
	};
}
