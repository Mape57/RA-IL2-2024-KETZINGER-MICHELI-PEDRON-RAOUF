<template>
  <!-- Conteneur principal -->
  <div>
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <!-- Titre Joueurs -->
      <div class="flex items-center">
        <span
            :class="{ 'rotate-180': isOpen }"
            class="material-symbols-outlined transition-transform duration-300 mr-2"
        >
          expand_more
        </span>
        <h3 class="font-bold text-lg">Joueurs</h3>
      </div>
      <!-- Boutons d'action -->
      <div class="flex space-x-2">
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Supprimer">delete</span>
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Ajouter">person_add</span>
      </div>
    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <!-- En-têtes des colonnes -->
      <div class="grid grid-cols-3 font-semibold text-gray-400 text-sm mb-2">
        <div>Nom</div>
        <div class="text-center"> Prénom</div>
        <div class="text-center">Âge</div>
        <div class="text-right">Niveau</div>
      </div>

      <div v-for="player in filteredPlayers"
           :key="player.id"
           class="grid grid-cols-3 items-center py-1">
        <!-- Nom du joueur -->
        <span>{{ player.name }}</span>
        <!-- Prénom du joueur -->
        <span>{{ player.surname }}</span>
        <!-- Âge du joueur -->
        <span class="text-sm text-gray-600 text-center">{{  player.birthday || 'N/A' }} ans</span>
        <!-- Niveau du joueur -->
        <span class="text-sm text-gray-600 text-right">{{ player.level }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Players",
  props: {
    players: Array,
    searchQuery: String,
  },
  data() {
    return {
      isOpen: true,
    };
  },
  computed: {
    filteredPlayers() {
      return this.players.filter(player =>
          player.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          player.surname.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    toggleAccordion() {
      this.isOpen = !this.isOpen;
    },
  },
};
</script>

<style scoped>
.grid {
  grid-template-columns: 2fr 1fr 1fr;
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
