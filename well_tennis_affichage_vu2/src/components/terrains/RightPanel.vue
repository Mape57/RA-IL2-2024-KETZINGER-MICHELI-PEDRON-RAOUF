<template>
  <div
      class="right-panel relative lg:fixed top-0 bg-white z-10 flex flex-col p-5 sm:w-[90%] lg:w-[67%] sm:rounded-lg sm:shadow-md"
  >
    <!-- Mode Mobile -->
    <div v-if="isMobile" class="flex flex-col h-full">
      <!-- Barre du haut pour le mobile -->
      <div class="flex items-center justify-between p-4 bg-[#fefdf8] border-b border-gray-300">
        <button
            @click="openFilter"
            class="flex items-center text-sm text-black font-semibold hover:text-green-700 transition"
        >
          <span class="material-symbols-outlined text-base mr-1">tune</span>
          <span>Filtrer</span>
        </button>
        <div class="text-center text-lg font-bold text-[#528359]">
          {{ selectedTerrainName }}
        </div>
        <div class="w-8"></div>
      </div>

      <!-- Contenu pour mobile -->
      <div class="content flex-1 overflow-auto p-4">
        <div v-for="(sessions, day) in sessionsByDay" :key="day" class="mb-6">
          <h2 class="text-[#528359] text-xl font-bold mb-4">{{ day }}</h2>
          <SessionCard
              v-for="session in sessions"
              :key="session.id"
              :startTime="session.startTime"
              :endTime="session.endTime"
              :coach="session.coach"
              :ageGroup="session.ageGroup"
              :skillLevel="session.skillLevel"
              :players="session.players"
          />
        </div>
      </div>
    </div>

    <!-- Mode Bureau -->
    <div v-else class="flex flex-col flex-1 w-full h-full">
      <!-- Barre supérieure avec les onglets des terrains et le bouton "Filtrer" -->
      <div class="flex items-center justify-center p-4 bg-[#fefdf8] border-b border-gray-300">
        <!-- Onglets des terrains -->
        <div class="flex justify-center space-x-8">
          <button
              v-for="terrain in sortedTerrains"
              :key="terrain.id"
              @click="selectTerrain(terrain.id)"
              :class="{ active: selectedTerrain === terrain.id }"
              class="tab-button"
          >
            {{ terrain.name }}
          </button>
        </div>

        <!-- Bouton "Filtrer" aligné à droite -->
        <div class="absolute right-5">
          <button
              @click="openFilter"
              class="flex items-center text-sm text-black font-semibold hover:text-green-700 transition"
          >
            <span>Filtrer</span>
            <span class="material-symbols-outlined text-base ml-1">tune</span>
          </button>
        </div>
      </div>

      <!-- Contenu principal pour bureau -->
      <div class="content flex-1 overflow-auto p-4">
        <div v-for="(sessions, day) in sessionsByDay" :key="day" class="mb-6">
          <h2 class="text-[#528359] text-xl font-bold mb-4">{{ day }}</h2>
          <SessionCard
              v-for="session in sessions"
              :key="session.id"
              :startTime="session.startTime"
              :endTime="session.endTime"
              :coach="session.coach"
              :ageGroup="session.ageGroup"
              :skillLevel="session.skillLevel"
              :players="session.players"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SessionCard from "./SessionCard.vue";
import terrainService from "../../services/terrainService";
import sessionsService from "../../services/sessionService";

export default {
  name: "RightPanel",
  components: {
    SessionCard,
  },
  data() {
    return {
      terrains: [],
      sessions: [],
      selectedTerrain: null,
      isMobile: false,
    };
  },
  computed: {
    sortedTerrains() {
      return this.terrains.slice().sort((a, b) => {
        const nameA = a.name.toUpperCase();
        const nameB = b.name.toUpperCase();
        return nameA.localeCompare(nameB, undefined, { numeric: true });
      });
    },
    selectedTerrainName() {
      const terrain = this.terrains.find((t) => t.id === this.selectedTerrain);
      return terrain ? terrain.name : "Terrain non sélectionné";
    },
    sessionsByDay() {
      const daysOfWeek = ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"];
      const groupedSessions = {};
      daysOfWeek.forEach((day) => {
        groupedSessions[day] = [];
      });
      this.sessions
          .filter((session) => session.idCourt?.id === this.selectedTerrain)
          .forEach((session) => {
            const day = session.day;
            groupedSessions[day]?.push(session);
          });
      return groupedSessions;
    },
  },
  methods: {
    async loadTerrainsAndSessions() {
      try {
        const terrainResponse = await terrainService.getAllTerrain();
        this.terrains = terrainResponse.data;

        const sessionResponse = await sessionsService.getAllSessions();
        this.sessions = sessionResponse.data;

        this.selectedTerrain = this.sortedTerrains[0]?.id || null;
      } catch (error) {
        console.error("Erreur lors du chargement des données :", error);
      }
    },
    openFilter() {
      console.log("Ouverture du filtre");
    },
    checkScreenSize() {
      this.isMobile = window.innerWidth < 1024;
    },
  },
  mounted() {
    this.loadTerrainsAndSessions();
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
};
</script>

<style scoped>
.right-panel {
  background-color: white;
  height: calc(100vh - 6.8rem);
  overflow-y: auto;
  margin-top: 1.2rem;
  margin-right: 0.9rem;
}

@media (min-width: 1024px) {
  .right-panel {
    width: 67%;
    position: relative;
    top: 0;
    right: 0;
    margin-left: auto;
  }
}

.tab-button {
  color: gray;
  font-weight: bold;
  transition: all 0.3s ease-in-out;
  padding: 0.5rem 1rem;
  border-bottom: 2px solid transparent;
  cursor: pointer;
}

.tab-button.active {
  color: #528359;
  border-bottom: 2px solid #528359;
}

button:focus {
  outline: none;
  box-shadow: 0 0 0 2px #528359;
}
</style>


