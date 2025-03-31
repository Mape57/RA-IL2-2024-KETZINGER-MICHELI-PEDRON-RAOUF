<template>
  <div
      :class="['left-panel', localIsMobile ? 'w-[55%]' : isTablet ? 'w-[40%]' : 'w-[30%]']"
      class="fixed top-5 left-5 bg-white rounded-lg shadow-md h-[97vh] p-6 flex flex-col ">
    <!-- Bouton de fermeture -->
    <button v-if="localIsMobile" @click="$emit('close')" class="close-button">
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
          v-if="userRole === 'ROLE_ADMIN' && !isMobile"
          @click="selectTab('settings')"
          :class="{ active: selectedTab === 'settings' }"
          class="tab-button flex-grow flex items-center justify-center"
      >
        <span class="material-symbols-outlined ">settings</span>
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
    <div class="content flex-1 overflow-auto p-4 w-full overflow-x-hidden">
      <!-- Onglet Données -->
      <div v-if="selectedTab === 'data'">
        <Trainers
            :trainers="trainers"
            :searchQuery="searchQuery"
            :isMobile="isMobile"
            :userRole="userRole"
            :loading="isLoadingTrainers"
            @update:trainers="userRole === 'ROLE_ADMIN' ? updateTrainers : () => {}"
        />
        <Players
            :players="players"
            :searchQuery="searchQuery"
            :isMobile="isMobile"
            :userRole="userRole"
            :loading="isLoadingPlayers"
            @update:players="userRole === 'ROLE_ADMIN' ? updatePlayers : () => {}"
        />
      </div>

      <!-- Onglet Contraintes -->
      <div v-if="selectedTab === 'constraints'">
        <Terrains :terrains="terrains" :isMobile="isMobile" :userRole="userRole"/>
        <Session :isMobile="isMobile" :userRole="userRole" />
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

        <div v-if="terrainErrors.length" class="bg-red-100 text-red-800 p-4 rounded mb-4">
          <p class="font-semibold mb-2">Erreurs détectées lors de l'import :</p>
          <ul class="list-disc list-inside text-sm">
            <li v-for="(err, index) in terrainErrors" :key="index">{{ err }}</li>
          </ul>
        </div>

        <div class="py-2 font-bold text-gray-700 flex items-center">
          <span class="material-symbols-outlined mr-2">download</span>
          Télécharger vos données
        </div>
        <button class="menu-item" @click="downloadXLS">
          <span class="material-symbols-outlined mr-2">calendar_today</span>
          Planning - format PDF
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
        <PlayerNot v-if="showPendingPlayers" :pendingPlayers="pendingPlayers" @update:pendingPlayers="updatePendingPlayers" />


      </div>
    </div>
  </div>
</template>


<script>
import { onBeforeUnmount, onMounted, ref, watch } from "vue";
import useTerrain from "../../useJs/useTerrain.js";
import useLeftPanel from "../../useJs/useLeftPanel.js";
import usePlayers from "../../useJs/usePlayers";
import useSessionConstraint from "../../useJs/useSessionConstraint.js";
import PlayersService from "../../services/PlayersService.js";
import ExportService from "../../functionality/ExportService";
import ImportService from "../../functionality/ImportService";
import ExportPdf from "../../functionality/ExportPdf";


import Players from "./Players.vue";
import Trainers from "./Trainers.vue";
import Terrains from "./Terrain.vue";
import Session from "./Session.vue";
import PlayerNot from "../vueInformations/PlayerNot.vue";



