<template>
  <div>
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <div class="flex items-center players-hover">
        <span :class="[isOpen ? 'rotate-0' : 'rotate-270']"
              class="material-symbols-outlined players-arrow transition-transform duration-300 mr-2">
        expand_more
       </span>
        <h3 class="font-bold text-lg players-title">Joueurs</h3>
      </div>

      <!-- Boutons d'action -->
      <div class="flex space-x-2" v-if="!localIsMobile && userRole === 'ROLE_ADMIN'">
  <span class="material-symbols-outlined small-icon cursor-pointer"
        title="Ajouter"
        ref="addPlayerButton"
        @click="addPlayer">
        person_add</span>
      </div>

    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <div v-if="loading" class="flex justify-center items-center py-4">
        <div class="loader"></div>
      </div>
      <div v-else>
        <!-- En-têtes des colonnes -->
        <div class="grid grid-cols-4 font-semibold text-gray-400 text-sm mb-2">
          <div class="text-left">Nom</div>
          <div class="text-left">Prénom</div>
          <div class="text-left">Âge</div>
          <div class="text-center">Niveau</div>
          <div class="text-center">Sessions</div>
        </div>

        <VueDraggable
            v-model="filteredPlayers"
            :group="{ name: 'players', pull: 'clone', put: false }"
            item-key="id"
            :sort="false"
            @start="onDragStart"
            @end="onDragEnd"
            class="player-list"
        >
          <div
              v-for="player in filteredPlayers"
              :key="player.id"
              class="grid grid-cols-4 items-center py-1"
              :class="{ 'cursor-pointer': !isMobile }"
              :ref="'player-' + player.id"
              @click="!isMobile && showPlayerInfo(player)">

            <!-- Nom du joueur -->
            <span
                class="truncate overflow-hidden whitespace-nowrap"
                :title="player.name"
            >
              {{ player.name }}
            </span>

            <!-- Prénom du joueur -->
            <span
                class="truncate overflow-hidden whitespace-nowrap"
                :title="player.surname"
            >{{ player.surname }}</span>

            <!-- Âge du joueur -->
            <span class="text-left">{{ computeAge(player.birthday) || "N/A" }} ans</span>
            <!-- Niveau du joueur -->
            <span class="text-center">{{ player.level }}</span>
            <span class="text-center">{{ player.numberOfSessions }} / {{ player.courses }}</span>
          </div>

        </VueDraggable>

        <!-- Affichage de PlayerInfoView -->
        <PlayerInfoView
            v-if="selectedPlayer && !isMobile && userRole === 'ROLE_ADMIN'"
            :player="selectedPlayer"
            @close="selectedPlayer = null"
            @delete="handlePlayerDeletion"
            @save="handlePlayerSave"
        />

      </div>
    </div>
  </div>
</template>

<script>
import {ref, onMounted, onUnmounted, computed} from "vue";
import { usePlayersStore } from "../../store/usePlayersStore.js";
import PlayerInfoView from "../vueInformations/PlayerInfoView.vue";
import {VueDraggable} from "vue-draggable-plus";

