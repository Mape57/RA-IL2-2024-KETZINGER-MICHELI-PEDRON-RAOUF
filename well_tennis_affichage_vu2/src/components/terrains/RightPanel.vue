
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
              @times-updated="handleTimesUpdated"
              :ageGroup="session.idTrainer ? `${session.idTrainer.infAge} - ${session.idTrainer.supAge} ` : 'N/A'"
              :skillLevel="session.idTrainer ? `${session.idTrainer.infLevel} - ${session.idTrainer.supLevel}` : 'N/A'"
              :players="session.players.map(player => `${player.name} ${player.surname}`)"
              :userRole="userRole"
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
              @times-updated="handleTimesUpdated"
              :ageGroup="session.idTrainer ? `${session.idTrainer.infAge} - ${session.idTrainer.supAge}` : 'N/A'"
              :skillLevel="session.idTrainer ? `${session.idTrainer.infLevel} - ${session.idTrainer.supLevel}` : 'N/A'"
              :players="session.players.map(player => `${player.name} ${player.surname}`)"
              :userRole="userRole"
          />
        </div>
      </div>
    </div>

    <!-- Mode Bureau -->
    <div v-else class="flex flex-col flex-1 w-full h-full overflow-x-hidden">
      <div class="flex items-center justify-center p-2 bg-white border-b border-gray-300 w-full overflow-x-hidden relative">
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

        <teleport to="body">
          <div
              v-if="showFilterPopup"
              class="fixed top-[65px] right-[40px] w-80 bg-white shadow-lg rounded-xl p-4 z-[9999] border border-gray-200"
          >
            <h3 class="text-lg font-semibold text-gray-800 mb-3">Filtrer Sessions</h3>

            <div class="space-y-3">
              <input
                  type="text"
                  v-model="trainerSearchQuery"
                  @input="filterSessions"
                  class="w-full p-2 border rounded-md focus:ring-2 focus:ring-green-500 outline-none"
                  placeholder="Nom de l'entraîneur"
              />
              <input
                  type="text"
                  v-model="playerSearchQuery"
                  @input="filterSessions"
                  class="w-full p-2 border rounded-md focus:ring-2 focus:ring-green-500 outline-none"
                  placeholder="Nom du joueur"
              />
              <input
                  type="number"
                  v-model="selectedLevel"
                  @input="filterSessions"
                  class="w-full p-2 border rounded-md focus:ring-2 focus:ring-green-500 outline-none"
                  placeholder="Niveau"
              />
              <input
                  type="number"
                  v-model="selectedAge"
                  @input="filterSessions"
                  class="w-full p-2 border rounded-md focus:ring-2 focus:ring-green-500 outline-none"
                  placeholder="Âge"
              />
            </div>

            <div class="flex justify-end mt-4">
              <button
                  @click="showAllSessions"
                  class="px-4 py-1 rounded-md text-white bg-green-700 hover:bg-green-800 text-sm"
              >
                Réinitialiser
              </button>
            </div>
          </div>
        </teleport>

      </div>

      <!-- Contenu principal -->
      <div class="content flex-1 overflow-auto p-4 w-full overflow-x-hidden">
        <div v-for="(sessions, day) in sessionsByDay" :key="day" class="mb-6">
          <h2 class="text-[#528359] text-xl font-bold mb-4">{{ day }}</h2>
          <SessionCard
              v-for="session in sessions"
              :key="session.id"
              :sessionId="session.id"
              :startTime="session.start"
              :endTime="session.stop"
              :coach="session.idTrainer"
              :ageGroup="calculateAgeGroup(session)"
              @times-updated="handleTimesUpdated"
              :skillLevel="calculateSkillLevel(session)"
              :players="session.players"
              :userRole="userRole"
              @update-players="updateSessionsPlayers"
              @delete="deleteSession(session.id)"
              @update-coach="handleCoachUpdate"
              @player-removed="handlePlayerRemoved"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import {ref, computed, onMounted, onUnmounted, watch} from "vue";
import SessionCard from "./SessionCard.vue";
import useTerrains from "../../useJs/useTerrain.js";
import { useSessionsStore } from "../../store/useSessionsStore.js";
import { usePlayersStore } from "../../store/usePlayersStore.js";
import { useTrainersStore } from "../../store/useTrainersStore.js";
import { storeToRefs } from "pinia";
import { useSessions } from "../../useJs/useSessions.js";
import terrainService from "../../services/TerrainService";
import sessionsService from "../../services/SessionService";
import trainerService from "../../services/TrainersService";
import playerService from "../../services/PlayersService";
import usePlayers from "../../useJs/usePlayers.js";
import useTrainers from "../../useJs/useTrainers.js";
import {getSportsAge} from "../../functionality/conversionUtils.js";



