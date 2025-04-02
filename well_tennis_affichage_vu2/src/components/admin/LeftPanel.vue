<template>
  <div
      :class="[
    localIsMobile ? 'w-[55%]' : isTablet ? 'w-[40%]' : 'w-[30%]'
  ]"
      class="bg-white rounded-lg shadow-md pt-6 px-6 flex flex-col overflow-hidden w-full h-full"
  >
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
            :searchQuery="searchQuery"
            :isMobile="isMobile"
            :userRole="userRole"
        />
        <Players
            :searchQuery="searchQuery"
            :isMobile="isMobile"
            :userRole="userRole"
        />
      </div>

      <!-- Onglet Contraintes -->
      <div v-if="selectedTab === 'constraints'">
        <Terrains :terrains="terrains" :isMobile="isMobile" :userRole="userRole"/>
        <Session :isMobile="isMobile" :userRole="userRole" />
      </div>

    <!-- Onglet Paramètres (masqué en mode mobile) -->
    <div v-if="selectedTab === 'settings' && !isMobile" class="content-settings">

      <!-- Section : Gestion des données -->
      <div class="py-2 font-bold text-gray-700 flex items-center">
        <span class="material-symbols-outlined mr-2">database</span>
        Gestion des données
      </div>

      <label class="menu-item cursor-pointer">
        <span class="material-symbols-outlined mr-2">download</span>
        Insérer les données (.xlsx)
        <input type="file" accept=".xlsx, .xls" @change="importCSV" class="hidden" />
      </label>

      <button class="menu-item" @click="downloadCSV">
        <span class="material-symbols-outlined mr-2">Database_Upload</span>
        Télécharger les données (.xlsx)
      </button>

      <button class="menu-item" @click="downloadPDF">
        <span class="material-symbols-outlined mr-2">calendar_today</span>
        Télécharger le planning (.pdf)
      </button>

      <div v-if="terrainErrors.length" class="bg-red-100 text-red-800 p-4 rounded mb-4">
        <p class="font-semibold mb-2">Erreurs détectées lors de l'import :</p>
        <ul class="list-disc list-inside text-sm">
          <li v-for="(err, index) in terrainErrors" :key="index">{{ err }}</li>
        </ul>
      </div>

      <!-- Section : Gestion des joueurs -->
      <div class="py-2 font-bold text-gray-700 flex items-center mt-4">
        <span class="material-symbols-outlined mr-2">group</span>
        Gestion des joueurs
      </div>

      <button class="menu-item" @click="togglePendingPlayers">
        <span class="material-symbols-outlined mr-2">person</span>
        Consulter les inscrits
      </button>

      <button class="menu-item" @click="askSendMailToAll">
        <span class="material-symbols-outlined mr-2">send</span>
        Envoyer le planning aux joueurs
      </button>

      <button class="menu-item" @click="askDeleteAllPlayers">
        <span class="material-symbols-outlined mr-2">delete</span>
        Supprimer tous les joueurs
      </button>

        <!-- Liste des inscrits en attente -->
        <PlayerNot v-if="showPendingPlayers" :pendingPlayers="pendingPlayers" @update:pendingPlayers="updatePendingPlayers" />


      </div>
    </div>
    <PopupMessage
        v-if="showPopup"
        :message="popupMessage"
        :type="popupType"
    />

    <ConfirmDialog
        :visible="showConfirm"
        :message="confirmMessage"
        @cancel="showConfirm = false"
        @confirm="confirmYes"
    />
  </div>
</template>


<script>
import {computed, onBeforeUnmount, onMounted, ref, watch} from "vue";
import useTerrain from "../../useJs/useTerrain.js";
import useLeftPanel from "../../useJs/useLeftPanel.js";
import useSessionConstraint from "../../useJs/useSessionConstraint.js";
import { usePlayersStore } from "../../store/usePlayersStore.js";
import { useTrainersStore } from "../../store/useTrainersStore.js";
import PlayersService from "../../services/PlayersService.js";
import ExportService from "../../functionality/ExportService";
import ImportService from "../../functionality/ImportService";
import ExportPdf from "../../functionality/ExportPdf";
import Players from "./Players.vue";
import Trainers from "./Trainers.vue";
import Terrains from "./Terrain.vue";
import Session from "./Session.vue";
import PlayerNot from "../vueInformations/PlayerNot.vue";
import PopupMessage from "../../components/PopupMessage.vue";
import ConfirmDialog from "../../components/ConfirmDialog.vue";
import { useSessionsStore } from "../../store/useSessionsStore";




