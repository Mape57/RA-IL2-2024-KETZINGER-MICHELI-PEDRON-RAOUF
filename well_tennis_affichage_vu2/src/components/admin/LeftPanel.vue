<template>
  <div class="left-panel fixed top-5 left-5 bg-white rounded-lg shadow-md w-[30%] h-[97vh] p-6 flex flex-col z-20">
    <div class="tabs flex items-center mb-4">

      <div class="flex w-full">
        <button
            @click="selectTab('data')"
            :class="{ active: selectedTab === 'data' }"
            class="tab-button flex-grow flex items-center justify-center"
        >
          <span class="material-symbols-outlined mr-2">database</span>
          <span>Données</span>
        </button>
        <button
            @click="selectTab('constraints')"
            :class="{ active: selectedTab === 'constraints' }"
            class="tab-button flex-grow flex items-center justify-center"
        >
          <span class="material-symbols-outlined mr-2">gavel</span>
          <span>Contraintes</span>
        </button>
      </div>

      <button
          @click="downloadData"
          class="ml-auto text-green-700 hover:text-green-900 transition"
          title="Télécharger les données"
      >
        <span class="material-symbols-outlined text-3xl">download</span>
      </button>
    </div>

    <div v-if="selectedTab === 'data'" class="mb-4">
      <input
          type="text"
          placeholder="Filtrer les données..."
          v-model="searchQuery"
          class="w-full p-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#528359] transition"
      />
    </div>

    <!-- Contenu des onglets -->
    <div class="content flex-1 overflow-auto">
      <!-- Onglet Données -->
      <div v-if="selectedTab === 'data'">
        <Trainers :trainers="trainers" />
        <!-- ici en plus d'affichier les joueurs on permet qu'il soit trier dans la recherche -->
        <Players :players="players" :searchQuery="searchQuery" />
      </div>
      <!-- Onglet Contraintes -->
      <div v-if="selectedTab === 'constraints'">
        <Terrains :terrains="terrains" />
        <Session :sessions="sessions" />
      </div>
    </div>
  </div>
</template>

<script>
import Players from "./Players.vue";
import Trainers from "./Trainers.vue";
import Terrains from "../terrains/Terrain.vue";
import Session from "./Session.vue";
import useLeftPanel from "../../useJs/useLeftPanel.js";
import { onMounted } from "vue";

export default {
  name: "LeftPanel",
  components: {
    Trainers,
    Players,
    Terrains,
    Session,
  },

  setup() {
    const { trainers, players, searchQuery,selectedTab, fetchTrainers, fetchPlayers, selectTab } = useLeftPanel();

    onMounted(() => {
      fetchTrainers();
      fetchPlayers();
    });

    return {
      trainers,
      players,
      searchQuery,
      selectedTab,
      fetchTrainers,
      fetchPlayers,
      selectTab,
    };
  },

  data() {
    return {
      terrains: [
        {
          id: 1,
          court_name: "Terrain 1",
          schedule: [
            { day: "Lundi", open: "17:00", close: "22:30" },
            { day: "Mercredi", open: "13:00", close: "22:30" },
          ],
        },
        {
          id: 2,
          court_name: "Terrain 2",
          schedule: [],
        },
      ],
      sessions: [
        { title: "3 à 4 ans", age: "3 - 4", effective: "4 - 6", duration: 1, sessions_level: "0 - 7" },
        { title: "5 à 7 ans", age: "5 - 7", effective: "6 - 8", duration: 1.5, sessions_level: "1 - 10" },
      ],
    };
  },

  methods: {
    downloadData() {
      const data = {
        terrains: this.terrains,
        sessions: this.sessions,
      };
      const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "data.json";
      a.click();
      URL.revokeObjectURL(url);
    },
  },
};
</script>

<style scoped>
.tab-button {
  color: gray;
  font-weight: bold;
  transition: all 0.3s ease-in-out;
  padding-bottom: 0.5rem;
  position: relative;
}

.tab-button.active {
  color: #2f855a;
}

.tab-button.active::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #2f855a;
}

.material-symbols-outlined {
  transition: color 0.2s ease;
}

.material-symbols-outlined:hover {
  color: #2f855a;
}
</style>



