<template>
  <div class="left-panel bg-white rounded-lg shadow-md w-[30%] h-[85vh] p-6 mt-5 flex flex-col">
    <!-- Onglets pour "Données" et "Contraintes" -->
    <div class="tabs flex space-x-6 mb-4">
      <button
          @click="selectTab('data')"
          :class="{ active: selectedTab === 'data' }"
          class="tab-button"
      >
        <span class="material-symbols-outlined mr-2">database</span>Données
      </button>
      <button
          @click="selectTab('constraints')"
          :class="{ active: selectedTab === 'constraints' }"
          class="tab-button"
      >
        <span class="material-symbols-outlined mr-2">gavel</span>Contraintes
      </button>
    </div>

    <!-- Contenu dynamique basé sur l'onglet sélectionné -->
    <div class="content flex-1 overflow-auto">
      <!-- Contenu de l'onglet "Données" -->
      <div v-if="selectedTab === 'data'">
        <Accordion title="Entraîneurs">
          <Trainers :trainers="trainers" @deleteTrainer="deleteTrainer" />
        </Accordion>
        <Accordion title="Joueurs">
          <Players :players="players" @deletePlayer="deletePlayer" />
        </Accordion>
      </div>

      <!-- Contenu de l'onglet "Contraintes" -->
      <div v-if="selectedTab === 'constraints'">
        <Accordion title="Terrains">
          <p class="text-gray-500 text-sm">Les contraintes liées aux terrains s'affichent ici.</p>
        </Accordion>
        <Accordion title="Séances">
          <p class="text-gray-500 text-sm">Les contraintes liées aux séances s'affichent ici.</p>
        </Accordion>
      </div>
    </div>
  </div>
</template>

<script>
import Accordion from '../shared/Accordion.vue';
import Trainers from './Trainers.vue';
import Players from './Players.vue';
import trainersService from "../../services/trainersService.js";
import playersService from "../../services/playersService.js";

export default {
  name: "LeftPanel",
  components: {
    Accordion,
    Trainers,
    Players,
  },
  data() {
    return {
      selectedTab: "data", // Onglet actif par défaut
      trainers: [],        // Liste des entraîneurs
      players: [],         // Liste des joueurs
    };
  },
  created() {
    this.fetchTrainers();
    this.fetchPlayers();
  },
  methods: {
    selectTab(tab) {
      this.selectedTab = tab; // Change l'onglet actif
    },
    deleteTrainer(trainerId) {
      this.trainers = this.trainers.filter((trainer) => trainer.id !== trainerId); // Supprime un entraîneur
    },
    deletePlayer(playerId) {
      this.players = this.players.filter((player) => player.id !== playerId); // Supprime un joueur
    },
    async fetchTrainers() {
      try {
        const response = await trainersService.getAllTrainers();
        this.trainers = response.data; // Stocke les entraîneurs
      } catch (error) {
        console.error("Erreur lors de la récupération :", error);
      }
    },
    async fetchPlayers() {
      try {
        const response = await playersService.getAllPlayers();
        this.players = response.data; // Stocke les joueurs
      } catch (error) {
        console.error("Erreur lors de la récupération :", error);
      }
    },
  },
};
</script>

<style scoped>
/* Style des onglets */
.tab-button {
  color: gray;
  font-weight: bold;
  transition: all 0.3s ease-in-out;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0.5rem 1rem;
  border-bottom: 2px solid transparent;
  cursor: pointer;
}

.tab-button.active {
  color: #528359;
  border-bottom-color: #528359;
}
</style>
