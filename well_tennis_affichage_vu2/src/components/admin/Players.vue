<template>
  <div class="players-table">
    <!-- En-tête du tableau -->
    <div class="table-header flex justify-between border-b border-gray-300 pb-2">
      <div class="flex-1 font-bold text-sm">Nom Prénom</div>
      <div class="flex-1 text-center font-bold text-sm">Âge</div>
      <div class="flex-1 text-right font-bold text-sm">Niveau</div>
    </div>

    <!-- Liste des joueurs -->
    <div
        v-for="player in players"
        :key="player.id"
        class="table-row flex items-center justify-between border-b border-gray-200 py-2"
    >
      <!-- Nom du joueur -->
      <div class="flex-1 text-gray-800">{{ player.name }}</div>

      <!-- Âge du joueur -->
      <div class="flex-1 text-center text-gray-600">{{ player.birthday }} </div>

      <!-- Niveau du joueur -->
      <div class="flex-1 text-right text-gray-600">{{ player.level }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Players",
  props: {
    players: Array,
  },
};
</script>

<style scoped>
.players-table {
  display: flex;
  flex-direction: column;
  font-size: 0.875rem; /* Taille de police légèrement réduite */
}

.table-header {
  font-weight: bold;
  color: #4a5568; /* Couleur grise pour l'en-tête */
}

.table-row {
  transition: background-color 0.2s ease-in-out;
}

.table-row:hover {
  background-color: #f7fafc; /* Couleur de fond au survol */
}
</style>
