<template>
  <div class="right-panel rounded-lg shadow-md ">
    <!-- Mode Mobile -->
    <div v-if="isMobile" class="flex flex-col h-full w-full overflow-x-hidden">
      <!-- Barre du haut pour le mobile -->
      <div class="relative flex items-center p-4 bg-[white] border-b border-gray-300 w-full overflow-x-hidden">
        <!-- Sélection du terrain -->
        <select
            v-model="selectedTerrain"
            class="absolute left-1/2 transform -translate-x-1/2 text-lg font-bold text-[#528359] bg-transparent border-none focus:outline-none w-auto text-center"
        >
          <option v-for="terrain in sortedTerrains" :key="terrain.id" :value="terrain.id">
            {{ terrain.name }}
          </option>
        </select>

        <!-- Bouton "Filtrer" -->
        <button
            @click="openFilter"
            class="ml-auto flex items-center text-sm text-black font-semibold hover:text-green-700 transition"
        >
          <span class="material-symbols-outlined text-base mr-1">tune</span>
        </button>
      </div>

      <!-- Contenu pour mobile -->
      <div class="content flex-1 overflow-auto p-4 w-full overflow-x-hidden">
        <div v-for="(sessions, day) in sessionsByDay" :key="day" class="mb-6">
          <h2 class="text-[#528359] text-xl font-bold mb-4">{{ day }}</h2>
          <SessionCard
              v-for="session in sessions"
              :key="session.id"
              :startTime="session.start"
              :endTime="session.stop"
              :coach="session.idTrainer ? `${session.idTrainer.name} ${session.idTrainer.surname}` : 'Aucun entraîneur'"
              :ageGroup="session.idTrainer ? `${session.idTrainer.infAge} - ${session.idTrainer.supAge} ` : 'N/A'"
              :skillLevel="session.idTrainer ? `${session.idTrainer.infLevel} - ${session.idTrainer.supLevel}` : 'N/A'"
              :players="session.players.map(player => `${player.name} ${player.surname}`)"
          />
        </div>
      </div>
    </div>

    <!-- Mode Tablette -->
    <div v-else-if="isTablet" class="flex flex-col h-full w-full overflow-x-hidden">
      <div class="relative flex items-center p-4 bg-white border-b border-gray-300 w-full">
        <select
            v-model="selectedTerrain"
            class="absolute left-1/2 transform -translate-x-1/2 text-lg font-bold text-[#528359] bg-transparent border-none focus:outline-none w-auto text-center"
        >
          <option v-for="terrain in sortedTerrains" :key="terrain.id" :value="terrain.id">
            {{ terrain.name }}
          </option>
        </select>

        <button
            @click="openFilter"
            class="ml-auto flex items-center text-sm text-black font-semibold hover:text-green-700 transition"
        >
          <span class="material-symbols-outlined text-base mr-1">tune</span>
        </button>
      </div>

      <!-- Contenu principal tablette -->
      <div class="content flex-1 overflow-auto p-4 w-full">
        <div v-for="(sessions, day) in sessionsByDay" :key="day" class="mb-6">
          <h2 class="text-[#528359] text-xl font-bold mb-4">{{ day }}</h2>
          <SessionCard
              v-for="session in sessions"
              :key="session.id"
              :startTime="session.start"
              :endTime="session.stop"
              :coach="session.idTrainer ? `${session.idTrainer.name} ${session.idTrainer.surname}` : 'Aucun entraîneur'"
              :ageGroup="session.idTrainer ? `${session.idTrainer.infAge} - ${session.idTrainer.supAge}` : 'N/A'"
              :skillLevel="session.idTrainer ? `${session.idTrainer.infLevel} - ${session.idTrainer.supLevel}` : 'N/A'"
              :players="session.players.map(player => `${player.name} ${player.surname}`)"
          />
        </div>
      </div>
    </div>

    <!-- Mode Bureau -->
    <div v-else class="flex flex-col flex-1 w-full h-full overflow-x-hidden">
      <div class="flex items-center justify-center p-2 bg-white border-b border-gray-300 w-full overflow-x-hidden">
        <div class="flex justify-center space-x-4">
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

      <!-- Contenu principal -->
      <div class="content flex-1 overflow-auto p-4 w-full overflow-x-hidden">
        <div v-for="(sessions, day) in sessionsByDay" :key="day" class="mb-6">
          <h2 class="text-[#528359] text-xl font-bold mb-4">{{ day }}</h2>
          <SessionCard
              v-for="session in sessions"
              :key="session.id"
              :startTime="session.start"
              :endTime="session.stop"
              :coach="session.idTrainer ? `${session.idTrainer.name} ${session.idTrainer.surname}` : 'Aucun entraîneur'"
              :ageGroup="session.idTrainer ? `${session.idTrainer.infAge} - ${session.idTrainer.supAge}` : 'N/A'"
              :skillLevel="session.idTrainer ? `${session.idTrainer.infLevel} - ${session.idTrainer.supLevel}` : 'N/A'"
              :players="session.players.map(player => `${player.name} ${player.surname}`)"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { ref, computed, onMounted, onUnmounted } from "vue";
