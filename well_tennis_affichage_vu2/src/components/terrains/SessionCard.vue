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
    <div class="flex-1 pl-2 ml-0">
      <span class="font-bold text-sm">Joueurs :</span>
      <div class="flex mt-1">
        <div class="pr-1">
          <ul class="list-disc list-inside text-sm text-gray-700">
            <li v-for="(player, index) in players.slice(0, 4)" :key="index">{{ player }}</li>
          </ul>
        </div>

        <div class="w-1/2 pl-2">
          <ul class="list-disc list-inside text-sm text-gray-700">
            <li v-for="(player, index) in players.slice(4)" :key="index">{{ player }}</li>
          </ul>
        </div>
      </div>
    </div>

    <div class="ml-auto flex items-center">
  <span
      @click="$emit('delete')"
      class="text-red-500 text-sm flex items-center cursor-pointer group"
  >
    <span class="hover:underline">Supprimer</span>
    <span
        class="material-symbols-outlined small-icon ml-1"
        title="Supprimer"
    >
      delete
    </span>
  </span>
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
    trainer: {
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

.flex {
  display: flex;
  flex-wrap: wrap;
}

.pl-2 {
  padding-left: 0.5rem;
}

.small-icon {
  font-size: 15px;
}

</style>
