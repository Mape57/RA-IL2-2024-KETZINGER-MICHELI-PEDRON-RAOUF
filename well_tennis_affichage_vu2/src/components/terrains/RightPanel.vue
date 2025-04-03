<template>
  <div class="right-panel-wrapper">
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
    <div v-else class="panel desktop">
      <div class="toolbar desktop-toolbar">
        <div class="tab-buttons">
          <button
              v-for="terrain in sortedTerrains"
              :key="terrain.id"
              @click="selectTerrain(terrain.id)"
              :class="['terrain-tab', { active: selectedTerrain === terrain.id }]"
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
              :sessionId="session.id"
              :startTime="session.start"
              :endTime="session.stop"
              :coach="session.idTrainer"
              :ageGroup="getGroupAge(session.players)"
              :skillLevel="getGroupLevel(session.players)"
              @times-updated="handleTimesUpdated"
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
  <teleport to="body">
    <div v-if="showFilterPopup">
      <!-- Overlay cliquable -->
      <div
          class="fixed inset-0 z-[9998]"
          @click="closeFilterPopup"
      ></div>

      <!-- Popup filtre -->
      <div
          :class="[
        'fixed bg-white shadow-lg rounded-lg z-[9999] border border-gray-200',
         isMobile
         ? 'top-[60px] right-4 w-[75%] max-w-[230px] p-2'
         : 'top-[65px] right-[40px] w-80 p-4'
      ]"
          @click.stop
      >
        <button
            v-if="isMobile"
            @click="closeFilterPopup"
            class="absolute -top-1 right-1 text-gray-500 hover:text-red-500 text-lg"
        >
          <span class="material-symbols-outlined text-base">close</span>
        </button>

        <h3 class="text-lg font-semibold text-gray-800 mb-3">Filtrer les sessions</h3>

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
              :class="[
                'rounded-md text-white bg-green-700 hover:bg-green-800 transition',
                isMobile ? 'text-[10px] px-1.5 py-[2px] leading-none' : 'text-sm px-4 py-1'
              ]"


          >
            Réinitialiser
          </button>
        </div>

      </div>
    </div>
  </teleport>

</template>

<script>

import {ref, computed, onMounted, onUnmounted} from "vue";
import SessionCard from "./SessionCard.vue";
import useTerrains from "../../useJs/useTerrain.js";
import {useSessionsStore} from "../../store/useSessionsStore.js";
import {usePlayersStore} from "../../store/usePlayersStore.js";
import {useTrainersStore} from "../../store/useTrainersStore.js";
import {storeToRefs} from "pinia";
import {getGroupAge, getGroupLevel, getSportsAge} from "../../functionality/conversionUtils.js";