export default {
  name: "RightPanel",
  components: {
    SessionCard,
  },
  props: {
    isMobile: Boolean,
    isTablet: Boolean,
    userRole: String,
  },
  setup() {
    // Importation des stores Pinia
    const sessionsStore = useSessionsStore();
    const playersStore = usePlayersStore();
    const trainersStore = useTrainersStore();

    // Références réactives extraites des stores
    const { sessions } = storeToRefs(sessionsStore);
    const { players } = storeToRefs(playersStore);
    const { trainers } = storeToRefs(trainersStore);

    // Méthodes pour récupérer les données
    const fetchSessions = sessionsStore.fetchSessions;
    const updateSession = sessionsStore.updateSession;
    const fetchPlayers = playersStore.fetchPlayers;
    const fetchTrainers = trainersStore.fetchTrainers;

    // Terrains récupérés via un composable
    const { terrains, fetchTerrains } = useTerrains();

    // États de filtres et UI
    const selectedTerrain = ref(null);
    const trainerSearchQuery = ref("");
    const playerSearchQuery = ref("");
    const selectedLevel = ref(null);
    const selectedAge = ref(null);
    const showFilterPopup = ref(false);
    const windowWidth = ref(window.innerWidth);

    // Détection responsive
    const isMobile = computed(() => windowWidth.value < 768);
    const isTablet = computed(() => windowWidth.value >= 768 && windowWidth.value < 1024);

    // Tri alphabétique des terrains
    const sortedTerrains = computed(() => {
      return terrains.value.slice().sort((a, b) => a.name.localeCompare(b.name, undefined, { numeric: true }));
    });

    // Sessions regroupées par jour de la semaine + filtres appliqués
    const sessionsByDay = computed(() => {
      const daysOfWeek = ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"];
      const groupedSessions = Object.fromEntries(daysOfWeek.map(day => [day, []]));

      // Si aucun terrain n’est sélectionné, on prend le premier disponible
      if (!selectedTerrain.value && sortedTerrains.value.length > 0) {
        selectedTerrain.value = sortedTerrains.value[0]?.id || null;
      }

      if (!sessions.value || sessions.value.length === 0) return groupedSessions;

      sessions.value
          .filter((session) => session.idCourt?.id === selectedTerrain.value)
          .filter((session) => !trainerSearchQuery.value || session.idTrainer?.name.toLowerCase().includes(trainerSearchQuery.value.toLowerCase()) || session.idTrainer?.surname.toLowerCase().includes(trainerSearchQuery.value.toLowerCase()))
          .filter((session) => !playerSearchQuery.value || session.players.some(player => player.name.toLowerCase().includes(playerSearchQuery.value.toLowerCase()) || player.surname.toLowerCase().includes(playerSearchQuery.value.toLowerCase())))
          .filter((session) => !selectedLevel.value || (session.idTrainer?.infLevel <= selectedLevel.value && session.idTrainer?.supLevel >= selectedLevel.value))
          .filter((session) => !selectedAge.value || (session.idTrainer?.infAge <= selectedAge.value && session.idTrainer?.supAge >= selectedAge.value))
          .forEach((session) => {
            const dayIndex = session.dayWeek - 1;
            if (daysOfWeek[dayIndex]) {
              groupedSessions[daysOfWeek[dayIndex]].push(session);
            }
          });

      // Tri des sessions dans chaque journée par heure de début
      Object.keys(groupedSessions).forEach(day => {
        groupedSessions[day].sort((a, b) => a.start.localeCompare(b.start));
      });

      return groupedSessions;
    });

    // Sélection d’un terrain
    const selectTerrain = (id) => {
      selectedTerrain.value = id;
    };

    // Mise à jour de la largeur de l'écran
    const updateWindowSize = () => {
      windowWidth.value = window.innerWidth;
    };

    // Affichage / Masquage du panneau de filtres
    const openFilter = () => {
      showFilterPopup.value = !showFilterPopup.value;
    };
    const closeFilterPopup = () => {
      showFilterPopup.value = false;
    };
    const filterSessions = () => {}; // placeholder

    // Réinitialiser tous les filtres
    const showAllSessions = () => {
      trainerSearchQuery.value = "";
      playerSearchQuery.value = "";
      selectedLevel.value = null;
      selectedAge.value = null;
    };

    // Niveaux de sessions (pour les filtres)
    const sessionLevels = computed(() => {
      const levels = new Set();
      sessions.value.forEach(session => {
        if (session.idTrainer) {
          levels.add(JSON.stringify({
            infLevel: session.idTrainer.infLevel,
            supLevel: session.idTrainer.supLevel
          }));
        }
      });
      return Array.from(levels).map(level => JSON.parse(level)).sort((a, b) => a.infLevel - b.infLevel);
    });

    // Tranches d'âge détectées dans les sessions
    const sessionAges = computed(() => {
      const ages = new Set();
      sessions.value.forEach(session => {
        if (session.idTrainer) {
          ages.add(session.idTrainer.age);
        }
      });
      return Array.from(ages).sort((a, b) => a - b);
    });

    // Chargement initial des données + gestion resize
    onMounted(() => {
      fetchTerrains();
      fetchSessions();
      fetchPlayers();
      fetchTrainers();
      window.addEventListener("resize", updateWindowSize);
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateWindowSize);
    });

    // Lorsqu'une session est modifiée (horaires)
    const handleTimesUpdated = async ({ sessionId }) => {
      await fetchSessions();
      const updatedSession = sessions.value.find(s => s.id === sessionId);
      if (updatedSession) {
        const playerIds = updatedSession.players.map(p => p.id);
        const trainerId = updatedSession.idTrainer?.id;
        if (playerIds.length > 0) await playersStore.fetchPlayersByIds(playerIds);
        if (trainerId) await trainersStore.fetchTrainersByIds([trainerId]);
      }
    };

    // Suppression d'une session + mise à jour des joueurs/entraîneurs concernés
    const deleteSession = async (sessionId) => {
      try {
        const sessionToDelete = sessions.value.find(s => s.id === sessionId);
        const playerIds = sessionToDelete?.players.map(p => p.id) || [];
        const trainerId = sessionToDelete?.idTrainer?.id;
        await sessionsStore.deleteSession(sessionId);
        await fetchSessions();
        if (playerIds.length > 0) await playersStore.fetchPlayersByIds(playerIds);
        if (trainerId) await trainersStore.fetchTrainersByIds([trainerId]);
      } catch (error) {
        console.error("Erreur lors de la suppression de la session:", error);
      }
    };

    //  Suivi des suppressions de joueurs pour gérer les transferts inter-sessions
    const lastRemovedPlayer = ref(null);
    const lastRemovedFromSession = ref(null);

    const handlePlayerRemoved = async (data) => {
      if (data?.player?.id) {
        lastRemovedPlayer.value = data.player;
        lastRemovedFromSession.value = data.fromSessionId;
        await playersStore.fetchPlayersByIds([data.player.id]);
      }
    };

    //  Mise à jour de l'entraîneur d'une session
    const handleCoachUpdate = async ({ sessionId, coach }) => {
      try {
        const session = sessions.value.find(s => s.id === sessionId);
        if (!session) return;
        const trainerId = coach?.id || coach?.idtrainer || null;
        const updatedSession = { ...session, idTrainer: trainerId };
        await sessionsStore.updateSession(updatedSession);
        await fetchSessions();
        if (trainerId) {
          await trainersStore.fetchTrainersByIds([trainerId]);
          const updated = sessions.value.find(s => s.id === sessionId);
          if (updated?.players.length > 0) {
            const playerIds = updated.players.map(p => p.id);
            await playersStore.fetchPlayersByIds(playerIds);
          }
        }
      } catch (error) {
        console.error("Erreur lors de la mise à jour de l'entraîneur :", error);
      }
    };

    //  Mise à jour des joueurs d'une session (avec détection des transferts)
    const updateSessionsPlayers = async (data) => {
      const { sessionId, players } = data || {};
      if (!sessionId) return;

      try {
        const session = sessions.value.find(s => s.id === sessionId);
        if (!session) return;

        const validPlayers = players || [];

        if (lastRemovedFromSession.value && lastRemovedFromSession.value !== sessionId && lastRemovedPlayer.value) {
          //  Transfert détecté entre deux sessions
          const updatedDestination = { ...session, players: validPlayers };
          await updateSession(updatedDestination);
          const source = sessions.value.find(s => s.id === lastRemovedFromSession.value);
          if (source) {
            const updatedSourcePlayers = source.players.filter(p => p.id !== lastRemovedPlayer.value.id);
            await sessionsStore.updateSession({ ...source, players: updatedSourcePlayers });
          }
          lastRemovedPlayer.value = null;
          lastRemovedFromSession.value = null;
        } else {
          //  Mise à jour classique
          await updateSession({ ...session, players: validPlayers });
        }

        await fetchSessions();

        const updatedSessionData = sessions.value.find(s => s.id === sessionId);
        if (updatedSessionData) {
          if (updatedSessionData.players.length > 0) {
            const playerIds = updatedSessionData.players.map(p => p.id);
            await playersStore.fetchPlayersByIds(playerIds);
          }
          if (updatedSessionData.idTrainer?.id) {
            await trainersStore.fetchTrainersByIds([updatedSessionData.idTrainer.id]);
          }
        }
      } catch (error) {
        console.error("Erreur dans updateSessionsPlayers :", error);
      }
    };

    //  Calcul de la tranche d’âge à partir des joueurs d’une session
    const calculateAgeGroup = (session) => {
      if (session.players.length === 0) {
        return session.idTrainer
            ? session.idTrainer.infAge + " - " + session.idTrainer.supAge
            : "3 - 99";
      }

      const ages = session.players.map(player => getSportsAge(player.birthday));
      const minAge = Math.min(...ages);
      const maxAge = Math.max(...ages);

      if (minAge >= 8 && maxAge <= 17) {
        const x = 2 - (maxAge - minAge);
        return `${Math.max(8, minAge - x)} - ${Math.min(maxAge + x, 17)}`;
      } else if (minAge >= 3 && maxAge <= 4) {
        return "3 - 4";
      } else if (minAge >= 5 && maxAge <= 7) {
        return "5 - 7";
      } else if (minAge >= 18 && maxAge <= 99) {
        return "18 - 99";
      } else {
        return "3 - 99";
      }
    };

    //  Calcul du niveau global d’une session
    const calculateSkillLevel = (session) => {
      if (session.players.length === 0) {
        return session.idTrainer
            ? session.idTrainer.infLevel + " - " + session.idTrainer.supLevel
            : "0 - 20";
      }

      const levels = session.players.map(player => player.level);
      const minAge = Math.min(...levels);
      const maxAge = Math.max(...levels);

      if (minAge === maxAge) {
        return `${minAge - 1} - ${maxAge + 1}`;
      } else if (levels.length === 2) {
        return `${minAge} - ${maxAge}`;
      } else {
        return session.idTrainer
            ? session.idTrainer.infLevel + " - " + session.idTrainer.supLevel
            : "0 - 20";
      }
    };

    // Retour des données et méthodes exposées au template
    return {
      terrains,
      sessions,
      trainers,
      players,
      selectedTerrain,
      trainerSearchQuery,
      playerSearchQuery,
      selectedLevel,
      selectedAge,
      sessionLevels,
      sessionAges,
      sortedTerrains,
      sessionsByDay,
      showAllSessions,
      isMobile,
      isTablet,
      selectTerrain,
      updateSessionsPlayers,
      handlePlayerRemoved,
      openFilter,
      deleteSession,
      handleCoachUpdate,
      closeFilterPopup,
      showFilterPopup,
      filterSessions,
      calculateAgeGroup,
      calculateSkillLevel,
      handleTimesUpdated,
    };
  },
};
</script>


<style scoped>
.right-panel {
  background-color: white;
  position: relative;
  overflow-y: auto;
  margin-right: 1.37rem;
  width: 67%;
  height: 83vh;
  margin-bottom: 4vh;
  z-index: 100;
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

button {
  padding: 10px 16px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.popup-content h3 {
  margin-bottom: 10px;
}

.popup-content select {
  margin-bottom: 10px;
}

select:focus {
  outline: none;
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

/* Scrollbar */
.content::-webkit-scrollbar {
  width: 12px;
}

.content::-webkit-scrollbar-track {
  background: transparent;
  border-radius: 10px;
}

.content::-webkit-scrollbar-thumb {
  background: linear-gradient(45deg, #528359, #3a6242);
  border-radius: 10px;
  border: 2px solid transparent;
  background-clip: padding-box;
  transition: background 0.3s ease-in-out, border 0.3s ease-in-out;
}

.content::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(45deg, #3a6242, #2f6035);
  border: 2px solid rgba(255, 255, 255, 0.3);
}

/* Pour firefox */
.content {
  scrollbar-color: #528359 transparent;
  scrollbar-width: thin;
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
    height: 90vh;
    position: relative;
    top: 0;
    right: 0;
    margin-left: auto;
  }

}

</style>


