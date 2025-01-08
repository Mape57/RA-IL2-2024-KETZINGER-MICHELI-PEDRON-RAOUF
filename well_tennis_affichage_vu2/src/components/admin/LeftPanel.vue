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
          @click="selectTab('settings')"
          :class="{ active: selectedTab === 'settings' }"
          class="tab-button flex-grow flex items-center justify-center"
      >
        <span class="material-symbols-outlined mr-2">settings</span>
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
        <Trainers :trainers="trainers"/>
        <Players :players="players" :searchQuery="searchQuery" @update:players="updatePlayers" />
      </div>
      <!-- Onglet Contraintes -->
      <div v-if="selectedTab === 'constraints'">
        <Terrains :terrains="terrains" />
        <Session :sessions="sessions" />
      </div>

      <!-- Onglet Paramètres -->
      <div v-if="selectedTab === 'settings'" class="content-settings">

        <!-- Importer vos données -->
        <div class="py-2 font-bold text-gray-700 flex items-center">
          <span class="material-symbols-outlined mr-2">upload</span>
          Importer vos données
        </div>
        <button class="menu-item" @click="importXLS">
          <span class="material-symbols-outlined mr-2">calendar_today</span>
          Planning - format XLS
        </button>
        <button class="menu-item" @click="importCSV">
          <span class="material-symbols-outlined mr-2">database</span>
          Données et contraintes - format CSV
        </button>

        <!-- Télécharger vos données -->
        <div class="py-2 font-bold text-gray-700 flex items-center">
          <span class="material-symbols-outlined mr-2">download</span>
          Télécharger vos données
        </div>
        <button class="menu-item" @click="downloadXLS">
          <span class="material-symbols-outlined mr-2">calendar_today</span>
          Planning - format XLS
        </button>
        <button class="menu-item" @click="downloadCSV">
          <span class="material-symbols-outlined mr-2">database</span>
          Données et contraintes - format CSV
        </button>

        <!-- Nouvelle année -->
        <div class="py-2 font-bold text-gray-700 flex items-center">
          <span class="material-symbols-outlined mr-2">event_repeat</span>
          Nouvelle année
        </div>
        <button class="menu-item" @click="sendReinscriptionMail">
          <span class="material-symbols-outlined mr-2">send</span>
          Envoyer le mail de réinscription
        </button>
        <button class="menu-item" @click="deleteAllPlayers">
          <span class="material-symbols-outlined mr-2">delete</span>
          Supprimer l'ensemble des joueurs
        </button>

      </div>
    </div>
  </div>
</template>

<script>
import Players from "./Players.vue";
import Trainers from "./Trainers.vue";
import Terrains from "./Terrain.vue";
import Session from "./Session.vue";
import useLeftPanel from "../../useJs/useLeftPanel.js";
import { onMounted } from "vue";
import ExportService from "../../functionality/ExportService";

export default {
  name: "LeftPanel",
  components: {
    Trainers,
    Players,
    Terrains,
    Session,
  },

  setup() {
    const {trainers, players, searchQuery, selectedTab, fetchTrainers, fetchPlayers, selectTab, updatePlayers} = useLeftPanel();

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
      updatePlayers,
    };
  },

  data() {
    return {
      terrains: [
        {
          id: 1,
          court_name: "Terrain 1",
          schedule: [
            {day: "Lundi", open: "17:00", close: "22:30"},
            {day: "Mercredi", open: "13:00", close: "22:30"},
          ],
        },
        {
          id: 2,
          court_name: "Terrain 2",
          schedule: [],
        },
      ],
      sessions: [
        {title: "3 à 4 ans", age: "3 - 4", effective: "4 - 6", duration: 1, sessions_level: "0 - 7"},
        {title: "5 à 7 ans", age: "5 - 7", effective: "6 - 8", duration: 1.5, sessions_level: "1 - 10"},
      ],
    };
  },

  methods: {
    importXLS() {
      alert("Import de planning XLS");
    },
    importCSV() {
      alert("Import des données CSV");
    },
    downloadXLS() {
      alert("Téléchargement de planning XLS");
    },
    async downloadCSV() {
      await ExportService.downloadCSV(this.players, this.trainers, this.terrains, this.sessions);
    },
    sendReinscriptionMail() {
      alert("Envoi du mail de réinscription !");
    },
    deleteAllPlayers() {
      alert("Suppression de tous les joueurs !");
    },
  },
};
</script>

<style scoped>

.tabs {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

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
  font-size: 1.3rem;
  line-height: 1;
  vertical-align: middle;
}

.material-symbols-outlined:hover {
  color: #2f855a;
}

.content-settings {
  padding: 1rem;
}

.menu-item {
  display: block;
  width: 100%;
  text-align: left;
  padding: 8px 12px;
  margin-bottom: 0.5rem;
  background-color: white;
  border: 1px solid #e2e8f0;
  color: #2f855a;
  cursor: pointer;
  border-radius: 0.5rem;
  transition: all 0.2s ease;
}

.menu-item:hover {
  background-color: #f0f4f3;
}

</style>



