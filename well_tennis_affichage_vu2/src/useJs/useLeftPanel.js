// src/composables/useLeftPanel.js
import { ref } from "vue";
import useTrainers from "./useTrainers.js";
import usePlayers from "./usePlayers.js";

export default function useLeftPanel() {
	// États
	const { trainers, fetchTrainers, deleteTrainer } = useTrainers();
	const { players, fetchPlayers, deletePlayer } = usePlayers();
	const selectedTab = ref("data");  // Manquait ici
	const searchQuery = ref("");

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
	};
}
