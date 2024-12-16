<template>
  <div class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 flex items-center shadow-sm mb-4">
    <!-- Heures -->
    <div class="text-[#528359] font-bold text-lg w-[10%] text-center border-r border-gray-800 pr-6">
      <p>{{ startTime }}</p>
      <p>{{ endTime }}</p>
    </div>

    <!-- Détails principaux -->
    <div class="flex-1 pl-4">
      <!-- Entraîneur -->
      <div class="mb-2">
        <span class="font-bold text-sm">Entraîneur :</span> {{ coach }}
      </div>

      <!-- Groupe d'âge -->
      <div class="mb-2">
        <span class="font-bold text-sm">Âge :</span> {{ ageGroup }}
      </div>

      <!-- Niveau -->
      <div>
        <span class="font-bold text-sm">Niveau :</span> {{ skillLevel }}
      </div>
    </div>

    <!-- Liste des joueurs -->
    <div class="flex-1 pl-4">
      <span class="font-bold text-sm">Joueurs :</span>
      <ul class="list-disc list-inside text-sm text-gray-700 mt-1">
        <li v-for="player in players" :key="player">{{ player }}</li>
      </ul>
    </div>

    <!-- Actions -->
    <div class="ml-auto">
      <button
          @click="$emit('edit')"
          class="text-blue-500 text-sm px-2 py-1 border border-blue-500 rounded hover:bg-blue-100 transition"
      >
        Modifier
      </button>
      <button
          @click="$emit('delete')"
          class="text-red-500 text-sm px-2 py-1 border border-red-500 rounded ml-2 hover:bg-red-100 transition"
      >
        Supprimer
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "SessionCard",
  props: {
    startTime: {
      type: String,
      required: true,
    },
    endTime: {
      type: String,
      required: true,
    },
    coach: {
      type: String,
      required: true,
    },
    ageGroup: {
      type: String,
      required: true,
    },
    skillLevel: {
      type: String,
      required: true,
    },
    players: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped>
.session-card {
  transition: transform 0.2s ease-in-out;
}
.session-card:hover {
  transform: translateY(-2px);
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
</style>