export default {
  name: "LeftPanel",
  components: {
    PlayerNot,
    Trainers,
    Players,
    Terrains,
    Session,
    PopupMessage,
    ConfirmDialog
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
    // We'll use the loading states from the stores
    const playersStore = usePlayersStore();
    const trainersStore = useTrainersStore();


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
    // We'll use searchQuery and selectedTab from useLeftPanel but players and trainers will come from the stores
    const { searchQuery, selectedTab, selectTab } = useLeftPanel();
    const { pendingPlayers, fetchPendingPlayers } = usePlayersStore();
    // Get references to the data from the stores
    const players = computed(() => playersStore.players);
    const trainers = computed(() => trainersStore.trainers);

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

    onMounted(() => {
      // Fetch data from the stores
      trainersStore.fetchTrainers();
      playersStore.fetchPlayers();
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
      selectTab,
      togglePendingPlayers,
      validatePlayer,
      updatePendingPlayers,
      getJourLabel,
      playersStore,
      trainersStore,
    };
  },

  data() {
    return {
      isTablet: false,
      showPopup: false,
      popupMessage: "",
      popupType: "success",
      showConfirm: false,
      confirmMessage: "",
      confirmAction: null,

    };
  },

  methods: {
    askDeleteAllPlayers() {
      this.confirmMessage = "Êtes-vous sûr de vouloir supprimer tous les joueurs ?";
      this.confirmAction = this.deleteAllPlayers;
      this.showConfirm = true;
    },

    askSendMailToAll() {
      this.confirmMessage = "Voulez-vous envoyer le planning à tous les joueurs ?";
      this.confirmAction = this.sendReinscriptionMail;
      this.showConfirm = true;
    },

    confirmYes() {
      if (this.confirmAction) this.confirmAction();
      this.showConfirm = false;
    },

    async importCSV(event) {
      const file = event.target.files[0];
      if (!file) {
        this.popupMessage = "Veuillez sélectionner un fichier.";
        this.popupType = "error";
        this.showPopup = true;
        return;
      }

      try {
        const result = await ImportService.importExcel(file);
        this.terrainErrors = result.terrainErrors || [];

        if (this.terrainErrors.length > 0) {
          this.selectTab('settings');
          this.popupMessage = "Import terminé avec des erreurs.";
          this.popupType = "warning";
        } else {
          this.popupMessage = "Importation des données réussie !";
          this.popupType = "success";
        }

        this.showPopup = true;

      } catch (error) {
        console.error("Erreur lors de l'importation :", error);
        this.terrainErrors = [
          "Une erreur est survenue pendant l'importation.",
          error.message || ""
        ];
        this.popupMessage = "Erreur lors de l'importation.";
        this.popupType = "error";
        this.showPopup = true;
      }
    },

    async downloadPDF() {
      try {
        await ExportPdf.generateSessionsPdf();
        this.popupMessage = "Le planning a été exporté en PDF avec succès.";
        this.popupType = "success";
        this.showPopup = true;
      } catch (error) {
        console.error("Erreur lors de l'exportation du PDF :", error);
        this.popupMessage = "Erreur lors de l'exportation du planning.";
        this.popupType = "error";
        this.showPopup = true;
      }
    },


    async downloadCSV() {
        const { sessionConstraints, fetchSessionConstraints } = useSessionConstraint();
        await fetchSessionConstraints();
        await ExportService.downloadCSV(this.players, this.trainers, this.terrains, sessionConstraints.value);
      this.popupMessage = "Données exportées avec succès !";
      this.popupType = "success";
      this.showPopup = true;
    },

    async sendReinscriptionMail() {
      try {
        const sessionsStore = useSessionsStore();
        await sessionsStore.sendSessionMails();

        this.popupMessage = "Les plannings ont bien été envoyés aux joueurs.";
        this.popupType = "success";
        this.showPopup = true;
      } catch (error) {
        this.popupMessage = "Erreur lors de l'envoi des plannings.";
        this.popupType = "error";
        this.showPopup = true;
      }
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

:root {
  --accent: #528359;
}

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
  background-color: #528359;
  transition: width 0.3s ease-in-out;
}

.tab-button.active {
  color: #2f855a;
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

.menu-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.6rem 1rem;
  background-color: white;
  color: var(--accent);
  border: 1px solid var(--accent);
  border-radius: 6px;
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  width: 100%;
  margin-bottom: 0.5rem;
}

.menu-item:hover {
  background-color: #e6f4eb;
}

.menu-item .material-symbols-outlined {
  font-size: 1.3rem;
}

</style>



