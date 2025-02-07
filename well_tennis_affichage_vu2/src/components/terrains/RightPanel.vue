<template>
  <div class="right-panel fixed top-5 right-10 bg-white rounded-lg shadow-md w-[67%] h-[88vh] p-5 left-[32%] z-10 flex flex-col">
    <!-- Onglets des terrains et bouton de filtrage -->
    <div class="flex items-center relative">
      <!-- Conteneur centré des boutons des terrains -->
      <div class="absolute left-1/2 transform -translate-x-1/2 flex space-x-8">
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
      <!-- Bouton Filtrer aligné à droite -->
      <div class="ml-auto">
        <button
            @click="openFilter"
            class="flex items-center space-x-1 text-sm text-black font-semibold hover:text-green-700 transition"
            title="Filtrer la recherche"
        >
          <span>Filtrer la recherche</span>
          <span class="material-symbols-outlined text-base">tune</span>
        </button>
      </div>
    </div>

    <!-- Ligne de séparation -->
    <div class="border-t border-gray-300 mb-4 mt-4"></div>

    <!-- Contenu du terrain sélectionné -->
    <div class="content flex-1 overflow-auto">
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
            @edit="editSession(session.id)"
            @delete="deleteSession(session.id)"
        />
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
      terrains: [], // Liste des terrains
      sessions: [], // Liste des séances
      selectedTerrain: null, // ID du terrain sélectionné
    };
  },
  computed: {
    sortedTerrains() {
      return this.terrains.slice().sort((a, b) => {
        const nameA = a.name.toUpperCase(); // Ignore la casse
        const nameB = b.name.toUpperCase(); // Ignore la casse
        return nameA.localeCompare(nameB, undefined, { numeric: true });
      });
    },
    // Filtrer les séances pour le terrain sélectionné
    currentTerrainSessions() {
      if (!this.selectedTerrain) return [];

      return this.sessions.filter(
          (session) => session.idCourt.id === this.selectedTerrain
      );
    },
    // Grouper les séances par jour
    sessionsByDay() {
      const daysOfWeek = [
        "Lundi",
        "Mardi",
        "Mercredi",
        "Jeudi",
        "Vendredi",
        "Samedi",
        "Dimanche",
      ];

      const groupedSessions = {};

      daysOfWeek.forEach((day) => {
        groupedSessions[day] = [];
      });

      this.currentTerrainSessions.forEach((session) => {
        // Mapping des jours de l'anglais au français
        const dayMapping = {
          Monday: "Lundi",
          Tuesday: "Mardi",
          Wednesday: "Mercredi",
          Thursday: "Jeudi",
          Friday: "Vendredi",
          Saturday: "Samedi",
          Sunday: "Dimanche",
        };

        const mappedDay = dayMapping[session.day] || session.day;

        if (mappedDay && groupedSessions[mappedDay]) {
          // Calcul des âges
          const playerAges = session.players.map((player) => {
            const birthday = new Date(player.birthday);
            const age = new Date().getFullYear() - birthday.getFullYear();
            const hasBirthdayPassed =
                new Date().getMonth() > birthday.getMonth() ||
                (new Date().getMonth() === birthday.getMonth() &&
                    new Date().getDate() >= birthday.getDate());
            return hasBirthdayPassed ? age : age - 1;
          });

          const minAge = Math.min(...playerAges);
          const maxAge = Math.max(...playerAges);

          // Calcul des niveaux
          const playerLevels = session.players.map((player) => player.level);
          const minLevel = Math.min(...playerLevels);
          const maxLevel = Math.max(...playerLevels);

          groupedSessions[mappedDay].push({
            id: session.id,
            startTime: session.start,
            endTime: session.stop,
            coach: `${session.idCoach.name} ${session.idCoach.surname}`,
            ageGroup: `${minAge} - ${maxAge} ans`,
            skillLevel: `${minLevel} - ${maxLevel}`,
            players: session.players.map(
                (player) => `${player.name} ${player.surname}`
            ),
          });
        }
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
    selectTerrain(id) {
      this.selectedTerrain = id;
    },
    editSession(sessionId) {
      console.log(`Modifier la séance ${sessionId}`);
    },
    deleteSession(sessionId) {
      console.log(`Supprimer la séance ${sessionId}`);
      this.sessions = this.sessions.filter((session) => session.id !== sessionId);
    },
  },
  async mounted() {
    await this.loadTerrainsAndSessions();
  },
};
</script>


<style scoped>
.tab-button {
  color: gray;
  font-weight: bold;
  transition: all 0.3s ease-in-out;
  padding: 0.5rem 1rem;
  position: relative;
  border-bottom: 2px solid transparent;
  cursor: pointer;
  white-space: nowrap;
  z-index: 1;
}

.tab-button.active {
  color: #528359;
  border-bottom: 2px solid #528359;
  z-index: 2;
}
</style>