export default {
  name: "LeftPanel",
  components: {
    PlayerNot,
    Trainers,
    Players,
    Terrains,
    Session,
  },
  props: {
    isMobile: Boolean,
    userRole: String,
  },
  setup(props) {
    const localIsMobile = ref(props.isMobile);
    const isTablet = ref(false);
    const selectedPlayer = ref(null);
    const showPendingPlayers = ref(false);
    const terrainErrors = ref([]);
    const isLoadingTrainers = ref(true);
    const isLoadingPlayers = ref(true);


    watch(() => props.isMobile, (newVal) => {
      localIsMobile.value = newVal;
    });

    const checkScreenSize = () => {
      localIsMobile.value = window.innerWidth < 768;
      isTablet.value = window.innerWidth >= 768 && window.innerWidth < 1024;
    };

    onMounted(() => {
      checkScreenSize();
      window.addEventListener("resize", checkScreenSize);
    });

    onBeforeUnmount(() => {
      window.removeEventListener("resize", checkScreenSize);
    });

    const updatePendingPlayers = (updatedList) => {
      pendingPlayers.value = updatedList;
    };


    const getJourLabel = (dayWeek) => {
      const jours = {
        1: "Lundi",
        2: "Mardi",
        3: "Mercredi",
        4: "Jeudi",
        5: "Vendredi",
        6: "Samedi",
        7: "Dimanche",
      };
      return jours[dayWeek] || "Jour inconnu";
    };


    const { terrains, fetchTerrains } = useTerrain();
    const { trainers, players, searchQuery, selectedTab, fetchTrainers, fetchPlayers, selectTab, updatePlayers, updateTrainers } = useLeftPanel();
    const { pendingPlayers, fetchPendingPlayers } = usePlayers();

    const validatePlayer = async (playerId) => {
      try {
        await PlayersService.updatePlayer(playerId, { validate: true });
        pendingPlayers.value = pendingPlayers.value.filter(player => player.id !== playerId);
        selectedPlayer.value = null; // Fermer la modale après validation
      } catch (error) {
      }
    };

    const showPlayerDetails = (player) => {
      selectedPlayer.value = player;
    };

    const togglePendingPlayers = () => {
      showPendingPlayers.value = !showPendingPlayers.value;
      if (showPendingPlayers.value) {
        fetchPendingPlayers();
      }
    };

    const fetchTrainersWithLoading = async () => {
      isLoadingTrainers.value = true;
      try {
        await fetchTrainers();
      } finally {
        isLoadingTrainers.value = false;
      }
    };

    const fetchPlayersWithLoading = async () => {
      isLoadingPlayers.value = true;
      try {
        await fetchPlayers();
      } finally {
        isLoadingPlayers.value = false;
      }
    };

    onMounted(() => {
      fetchTrainersWithLoading();
      fetchPlayersWithLoading();
      fetchTerrains();
    });

    return {
      localIsMobile,
      isTablet,
      trainers,
      players,
      searchQuery,
      selectedTab,
      pendingPlayers,
      showPendingPlayers,
      terrains,
      selectedPlayer,
      terrainErrors,
      showPlayerDetails,
      fetchTrainers,
      fetchPlayers,
      selectTab,
      updatePlayers,
      updateTrainers,
      togglePendingPlayers,
      validatePlayer,
      updatePendingPlayers,
      getJourLabel,
      isLoadingTrainers,
      isLoadingPlayers,
    };
  },

  data() {
    return {
      isTablet: false,
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
        const result = await ImportService.importExcel(file);
        this.terrainErrors = result.terrainErrors || [];
        if (this.terrainErrors.length > 0) {
          this.selectTab('settings');
        }

      } catch (error) {
        console.error("Erreur lors de l'importation :", error);
        this.terrainErrors = [
          "Une erreur est survenue pendant l'importation.",
          error.message || ""
        ];
      }
    },

    async downloadXLS() {
      try {
        await ExportPdf.generateSessionsPdf();
        console.log("Exportation PDF réussie !");
      } catch (error) {
        console.error("Erreur lors de l'exportation du PDF :", error);
      }
    },
    
    async downloadCSV() {
        const { sessionConstraints, fetchSessionConstraints } = useSessionConstraint();
        await fetchSessionConstraints();
        await ExportService.downloadCSV(this.players, this.trainers, this.terrains, sessionConstraints.value);
    },

    sendReinscriptionMail() {
      alert("Envoi du mail de réinscription !");
    },
    async deleteAllPlayers() {
      if (confirm("Êtes-vous sûr de vouloir supprimer tous les joueurs ?")) {
        try {
          await PlayersService.deleteAllPlayers(); // Suppression via l'API
          this.players = []; // Mise à jour de la liste après suppression
          alert("Tous les joueurs ont été supprimés avec succès !");
        } catch (error) {
          console.error("Erreur lors de la suppression des joueurs :", error);
          alert("Une erreur s'est produite lors de la suppression.");
        }
      }
    },
    checkScreenSize() {
      clearTimeout(this.resizeTimeout);
      this.resizeTimeout = setTimeout(() => {
        this.localIsMobile = window.innerWidth < 768;
        this.isTablet = window.innerWidth >= 768 && window.innerWidth < 1024;
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
.content {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  padding-right: 7px;
  box-sizing: content-box;
}

/* Scrollbar */
.content::-webkit-scrollbar {
  width: 12px;
}

.content::-webkit-scrollbar-track {
  background: transparent;
  border-radius: 10px;
}

.content::-webkit-scrollbar-thumb {
  background: linear-gradient(45deg, #528359, #3a6242);
  border-radius: 10px;
  border: 2px solid transparent;
  background-clip: padding-box;
  transition: background 0.3s ease-in-out, border 0.3s ease-in-out;
}

.content::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(45deg, #3a6242, #2f6035);
  border: 2px solid rgba(255, 255, 255, 0.3);
}

/* Pour firefox */
.content {
  scrollbar-color: #528359 transparent;
  scrollbar-width: thin;
}

.left-panel {
  position: fixed;
  z-index: 1000;
  background: white;
  border-radius: 8px;
  overflow-y: auto;
  overflow-x: hidden;
  box-sizing: border-box;
}

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
  flex-grow: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.tab-button.active::after {
  content: "";
  position: absolute;
  bottom: -2px;
  left: 50%;
  transform: translateX(-50%);
  width: 80%;
  height: 2px;
  background-color: #2f855a;
  transition: width 0.3s ease-in-out;
}

.tab-button.active {
  color: #2f855a;
}


.menu-item {
  display: block;
  width: 100%;
  text-align: left;
  padding: 13px 12px;
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

/* Icône dans les boutons */
.menu-item .material-symbols-outlined {
  font-size: 20px;
  line-height: 1;
  vertical-align: middle;
  margin-right: 10px;
  transition: transform 0.3s ease-in-out, color 0.3s ease-in-out;
}

.menu-item:hover {
  background-color: #2F855A;
  color: white;
  border-color: #2F855A;
  transform: translateY(-2px);
}


.menu-item:hover {
  background-color: #2F855A;
  color: white;
  border-color: #2F855A;
  transform: translateY(-2px);
}

.menu-item:hover .material-symbols-outlined {
  color: white;
  transform: scale(1.1);
}

.menu-item:active {
  transform: scale(0.95);
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



