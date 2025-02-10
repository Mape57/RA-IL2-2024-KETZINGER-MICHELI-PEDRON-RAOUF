<template>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <div>
    <div
        v-if="!isMobile"
        class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 shadow-sm mb-4 flex lg:flex-row items-center"
    >
      <div class="text-[#528359] font-bold text-lg lg:w-[15%] text-center lg:border-r border-gray-800 lg:pr-4 mb-4 lg:mb-0 flex flex-col justify-center items-center">
        <p>{{ startTime }}</p>
        <p>{{ endTime }}</p>
      </div>

      <div class="w-[25%] h-[20%] lg:pl-2 mb-4 lg:mb-0 flex flex-col justify-center">
        <div class="mb-2">
          <span class="font-bold text-sm">Entraîneur :</span> {{ coach }}
        </div>
        <div class="mb-2">
          <span class="font-bold text-sm">Âge :</span> {{ ageGroup }} ans
        </div>
        <div>
          <span class="font-bold text-sm">Niveau :</span> {{ skillLevel }}
        </div>
      </div>

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

      <div class="lg:w-[10%] flex justify-end">
        <button
            @click="$emit('delete')"
            class="text-red-500 hover:underline text-sm flex items-center"
        >
          <span class="material-icons text-base mr-1">delete</span> Supprimer
        </button>
      </div>
    </div>

    <div
        v-else
        class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 shadow-sm mb-4 flex flex-col"
    >
      <div class="flex justify-between items-center border-b border-gray-300 pb-2 mb-2">
        <div class="text-[#528359] font-bold text-lg">
          {{ coach }}
        </div>
        <div class="text-[#528359] text-sm font-medium" @click="showInfo = !showInfo">
          {{ showInfo ? (ageGroup + ' ans, Niveau: ' + skillLevel) : (startTime + ' - ' + endTime) }}
        </div>
      </div>

      <div class="grid no-gap-grid">
        <span
            v-for="(player, index) in players"
            :key="index"
            class="text-sm text-gray-700"
        >
          • {{ player }}
        </span>
      </div>

      <div class="mt-1 flex justify-end">
        <button
            @click="$emit('delete')"
            class="text-red-500 hover:underline text-sm flex items-center"
        >
          <span class="material-icons small-icon mr-1">delete</span>
          Supprimer
        </button>
      </div>
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
  data() {
    return {
      isMobile: false,
      showInfo: false,
    };
  },
  methods: {
    checkScreenSize() {
      this.isMobile = window.innerWidth <= 1024;
    },
  },
  mounted() {
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.checkScreenSize);
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

.text-gray-700 {
  margin: 0;
  padding: 0;
  text-align: left;
}

.no-gap-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  column-gap: 0;
  row-gap: 0;
}

.text-sm {
  margin: 0;
  padding: 0;
  text-align: left;
}

@media (max-width: 1024px) {
  .session-card {
    flex-direction: column;
    padding: 1rem;
  }
}

@media (max-width: 768px) {
  .small-icon {
    font-size: 0.875rem;
  }

  .session-card {
    padding: 0.8rem;
  }
}

@media (max-width: 480px) {
  .session-card {
    padding: 1rem;
  }
}

</style>
