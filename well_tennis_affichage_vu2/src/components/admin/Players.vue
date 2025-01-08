<template>
  <div>
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <div class="flex items-center">
        <span
            :class="{ 'rotate-180': isOpen }"
            class="material-symbols-outlined transition-transform duration-300 mr-2"
        >
          expand_more
        </span>
        <h3 class="font-bold text-lg">Joueurs</h3>
      </div>
      <div class="flex space-x-2">
        <span class="material-symbols-outlined small-icon cursor-pointer"
              title="Ajouter"
              ref="addPlayerButton"
              @click="addPlayer"
        >person_add</span>
      </div>
    </div>

    <div v-if="isOpen" class="mt-2">
      <div class="grid grid-cols-4 font-semibold text-gray-400 text-sm mb-2">
        <div class="text-left">Nom</div>
        <div class="text-center">Prénom</div>
        <div class="text-center">Âge</div>
        <div class="text-right">Niveau</div>
      </div>

      <div
          v-for="player in filteredPlayers"
          :key="player.id"
          class="grid grid-cols-4 items-center py-1 cursor-pointer"
          @click="showPlayerInfo(player)"
      >
        <span>{{ player.name }}</span>
        <span>{{ player.surname }}</span>
        <span class="text-center">{{ computeAge(player.birthday) || "N/A" }} ans</span>
        <span class="text-right">{{ player.level }}</span>
      </div>


      <!-- Affichage de PlayerInfoView -->
      <PlayerInfoView
          v-if="selectedPlayer"
          :player="selectedPlayer"
          @close="selectedPlayer = null"
          @delete="handlePlayerDeletion"
          @save="handlePlayerSave"
      />

    </div>
  </div>
</template>

<script>
import usePlayers from "../../useJs/usePlayers.js";
import PlayerInfoView from "../vueInformations/PlayerInfoView.vue";

export default {
  name: "Players",
  components: {PlayerInfoView},
  props: {
    players: Array,
    searchQuery: String,
  },
  setup() {
    const {computeAge} = usePlayers();
    return {
      computeAge,
    };
  },
  data() {
    return {
      isOpen: true,
      selectedPlayer: null, // Joueur sélectionné pour afficher les détails
    };
  },
  computed: {
    filteredPlayers() {
      return this.players.filter(
          (player) =>
              player.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
              player.surname.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    toggleAccordion(event) {
      const addButton = this.$refs.addPlayerButton;

      // Si le bouton "Ajouter" est cliqué et que le panneau est déjà ouvert, ne pas refermer
      if (addButton && addButton.contains(event.target) && this.isOpen) {
        return;
      }

      // Sinon, basculer l'état d'ouverture
      this.isOpen = !this.isOpen;
    },
    showPlayerInfo(player) {
      if (this.selectedPlayer && this.selectedPlayer.id === player.id) {
        // Si le joueur sélectionné est cliqué à nouveau, on ferme l'onglet
        this.selectedPlayer = null;
      } else {
        // Sinon, on met à jour le joueur sélectionné
        this.selectedPlayer = player;
      }
    },
    handlePlayerDeletion(deletedPlayerId) {
      const updatedPlayers = this.players.filter(player => player.id !== deletedPlayerId);
      this.$emit('update:players', updatedPlayers); // Émet la liste mise à jour au parent
      this.selectedPlayer = null; // Ferme l'affichage des détails
    },
    handlePlayerSave(savedPlayer) {
      if (!savedPlayer || typeof savedPlayer !== "object") {
        console.error("Données invalides reçues dans handlePlayerSave :", savedPlayer);
        return;
      }
      const index = this.players.findIndex(player => player.id === savedPlayer.id);
      if (index !== -1) {
        // Mise à jour d'un joueur existant
        this.players.splice(index, 1, savedPlayer);
      } else {
        // Ajout d'un nouveau joueur
        this.players.push(savedPlayer);
      }

      // Émet la liste mise à jour au parent
      this.$emit("update:players", [...this.players]);
    },

    addPlayer() {
      // Initialise un joueur vide
      this.selectedPlayer = {
        id: null, // Pas encore défini
        name: "",
        surname: "",
        birthday: "",
        courses: 1,
        level: 1,
        email: "",
        disponibilities: [],
      }; // Ouvre PlayerInfoView avec ce nouveau joueur
    },
  },
};
</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
  gap: 0.5rem;
}

.material-symbols-outlined {
  cursor: pointer;
  transition: transform 0.3s ease;
}


.rotate-180 {
  transform: rotate(180deg);
}

.border-b {
  border-bottom: 1px solid #e2e8f0;
}


</style>