export default {
  name: "RightPanel",
  methods: {getGroupLevel, getGroupAge},
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
    const {sessions} = storeToRefs(sessionsStore);
    const {players} = storeToRefs(playersStore);
    const {trainers} = storeToRefs(trainersStore);

    // Méthodes pour récupérer les données
    const fetchSessions = sessionsStore.fetchSessions;
    const updateSession = sessionsStore.updateSession;
    const fetchPlayers = playersStore.fetchPlayers;
    const fetchTrainers = trainersStore.fetchTrainers;

    // Terrains récupérés via un composable
    const {terrains, fetchTerrains} = useTerrains();

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
      return terrains.value.slice().sort((a, b) => a.name.localeCompare(b.name, undefined, {numeric: true}));
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
          .map((session) => {
            const playersAge = [];
            session.players.forEach((player) => {
              const age = getSportsAge(player.birthday);
              playersAge.push(age);
            });
            session.minAge = Math.min(...playersAge);
            session.maxAge = Math.max(...playersAge);

            const playersLevel = [];
            session.players.forEach((player) => {
              playersLevel.push(player.level);
            });
            session.minLevel = Math.min(...playersLevel);
            session.maxLevel = Math.max(...playersLevel);
            return session;
          })
          .filter((session) => session.idCourt?.id === selectedTerrain.value)
          .filter((session) => !trainerSearchQuery.value || session.idTrainer?.name.toLowerCase().includes(trainerSearchQuery.value.toLowerCase()) || session.idTrainer?.surname.toLowerCase().includes(trainerSearchQuery.value.toLowerCase()))
          .filter((session) => !playerSearchQuery.value || session.players.some(player => player.name.toLowerCase().includes(playerSearchQuery.value.toLowerCase()) || player.surname.toLowerCase().includes(playerSearchQuery.value.toLowerCase())))
          .filter((session) => !selectedLevel.value || (session.minLevel <= selectedLevel.value && session.maxLevel >= selectedLevel.value))
          .filter((session) => !selectedAge.value || (session.minAge <= selectedAge.value && session.maxAge >= selectedAge.value))
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
    const filterSessions = () => {
    }; // placeholder

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
    onMounted(async () => {
      fetchTerrains();
      // Charger les sessions et les entraîneurs en parallèle
      await Promise.all([fetchSessions(), fetchTrainers()]);
      // Initialiser les heures des coachs une fois les données chargées
      trainersStore.initializeTrainerHours(sessions.value);
      fetchPlayers();
      window.addEventListener("resize", updateWindowSize);
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateWindowSize);
    });

    // Lorsqu'une session est modifiée (horaires)
    const handleTimesUpdated = async ({sessionId, oldStart, oldStop, newStart, newStop}) => {
      await fetchSessions();
      const updatedSession = sessions.value.find(s => s.id === sessionId);
      if (updatedSession) {
        const playerIds = updatedSession.players.map(p => p.id);
        const trainerId = updatedSession.idTrainer?.id;

        // Mise à jour des heures de l'entraîneur si concerné
        if (trainerId && oldStart && oldStop && newStart && newStop) {
          // Calculer l'ancienne durée
          const oldStartTime = new Date(`2000-01-01T${oldStart}`);
          const oldStopTime = new Date(`2000-01-01T${oldStop}`);
          const oldDuration = (oldStopTime - oldStartTime) / (1000 * 60 * 60);

          // Calculer la nouvelle durée
          const newStartTime = new Date(`2000-01-01T${newStart}`);
          const newStopTime = new Date(`2000-01-01T${newStop}`);
          const newDuration = (newStopTime - newStartTime) / (1000 * 60 * 60);

          // Ajuster les heures en fonction du changement
          const hoursDiff = newDuration - oldDuration;
          if (hoursDiff !== 0) {
            if (hoursDiff > 0) {
              trainersStore.incrementTrainerHours(trainerId, hoursDiff);
            } else {
              trainersStore.decrementTrainerHours(trainerId, Math.abs(hoursDiff));
            }
          }
        }

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

        // Si un entraîneur est associé à la session, décrémenter ses heures
        if (trainerId && sessionToDelete.start && sessionToDelete.stop) {
          const startTime = new Date(`2000-01-01T${sessionToDelete.start}`);
          const stopTime = new Date(`2000-01-01T${sessionToDelete.stop}`);
          const durationHours = (stopTime - startTime) / (1000 * 60 * 60);

          if (durationHours > 0) {
            trainersStore.decrementTrainerHours(trainerId, durationHours);
          }
        }

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

        console.log(`Joueur ${data.player.id} supprimé de la session ${data.fromSessionId}`);

        // Attendre un court instant pour permettre au backend de traiter les modifications
        await new Promise(resolve => setTimeout(resolve, 200));

        // Recharger tous les joueurs pour avoir les compteurs à jour
        await playersStore.fetchPlayers();
      }
    };
    //  Mise à jour de l'entraîneur d'une session
    const handleCoachUpdate = async ({sessionId, coach, oldCoach}) => {
      try {
        console.log("Mise à jour du coach pour la session:", sessionId);
        console.log("Ancien coach:", oldCoach);
        console.log("Nouveau coach:", coach);
        
        const session = sessions.value.find(s => s.id === sessionId);
        if (!session) {
          console.error("Session introuvable:", sessionId);
          return;
        }

        const oldTrainerId = oldCoach?.id || null;
        // Get the proper ID from the coach object, considering different property names
        const newTrainerId = coach?.id || coach?.idtrainer || null;
        
        console.log("Ancien coach ID:", oldTrainerId);
        console.log("Nouveau coach ID:", newTrainerId);
        
        // Create a properly formatted session object for the backend update
        const updatedSession = {
            ...session,
            idTrainer: newTrainerId
        };

        // Gestion du changement d'entraîneur pour leurs heures
        if (session.start && session.stop) {
          const startTime = new Date(`2000-01-01T${session.start}`);
          const stopTime = new Date(`2000-01-01T${session.stop}`);
          const durationHours = (stopTime - startTime) / (1000 * 60 * 60);

          // Si on supprime un entraîneur ou le remplace, décrémente ses heures
          if (oldTrainerId && durationHours > 0) {
            trainersStore.decrementTrainerHours(oldTrainerId, durationHours);
          }

          // Si on ajoute un entraîneur, incrémente ses heures
          if (newTrainerId && durationHours > 0) {
            trainersStore.incrementTrainerHours(newTrainerId, durationHours);
          }
        }

        await sessionsStore.updateSession(updatedSession);
        await fetchSessions();

        // Mise à jour des données liées
        if (oldTrainerId) {
          await trainersStore.fetchTrainersByIds([oldTrainerId]);
        }

        if (newTrainerId) {
          await trainersStore.fetchTrainersByIds([newTrainerId]);
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
      const {sessionId, players} = data || {};
      if (!sessionId) return;

      try {
        const session = sessions.value.find(s => s.id === sessionId);
        if (!session) return;

        const validPlayers = players || [];

        // Vérification des doublons potentiels parmi les joueurs
        const playerIds = validPlayers.map(p => p.id);
        const uniquePlayerIds = [...new Set(playerIds)];

        console.log(`Session ${sessionId} - Joueurs à mettre à jour:`, playerIds);
        console.log(`IDs uniques: ${uniquePlayerIds.length}/${playerIds.length}`);

        // Alerte si des doublons sont détectés
        if (uniquePlayerIds.length !== playerIds.length) {
          console.warn("ATTENTION: Doublons détectés dans la liste des joueurs!");
          console.warn("IDs en double:", playerIds.filter((id, index) => playerIds.indexOf(id) !== index));
        }

        if (lastRemovedFromSession.value && lastRemovedFromSession.value !== sessionId && lastRemovedPlayer.value) {
          //  Transfert détecté entre deux sessions
          console.log("Transfert détecté:",
              `Joueur ${lastRemovedPlayer.value.id} de session ${lastRemovedFromSession.value} vers session ${sessionId}`);

          const updatedDestination = {...session, players: validPlayers};
          console.log("Mise à jour session destination:", sessionId, "avec joueurs:",
              updatedDestination.players.map(p => p.id));

          await updateSession(updatedDestination);

          const source = sessions.value.find(s => s.id === lastRemovedFromSession.value);
          if (source) {
            const updatedSourcePlayers = source.players.filter(p => p.id !== lastRemovedPlayer.value.id);
            console.log("Mise à jour session source:", lastRemovedFromSession.value,
                "avec joueurs:", updatedSourcePlayers.map(p => p.id));

            await sessionsStore.updateSession({...source, players: updatedSourcePlayers});
          }
          lastRemovedPlayer.value = null;
          lastRemovedFromSession.value = null;
        } else {
          //  Mise à jour classique
          console.log("Mise à jour simple de la session:", sessionId,
              "avec joueurs:", validPlayers.map(p => p.id));

          await updateSession({...session, players: validPlayers});
        }

        // Attendre un court instant pour permettre au backend de traiter les modifications
        await new Promise(resolve => setTimeout(resolve, 200));

        // Recharger TOUTES les données pour s'assurer d'avoir des données à jour
        console.log("Rechargement complet des données...");
        await fetchSessions();

        // Forcer le rechargement de TOUS les joueurs au lieu de seulement ceux de la session
        // Cela garantit que les compteurs de sessions sont correctement mis à jour
        await playersStore.fetchPlayers();

        // Recharger les données spécifiques de l'entraîneur si nécessaire
        const updatedSessionData = sessions.value.find(s => s.id === sessionId);
        if (updatedSessionData && updatedSessionData.idTrainer?.id) {
          await trainersStore.fetchTrainersByIds([updatedSessionData.idTrainer.id]);
        }
      } catch (error) {
        console.error("Erreur dans updateSessionsPlayers :", error);

        // Même en cas d'erreur, essayer de recharger les données pour rester cohérent
        await fetchSessions();
        await playersStore.fetchPlayers();
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
      handleTimesUpdated,
    };
  },
};
</script>


<style scoped>
.toolbar {
  display: flex;
  align-items: center;
  padding: 12px;
  background: white;
  border-bottom: 1px solid #ccc;
  position: relative;
}

.desktop-toolbar {
  justify-content: center;
}

.tab-buttons {
  display: flex;
  gap: 16px;
}

.terrain-tab {
  background: none;
  border: none;
  border-bottom: 2px solid transparent;
  padding: 0.5rem 1rem;
  font-weight: 500;
  font-size: 1rem;
  color: #4a5568;
  cursor: pointer;
  transition: all 0.2s ease;
}

.terrain-tab:hover {
  color: #2f855a;
}

.terrain-tab.active {
  color: #2f855a;
  border-bottom: 2px solid #2f855a;
  border-radius: 0;
}

.terrain-tab.active::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1.5px;
  background: grey;
  border-radius: 9999px;
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

.filter-popup h3 {
  margin-bottom: 12px;
}

.filter-fields input {
  width: 100%;
  padding: 8px;
  margin-bottom: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.filter-footer button {
  background-color: #528359;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
}

.panel.desktop {
  position: relative;
  z-index: 1000;
}

.toolbar {
  display: flex;
  align-items: center;
  padding: 12px;
  background: white;
  border-bottom: 1px solid #ccc;
  position: sticky;
  top: 0;
  z-index: 50;
}


</style>


