<template>
  <div
      :class="['left-panel', isMobile ? 'w-[55%]' : isTablet ? 'w-[40%]' : 'w-[30%]']"
      class="fixed z-[10] top-5 left-5 bg-white rounded-lg shadow-md h-[97vh] p-6 flex flex-col ">
    <!-- Bouton de fermeture -->
    <button v-if="isMobile" @click="$emit('close')" class="close-button">
      <span class="material-symbols-outlined">close</span>
    </button>

    <!-- Onglets -->
    <div class="tabs flex items-center mb-4">
      <div class="flex w-full">
        <button
            @click="selectTab('data')"
            :class="{ active: selectedTab === 'data' }"
            class="tab-button flex-grow flex items-center justify-center"
        >
          <span class="material-symbols-outlined mr-2">database</span>
          <span v-if="!isMobile">Données</span>
        </button>
        <button
            @click="selectTab('constraints')"
            :class="{ active: selectedTab === 'constraints' }"
            class="tab-button flex-grow flex items-center justify-center"
        >
          <span class="material-symbols-outlined mr-2">gavel</span>
          <span v-if="!isMobile">Contraintes</span>
        </button>
      </div>

      <button
          v-if="!isMobile"
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
        <Trainers :trainers="trainers" @update:trainers="updateTrainers"/>
        <Players :players="players" :searchQuery="searchQuery" @update:players="updatePlayers"/>
      </div>

      <!-- Onglet Contraintes -->
      <div v-if="selectedTab === 'constraints'">
        <Terrains :terrains="terrains" />
        <Session :sessions="sessions" />
      </div>

      <!-- Onglet Paramètres (masqué en mode mobile) -->
      <div v-if="selectedTab === 'settings' && !isMobile" class="content-settings">
        <div class="py-2 font-bold text-gray-700 flex items-center">
          <span class="material-symbols-outlined mr-2">upload</span>
          Importer vos données
        </div>
        <button class="menu-item" @click="togglePendingPlayers">
          <span class="material-symbols-outlined mr-2">person</span>
          Consulter les inscrits
        </button>

        <label class="menu-item cursor-pointer">
          <span class="material-symbols-outlined mr-2">database</span>
          Importer Données et Contraintes - format CSV
          <input type="file" accept=".xlsx, .xls" @change="importCSV" class="hidden" />
        </label>

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

        <!-- Liste des inscrits en attente -->
        <div v-if="showPendingPlayers" class="mt-4 border-t pt-4">
          <h3 class="text-lg font-semibold text-gray-700 mb-4">Inscriptions en attente</h3>
          <div class="space-y-3">
            <div
                v-for="player in pendingPlayers"
                :key="player.id"
                class="flex items-center p-3 border border-gray-300 rounded-lg bg-gray-50 shadow-sm cursor-pointer hover:bg-gray-100"
                @click="showPlayerDetails(player)"
            >
              <div class="flex items-center space-x-3 flex-grow">
                <span class="material-symbols-outlined text-green-600 text-xl">person</span>
                <div>
                  <p class="text-sm font-semibold text-gray-800">{{ player.name }} {{ player.surname }}</p>
                  <p class="text-xs text-gray-500">{{ player.email }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Modale d'affichage des détails d'un joueur -->
        <!-- Modale d'affichage des détails d'un joueur -->
        <transition name="fade">
          <div v-if="selectedPlayer" class="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center p-6">
            <div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-2xl">
              <h3 class="text-2xl font-bold text-gray-700 mb-4">Détails de l'inscription</h3>
              <div class="space-y-4 text-lg">
                <p><strong>Nom :</strong> {{ selectedPlayer.name }} {{ selectedPlayer.surname }}</p>
                <p><strong>Email :</strong> {{ selectedPlayer.email }}</p>
                <p><strong>Date de naissance :</strong> {{ selectedPlayer.birthday }}</p>
                <p><strong>Nombre de cours :</strong> {{ selectedPlayer.courses }}</p>
                <p><strong>Niveau :</strong> {{ selectedPlayer.level }}</p>
                <div>
                  <p><strong>Disponibilités :</strong></p>
                  <ul class="list-disc ml-6 text-base">
                    <li v-for="dispo in selectedPlayer.disponibilities" :key="dispo.id">
                      <span class="font-semibold">Jour {{ dispo.dayWeek }}</span> : {{ dispo.open }} - {{ dispo.close }}
                    </li>
                  </ul>
                </div>
              </div>
              <div class="mt-6 flex justify-end space-x-4">
                <button @click="selectedPlayer = null" class="bg-gray-300 hover:bg-gray-400 px-5 py-2 rounded-lg text-lg">
                  Fermer
                </button>
                <button @click="validatePlayer(selectedPlayer.id)" class="bg-green-500 hover:bg-green-600 text-white px-5 py-2 rounded-lg text-lg">
                  Valider
                </button>
              </div>
            </div>
          </div>
        </transition>


      </div>
    </div>
  </div>
</template>


<script>
import Players from "./Players.vue";
import Trainers from "./Trainers.vue";
import Terrains from "./Terrain.vue";
import Session from "./Session.vue";
import useTerrain from "../../useJs/useTerrain.js";
import useLeftPanel from "../../useJs/useLeftPanel.js";
import usePlayers from "../../useJs/usePlayers";
import {onMounted, ref} from "vue";
import ExportService from "../../functionality/ExportService";
import ImportService from "../../functionality/ImportService";
import PlayersService from "../../services/PlayersService.js";

export default {
  name: "LeftPanel",
  components: {
    Trainers,
    Players,
    Terrains,
    Session,
  },
  props: {
    isMobile: Boolean,

  },
  setup() {

    const { terrains, fetchTerrains } = useTerrain();
    const {trainers, players, searchQuery, selectedTab, fetchTrainers, fetchPlayers, selectTab, updatePlayers, updateTrainers} = useLeftPanel();

    const validatePlayer = async (playerId) => {
      try {
        await PlayersService.updatePlayer(playerId, { validate: true });
        pendingPlayers.value = pendingPlayers.value.filter(player => player.id !== playerId);
        selectedPlayer.value = null; // Fermer la modale après validation
      } catch (error) {
      }
    };

    const selectedPlayer = ref(null);

    const showPlayerDetails = (player) => {
      selectedPlayer.value = player;
    };
    const { pendingPlayers, fetchPendingPlayers } = usePlayers();
    const showPendingPlayers = ref(false);

    const togglePendingPlayers = () => {
      showPendingPlayers.value = !showPendingPlayers.value;
      if (showPendingPlayers.value) {
        fetchPendingPlayers();
      }
    };
    onMounted(() => {
      fetchTrainers();
      fetchPlayers();
      fetchTerrains();
    });

    return {
      trainers,
      players,
      searchQuery,
      selectedTab,
      showPlayerDetails,
      fetchTrainers,
      fetchPlayers,
      selectTab,
      updatePlayers,
      updateTrainers,
      togglePendingPlayers,
      validatePlayer,
      pendingPlayers,
      showPendingPlayers,
      terrains,
      selectedPlayer,
    };
  },

  data() {
    return {
      isTablet: false,
      sessions: [
        {title: "3 à 4 ans", age: "3 - 4", effective: "4 - 6", duration: 1, sessions_level: "0 - 7"},
        {title: "5 à 7 ans", age: "5 - 7", effective: "6 - 8", duration: 1.5, sessions_level: "1 - 10"},
      ],
    };
  },

  methods: {
    async importCSV(event) {
      const file = event.target.files[0];
      if (!file) {
        alert("Veuillez sélectionner un fichier.");
        return;
      }
      try {
        await ImportService.importExcel(file);
      } catch (error) {
        console.error("Erreur lors de l'importation :", error);
      }
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
    checkScreenSize() {
      clearTimeout(this.resizeTimeout);
      this.resizeTimeout = setTimeout(() => {
        this.isMobile = window.innerWidth < 768;
        this.isTablet = window.innerWidth >= 768 && window.innerWidth < 1024; // Détection de la tablette
      }, 200);
    },
  },
  mounted() {
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.checkScreenSize);
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

.left-panel {
  position: relative;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 15px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
  color: #528359;
}

.close-button:hover {
  color: #3a6242;
}

.tab-button span {
  display: inline;
}


.left-panel {
  position: relative;
}

.left-panel {
  position: relative;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 15px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
  color: #528359;
}

.close-button:hover {
  color: #3a6242;
}

@media (max-width: 768px) {
  .left-panel {
    width: 55%;
    left: 7.5%;
  }
  .tab-button span:nth-child(2) {
    display: none;
  }
}

@media (min-width: 768px) and (max-width: 1024px) {
  .left-panel {
    width: 40%;
    left: 2%;
  }
}



</style>