export default {
  name: "Players",
  components: {VueDraggable, PlayerInfoView},
  props: {
    searchQuery: String,
    isMobile: Boolean,
    userRole: String,
  },
  setup() {
    const playersStore = usePlayersStore();
    const { computeAge } = usePlayersStore();
    
    const players = computed(() => playersStore.players);
    const loading = computed(() => playersStore.loading);

    const localIsMobile = ref(window.innerWidth < 768);

    const updateIsMobile = () => {
      localIsMobile.value = window.innerWidth < 768;
    };
    onMounted(() => {
      updateIsMobile();
      window.addEventListener("resize", updateIsMobile);
      
      playersStore.fetchPlayers();
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateIsMobile);
    });


    return {
      computeAge,
      localIsMobile,
      players,
      loading,
      playersStore,
    };
  },

  data() {
    return {
      isOpen: true,
      selectedPlayer: null,
      draggedPlayer: null, // Stocke le joueur en cours de drag
    };
  },
  computed: {
    filteredPlayers() {
      return this.players
          .filter(player => player.validate === true)
          .filter(player => {
            const searchLower = this.searchQuery.toLowerCase();
            return player.name.toLowerCase().includes(searchLower) ||
                player.surname.toLowerCase().includes(searchLower);
          })
          .sort((a, b) => {
            const diffA = a.courses - a.numberOfSessions;
            const diffB = b.courses - b.numberOfSessions;

            // Compare name and surname when values are equal
            const compareNames = () => {
              const nameCompare = a.name.toLowerCase().localeCompare(b.name.toLowerCase());
              return nameCompare !== 0 ? nameCompare :
                  a.surname.toLowerCase().localeCompare(b.surname.toLowerCase());
            };

            // Negative values (overused sessions)
            if (diffA < 0 && diffB < 0) {
              return diffA === diffB ? compareNames() : diffA - diffB;
            }

            // Sort negative before non-negative
            if (diffA < 0) return -1;
            if (diffB < 0) return 1;

            // Positive values (remaining sessions)
            if (diffA > 0 && diffB > 0) {
              return diffA === diffB ? compareNames() : diffB - diffA;
            }

            // Sort positive before zero
            if (diffA > 0) return -1;
            if (diffB > 0) return 1;

            // Equal values (zero)
            return compareNames();
          });
    }
  },
  methods: {
    toggleAccordion(event) {
      const addButton = this.$refs.addPlayerButton;

      if (addButton && addButton.contains(event.target) && this.isOpen) {
        return;
      }

      // Sinon, basculer l'état d'ouverture
      this.isOpen = !this.isOpen;
    },
    showPlayerInfo(player) {
      if (this.localIsMobile) return; // Empêche d'afficher les informations en mode mobile
      if (this.selectedPlayer && this.selectedPlayer.id === player.id) {
        this.selectedPlayer = null;
      } else {
        this.selectedPlayer = player;
      }
    },

    async handlePlayerDeletion(deletedPlayerId) {
      if (this.userRole !== "ROLE_ADMIN") return;
      this.selectedPlayer = null; // Ferme l'affichage des détails
    },
    async handlePlayerSave(savedPlayer) {
      if (this.userRole !== "ROLE_ADMIN") return;
      if (!savedPlayer || typeof savedPlayer !== "object") {
        console.error("Données invalides reçues dans handlePlayerSave :", savedPlayer);
        return;
      }
      
      try {
        if (savedPlayer.id) {
          // Update existing player
          await this.playersStore.updatePlayer(savedPlayer.id, savedPlayer);
        } else {
          // Create new player
          savedPlayer = await this.playersStore.createPlayer(savedPlayer);
        }
        
        this.$nextTick(() => {
          const newPlayerElement = this.$refs[`player-${savedPlayer.id}`]?.[0];
          if (newPlayerElement) {
            newPlayerElement.scrollIntoView({behavior: "smooth", block: "center"});

            // Ajouter une classe temporaire pour l'effet de mise en valeur
            newPlayerElement.classList.add("highlighted");
            setTimeout(() => newPlayerElement.classList.remove("highlighted"), 3000); // Retire l'effet après 3s
          }
        });
      } catch (error) {
        console.error("Error saving player:", error);
      }

    },


    addPlayer() {
      if (this.localIsMobile || this.userRole !== "ROLE_ADMIN") return; // Empêche l'ajout de joueurs en mode mobile
      this.selectedPlayer = {
        id: null,
        name: "",
        surname: "",
        birthday: "",
        courses: 1,
        level: 1,
        email: "",
        disponibilities: [],
      };
    },
    onDragStart(event) {
      console.log('start')
      this.draggedPlayer = event.item.element;
    },
    onDragEnd() {
      console.log('update')
      this.$emit("player-dragged", this.draggedPlayer);
      this.draggedPlayer = null;
    }
  },
};
</script>


<style scoped>
.player-list li, .player-list > div {
  cursor: grab;
  transition: background-color 0.2s;
}

.player-list li:hover, .player-list > div:hover {
  background-color: rgba(82, 131, 89, 0.1);
}

.player-list li:active, .player-list > div:active {
  cursor: grabbing;
}


.players-hover {
  transition: color 0.2s ease-in-out;
}

.players-title {
  transition: color 0.2s ease-in-out;
}

.players-arrow {
  transition: color 0.2s ease-in-out;
}

.players-hover:hover .players-title,
.players-hover:hover .players-arrow {
  color: #2F855A;
}

.grid {
  display: grid;
  grid-template-columns: 2fr 1.7fr 1fr 1fr 1fr;
  gap: 0.8rem;
}

.material-symbols-outlined {
  cursor: pointer;
  transition: transform 0.3s ease;
}

.small-icon {
  font-size: 18px;
}

.rotate-270 {
  transform: rotate(-90deg);
}

.rotate-0 {
  transform: rotate(0deg);
}

.border-b {
  border-bottom: 1px solid #e2e8f0;
}

::v-deep(.highlighted) {
  animation: highlightEffect 2s ease-out;
}

@keyframes highlightEffect {
  0% {
    color: #2F855A;
    font-weight: bold;
    transform: scale(1.02);
  }
  70% {
    color: #2F855A;
    font-weight: bold;
    transform: scale(1.01);
  }
  100% {
    color: inherit;
    font-weight: normal;
    transform: scale(1);
  }
}

/* Ajout de styles pour indiquer les éléments glissables */
.trainer-list li, .trainer-list > div {
  cursor: grab;
  transition: background-color 0.2s;
}

.trainer-list li:hover, .trainer-list > div:hover {
  background-color: rgba(82, 131, 89, 0.1);
}

.trainer-list li:active, .trainer-list > div:active {
  cursor: grabbing;
}

</style>