import SessionCard from "./SessionCard.vue";
import terrainService from "../../services/terrainService";
import sessionsService from "../../services/sessionService";

export default {
  name: "RightPanel",
  components: {
    SessionCard,
  },
  setup() {
    const terrains = ref([]);
    const sessions = ref([]);
    const selectedTerrain = ref(null);
    const windowWidth = ref(window.innerWidth);

    // Détection du mode mobile
    const isMobile = computed(() => windowWidth.value < 768);
    const isTablet = computed(() => windowWidth.value >= 768 && windowWidth.value < 1024);

    // Trie les terrains par ordre alphabétique
    const sortedTerrains = computed(() => {
      return terrains.value.slice().sort((a, b) => a.name.localeCompare(b.name, undefined, { numeric: true }));
    });

    // Sessions groupées par jour
    const sessionsByDay = computed(() => {
      const daysOfWeek = ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"];
      const groupedSessions = Object.fromEntries(daysOfWeek.map((day) => [day, []]));

      if (!selectedTerrain.value) return groupedSessions;

      sessions.value
          .filter((session) => session.idCourt?.id === selectedTerrain.value)
          .forEach((session) => {
            const dayIndex = session.dayWeek - 1; // Convertit 1-7 en index 0-6
            if (daysOfWeek[dayIndex]) {
              groupedSessions[daysOfWeek[dayIndex]].push(session);
            }
          });

      return groupedSessions;
    });

    // Récupération des terrains et sessions depuis l'API
    const loadTerrainsAndSessions = async () => {
      try {
        const [terrainResponse, sessionResponse] = await Promise.all([
          terrainService.getAllTerrain(),
          sessionsService.getAllSessions(),
        ]);

        terrains.value = terrainResponse.data;
        sessions.value = sessionResponse.data;


        selectedTerrain.value = sortedTerrains.value[0]?.id || null;
      } catch (error) {
        console.error("Erreur lors du chargement des données :", error);
      }
    };


    // Sélectionne un terrain
    const selectTerrain = (id) => {
      selectedTerrain.value = id;
    };

    // Met à jour la taille de l'écran
    const updateWindowSize = () => {
      windowWidth.value = window.innerWidth;
    };

    onMounted(() => {
      loadTerrainsAndSessions();
      window.addEventListener("resize", updateWindowSize);
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateWindowSize);
    });

    return {
      terrains,
      sessions,
      selectedTerrain,
      sortedTerrains,
      sessionsByDay,
      isMobile,
      isTablet,
      selectTerrain,
    };
  },
};
</script>

<style scoped>
.right-panel {
  background-color: white;
  overflow-y: auto;
  margin-top: 1.2rem;
  margin-right: 1.37rem;
}

.tab-button {
  color: gray;
  font-weight: bold;
  padding: 0.5rem 1rem;
  border-bottom: 2px solid transparent;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.tab-button.active {
  color: #528359;
  border-bottom: 2px solid #528359;
}

button:focus {
  outline: none;
  box-shadow: none;
}

select {
  background: transparent;
  border: none;
  font-weight: bold;
  font-size: 1rem;
  cursor: pointer;
  color: #528359;
}

select:focus {
  outline: none;
}

body {
  font-size: 16px;
}

@media (max-width: 768px) {
  body {
    font-size: 14px;
  }

  .right-panel {
    padding: 1rem;
    width: 100%;
  }

  .tab-button {
    padding: 0.5rem;
    font-size: 0.875rem;
  }

  .content {
    padding: 1rem;
  }

  select {
    font-size: 1rem;
  }

  .material-symbols-outlined {
    font-size: 1rem;
  }
}

@media (min-width: 768px) and (max-width: 1024px) {
  .right-panel {
    width: 54%;
    height: 80vh;
    margin-left: auto;
    margin-right: 2%;
    margin-top: 17px;

  }

  .content {
    padding: 1.5rem;
  }

  .tab-button {
    padding: 0.75rem;
    font-size: 1rem;
  }
}

@media (min-width: 800px) and (max-width: 809px) {
  .right-panel {
    width: 54%;
    height: 83vh;
    margin-left: auto;
    margin-right: 2%;
    margin-top: 17px;
  }

  .content {
    padding: 1.5rem;
  }

  .tab-button {
    padding: 0.75rem;
    font-size: 1rem;
  }
}


@media (min-width: 1024px) {
  .right-panel {
    width: 67%;
    height: 91vh;
    position: relative;
    top: 0;
    right: 0;
    margin-left: auto;
  }
}

</style>

