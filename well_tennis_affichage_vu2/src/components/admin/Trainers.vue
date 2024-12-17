<template>
  <div class="trainers-table">
    <!-- En-tête du tableau -->
    <div class="table-header flex justify-between border-b border-gray-300 pb-2">
      <div class="flex-1 font-bold text-sm">Nom</div>
      <div class="flex-1 font-bold text-sm">Prénom</div>
      <div class="flex-1 text-center font-bold text-sm">Tranche d'âge</div>
      <div class="flex-1 text-right font-bold text-sm">Niveau</div>
    </div>

    <!-- Liste des entraîneurs -->
    <div
        v-for="trainer in trainers"
        :key="trainer.id"
        class="table-row flex items-center justify-between border-b border-gray-200 py-2"
    >
      <!-- Nom de l'entraîneur -->
      <div class="flex-1 text-gray-800">{{ trainer.name }}</div>

      <!-- Prénom de l'entraîneur -->
      <div class="flex-1 text-gray-800">{{ trainer.surname}}</div>

      <!-- Tranche d'âge -->
      <div class="flex-1 text-center text-gray-600">{{ trainer.ages}}</div>

      <!-- Niveau -->
      <div class="flex-1 text-right text-gray-600">{{ trainer.levels}}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Trainers",
  props: {
    trainers: Array, // Liste des entraîneurs passée depuis le parent
  },
};
</script>

<style scoped>
.trainers-table {
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
