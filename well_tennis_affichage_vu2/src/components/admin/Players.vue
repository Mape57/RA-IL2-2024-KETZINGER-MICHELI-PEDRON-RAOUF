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
      />
    </div>
  </div>
</template>

<script>
import usePlayers from "../../useJs/usePlayers.js";
import PlayerInfoView from "./PlayerInfoView.vue";

export default {
  name: "Players",
  components: { PlayerInfoView },
  props: {
    players: Array,
    searchQuery: String,
  },
  setup() {
    const { computeAge } = usePlayers();
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
    toggleAccordion() {
      this.isOpen = !this.isOpen;
    },
    showPlayerInfo(player) {
      this.selectedPlayer = player; // Met à jour le joueur sélectionné
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

.small-icon {
  font-size: 18px;
}

.rotate-180 {
  transform: rotate(180deg);
}

.border-b {
  border-bottom: 1px solid #e2e8f0;
}
</style>
