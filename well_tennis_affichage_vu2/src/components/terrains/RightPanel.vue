
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
    // ici j utilise le store de session
    const sessionsStore = useSessionsStore();
    const { sessions } = storeToRefs(sessionsStore);
    const fetchSessions = sessionsStore.fetchSessions;
    const updateSession = sessionsStore.updateSession;

    const {trainers, fetchTrainers} = useTrainers()
    const {players, fetchPlayers } = usePlayers()
    const {terrains, fetchTerrains } = useTerrains();


    const selectedTerrain = ref(null);
    const trainerSearchQuery = ref("");
    const playerSearchQuery = ref("");
    const selectedLevel = ref(null);
    const selectedAge = ref(null);
    const showFilterPopup = ref(false);
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
      const daysOfWeek = ["Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"];
      const groupedSessions = Object.fromEntries(daysOfWeek.map((day) => [day, []]));


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
            const dayIndex = session.dayWeek - 1; // Convertit 1-7 en index 0-6
            if (daysOfWeek[dayIndex]) {
              groupedSessions[daysOfWeek[dayIndex]].push(session);
            }
          });

      Object.keys(groupedSessions).forEach(day => {
        groupedSessions[day].sort((a, b) => a.start.localeCompare(b.start));
      });

      return groupedSessions;
    });


    // Sélectionne un terrain
    const selectTerrain = (id) => {
      selectedTerrain.value = id;
    };

    // Met à jour la taille de l'écran
    const updateWindowSize = () => {
      windowWidth.value = window.innerWidth;
    };

    const openFilter = () => {
      showFilterPopup.value = !showFilterPopup.value;
    };

    const closeFilterPopup = () => {
      showFilterPopup.value = false;
    };
    const filterSessions = () => {
    };
    const showAllSessions = () => {
      trainerSearchQuery.value = "";
      playerSearchQuery.value = "";
      selectedLevel.value = null;
      selectedAge.value = null;
    };

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

    const sessionAges = computed(() => {
      const ages = new Set();
      sessions.value.forEach(session => {
        if (session.idTrainer) {
          ages.add(session.idTrainer.age);
        }
      });
      return Array.from(ages).sort((a, b) => a - b);
    });

    onMounted(() => {
      fetchTerrains();
      fetchSessions()
      fetchPlayers();
      fetchTrainers();
      window.addEventListener("resize", updateWindowSize);
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateWindowSize);
    });


    const deleteSession = async (sessionId) => {
      try {
        await sessionsStore.deleteSession(sessionId);
        // Refresh sessions after deletion
        await fetchSessions();
      } catch (error) {
        console.error("Erreur lors de la suppression de la session:", error);
      }
    };

    // Track removed players to handle cross-session transfers
    const lastRemovedPlayer = ref(null);
    const lastRemovedFromSession = ref(null);

    // Handle player removed from a session
    const handlePlayerRemoved = (data) => {
      if (data && data.player && data.fromSessionId) {
        lastRemovedPlayer.value = data.player;
        lastRemovedFromSession.value = data.fromSessionId;
      }
    };

    const handleCoachUpdate = async ({ sessionId, coach }) => {
      try {
        // Trouver la session à mettre à jour
        const session = sessions.value.find(s => s.id === sessionId);
        if (!session) {
          console.error("Session non trouvée :", sessionId);
          return;
        }
        // Extraire l'ID de l'entraîneur
        const trainerId = coach?.id || coach?.idtrainer || null;
        // Créer l'objet session mis à jour
        const updatedSession = {
          ...session,
          idTrainer: trainerId // Envoyer uniquement l'ID
        };
        // Appeler la méthode de mise à jour du store
        await sessionsStore.updateSession(updatedSession);
        // Rafraîchir les sessions pour obtenir les dernières données
        await fetchSessions();
        console.log("Entraîneur mis à jour pour la session :", sessionId);
      } catch (error) {
        console.error("Erreur lors de la mise à jour de l'entraîneur :", error);
      }
    };


    const updateSessionsPlayers = async (data) => {
        // Validate data object
        if (!data || typeof data !== 'object') {
          console.error("Erreur dans updateSessionsPlayers : données invalides", data);
          return;
        }
        const {sessionId, players} = data;
        // Validate sessionId
        if (!sessionId) {
          console.error("Erreur dans updateSessionsPlayers : sessionId manquant");
          return;
        }
        try {
          // Find the session to update
          const session = sessions.value.find(s => s.id === sessionId);
          if (!session) {
            console.error("Session non trouvée :", sessionId);
            return;
          }
          const validPlayers = players || [];

          // If this is a different session than the one a player was removed from,
          // and we have a lastRemovedPlayer, this is likely a cross-session transfer
          if (lastRemovedFromSession.value &&
              lastRemovedFromSession.value !== sessionId &&
              lastRemovedPlayer.value) {
            console.log("Cross-session transfer detected");
            // Create updated session object with new players
            const updatedSession = {
              ...session,
              players: validPlayers
            };
            console.log("Mise à jour de la session de destination:", sessionId);
            console.log("Avec joueurs:", validPlayers);
            await updateSession(updatedSession);
            // Now update the source session by removing the player
            const sourceSession = sessions.value.find(s => s.id === lastRemovedFromSession.value);
            if (sourceSession) {
              const updatedSourcePlayers = sourceSession.players.filter(
                p => p.id !== lastRemovedPlayer.value.id
              );
              const updatedSourceSession = {
                ...sourceSession,
                players: updatedSourcePlayers
              };
              console.log("Mise à jour de la session source:", lastRemovedFromSession.value);
              console.log("Pour supprimer le joueur:", lastRemovedPlayer.value.name);
              await sessionsStore.updateSession(updatedSourceSession);
            }
            // Reset tracking variables
            lastRemovedPlayer.value = null;
            lastRemovedFromSession.value = null;
          } else {
            // Regular update within the same session
            const updatedSession = {
              ...session,
              players: validPlayers
            };
            console.log("Mise à jour régulière avec joueurs:", validPlayers);
            await sessionsStore.updateSession(updatedSession);
          }
          console.log("Joueurs mis à jour pour la session :", sessionId);
          // Refresh sessions to get latest data
          await fetchSessions();
        } catch (error) {
          console.error("Erreur dans updateSessionsPlayers :", error);
        }
    };

    const calculateAgeGroup = (session) => {
      if (session.players.length === 0) {
        if (session.idTrainer) {
          return session.idTrainer.infAge + " - " + session.idTrainer.supAge;
        } else {
          return "3 - 99";
        }
      }

      const ages = session.players.map(player => getSportsAge(player.birthday));
      const minAge = Math.min(...ages);
      const maxAge = Math.max(...ages);

      if (minAge >= 8 && maxAge <= 17) {
        const x = 2 - (maxAge - minAge);
        return `${Math.max(8, minAge -x)} - ${Math.min(maxAge + x, 17)}`;
      }
      else if (minAge >= 3 && maxAge <= 4) {
        return "3 - 4";
      }
      else if (minAge >= 5 && maxAge <= 7) {
        return "5 - 7";
      }
      else if (minAge >= 18 && maxAge <= 99) {
        return "18 - 99";
      } else {
        return "3 - 99";
      }
    };

    const calculateSkillLevel = (session) => {
      if (session.players.length === 0) {
        if (session.idTrainer) {
          return session.idTrainer.infLevel + " - " + session.idTrainer.supLevel;
        } else {
          return "0 - 20";
        }
      }

      const levels = session.players.map(player => player.level);
      const minAge = Math.min(...levels);
      const maxAge = Math.max(...levels);

      if (minAge === maxAge) {
        return `${minAge - 1} - ${maxAge + 1}`;
      }
      else if (levels.length === 2) {
        return minAge + " - " + maxAge;
      }
      else {
        if (session.idTrainer) {
          return session.idTrainer.infLevel + " - " + session.idTrainer.supLevel;
        } else {
          return "0 - 20";
        }
      }
    };

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


