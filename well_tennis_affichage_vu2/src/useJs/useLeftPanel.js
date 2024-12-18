// src/composables/useLeftPanel.js
import { ref } from "vue";
import useTrainers from "./useTrainers.js";
import usePlayers from "./usePlayers.js";

export default function useLeftPanel() {
	// États
	const { trainers, fetchTrainers, deleteTrainer } = useTrainers();
	const { players, fetchPlayers, deletePlayer } = usePlayers();
	const selectedTab = ref("data");
	const searchQuery = ref("");

	const selectTab = (tab) => {
		selectedTab.value = tab;
	};

	return {
		trainers,
		players,
		selectedTab,
		searchQuery,
		fetchTrainers,
		fetchPlayers,
		deleteTrainer,
		deletePlayer,
	};
}
