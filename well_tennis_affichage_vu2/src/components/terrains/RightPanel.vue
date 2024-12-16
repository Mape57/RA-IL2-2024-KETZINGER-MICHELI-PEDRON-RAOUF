<template>
  <div class="w-[70%] h-[85vh] space-y-4 bg-white rounded-lg shadow-md p-6 overflow-auto space-x-4 px-4 m-5">
<!--    <div class="flex flex-col lg:flex-row w-[70%] h-[80vh] bg-white rounded-lg shadow-md p-6 overflow-auto space-y-4 lg:space-y-0 lg:space-x-4 px-4 mt-5 mb-5">-->

    <!-- Onglets des terrains -->
    <div class="tabs flex justify-center space-x-6">
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

    <div class="border-t border-gray-700 mb-4"></div>

    <!-- Contenu du terrain sélectionné -->
    <div class="content flex-1 overflow-auto">
      <div v-for="(sessions, day) in sessionsByDay" :key="day" class="mb-6">
        <!-- Affichage du jour -->
        <h2 class="text-[#528359] text-xl font-bold mb-4">{{ day }}</h2>

        <!-- Affichage des séances pour le jour -->
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
              players: ["John Doe", "Jane Doe"],
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
      selectedTerrain: 1, // Terrain sélectionné par défaut
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

      // Initialisation des jours vides
      daysOfWeek.forEach((day) => {
        groupedSessions[day] = [];
      });

      // Grouper les sessions par jour
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
      this.selectedTerrain = id; // Change le terrain sélectionné
    },
    editSession(sessionId) {
      console.log(`Modifier la séance ${sessionId}`);
      // Logique pour modifier une séance
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
/* Styles pour les onglets */
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
  border-bottom-color: #528359;
}

/* Contenu du panneau droit */
.right-panel {
  transition: all 0.3s ease-in-out;
}
</style>
