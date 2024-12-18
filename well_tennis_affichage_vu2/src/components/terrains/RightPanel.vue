<template>
  <div class="right-panel fixed top-5 right-10 bg-white rounded-lg shadow-md w-[67%] h-[88vh] p-5 left-[32%] z-10 flex flex-col">
    <!-- Onglets des terrains et bouton de filtrage -->
    <div class="flex items-center relative">
      <!-- Conteneur centré des boutons des terrains -->
      <div class="absolute left-1/2 transform -translate-x-1/2 flex space-x-8">
        <button
            v-for="terrain in terrains"
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

export default {
  name: "RightPanel",
  components: {
    SessionCard,
  },
  data() {
    return {
      terrains: [
        {
          id: 1,
          name: "Terrain 1",
          sessions: [
            {
              id: 1,
              startTime: "17:00",
              endTime: "18:30",
              coach: "Billie Sharpe",
              ageGroup: "15-18 ans",
              skillLevel: "8-10",
              players: ["Erica Scott", "Alexandra Vance", "Anais Ayala"],
              day: "Lundi",
            },
            {
              id: 2,
              startTime: "19:00",
              endTime: "20:30",
              coach: "Athena Garrett",
              ageGroup: "8-12 ans",
              skillLevel: "5-7",
              players: ["Callan McConnell", "Kayla Dotson"],
              day: "Mardi",
            },
            {
              id: 3,
              startTime: "10:00",
              endTime: "11:30",
              coach: "Billie Sharpe",
              ageGroup: "10-14 ans",
              skillLevel: "6-8",
              players: ["John Doe", "Jane Doe", "Kayla Dotson", "Kayla Dotson", "Jane Doe", "Kayla Dotson", "Jane Doe" ],
              day: "Mercredi",
            },
            {
              id: 4,
              startTime: "10:00",
              endTime: "11:30",
              coach: "Billie Sharpe",
              ageGroup: "10-14 ans",
              skillLevel: "6-8",
              players: ["John Doe", "Jane Doe"],
              day: "Mercredi",
            },
          ],
        },
        {
          id: 2,
          name: "Terrain 2",
          sessions: [
            {
              id: 4,
              startTime: "14:00",
              endTime: "15:30",
              coach: "Athena Garrett",
              ageGroup: "8-12 ans",
              skillLevel: "5-7",
              players: ["Alice", "Bob"],
              day: "Jeudi",
            },
          ],
        },
        {
          id: 3,
          name: "Terrain 3",
          sessions: [],
        },
      ],
      selectedTerrain: 1,
    };
  },
  computed: {
    currentTerrain() {
      return this.terrains.find(
          (terrain) => terrain.id === this.selectedTerrain
      );
    },
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

      this.currentTerrain.sessions.forEach((session) => {
        if (session.day && groupedSessions[session.day]) {
          groupedSessions[session.day].push(session);
        }
      });

      return groupedSessions;
    },
  },
  methods: {
    selectTerrain(id) {
      this.selectedTerrain = id;
    },
    editSession(sessionId) {
      console.log(`Modifier la séance ${sessionId}`);
    },
    deleteSession(sessionId) {
      console.log(`Supprimer la séance ${sessionId}`);
      const terrain = this.currentTerrain;
      terrain.sessions = terrain.sessions.filter(
          (session) => session.id !== sessionId
      );
    },
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

