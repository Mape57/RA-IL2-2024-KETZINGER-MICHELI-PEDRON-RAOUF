<template>
  <div>
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <div class="flex items-center players-hover">
        <span :class="{ 'rotate-180': isOpen }"
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
        >
          <div
              v-for="player in filteredPlayers"
              :key="player.id"
              class="grid grid-cols-4 items-center py-1"
              :class="{ 'cursor-pointer': !isMobile }"
              :ref="'player-' + player.id"
              @click="!isMobile && showPlayerInfo(player)">

            <!-- Nom du joueur -->
            <span>{{ player.name }}</span>
            <!-- Prénom du joueur -->
            <span class="text-left">{{ player.surname }}</span>
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
import {ref, onMounted, onUnmounted} from "vue";
import usePlayers from "../../useJs/usePlayers.js";
import PlayerInfoView from "../vueInformations/PlayerInfoView.vue";
import {VueDraggable} from "vue-draggable-plus";

export default {
  name: "Players",
  components: {VueDraggable, PlayerInfoView},
  props: {
    players: Array,
    searchQuery: String,
    isMobile: Boolean,
    userRole: String,
    loading: {
      type: Boolean,
      default: false
    },
  },
  setup() {
    const {computeAge} = usePlayers();

    const localIsMobile = ref(window.innerWidth < 768);

    const updateIsMobile = () => {
      localIsMobile.value = window.innerWidth < 768;
    };
    onMounted(() => {
      updateIsMobile();
      window.addEventListener("resize", updateIsMobile);
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateIsMobile);
    });


    return {
      computeAge,
      localIsMobile,
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
          .filter(player =>
              player.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
              player.surname.toLowerCase().includes(this.searchQuery.toLowerCase())
          );
    },
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

    handlePlayerDeletion(deletedPlayerId) {
      const updatedPlayers = this.players.filter(player => player.id !== deletedPlayerId);
      this.$emit('update:players', updatedPlayers); // Émet la liste mise à jour au parent
      this.selectedPlayer = null; // Ferme l'affichage des détails
    },
    handlePlayerSave(savedPlayer) {
      if (this.userRole !== "ROLE_ADMIN") return;
      if (!savedPlayer || typeof savedPlayer !== "object") {
        console.error("Données invalides reçues dans handlePlayerSave :", savedPlayer);
        return;
      }
      const index = this.players.findIndex(player => player.id === savedPlayer.id);
      if (index !== -1) {
        // Mise à jour d'un joueur existant
        this.players.splice(index, 1, savedPlayer);
      } else {
        // Ajout d'un nouveau joueur
        this.players.push(savedPlayer);
      }

      // Émet la liste mise à jour au parent
      this.$emit("update:players", [...this.players]);

      this.$nextTick(() => {
        const newPlayerElement = this.$refs[`player-${savedPlayer.id}`]?.[0];
        if (newPlayerElement) {
          newPlayerElement.scrollIntoView({behavior: "smooth", block: "center"});

          // Ajouter une classe temporaire pour l'effet de mise en valeur
          newPlayerElement.classList.add("highlighted");
          setTimeout(() => newPlayerElement.classList.remove("highlighted"), 3000); // Retire l'effet après 3s
        }
      });

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

.rotate-180 {
  transform: rotate(180deg);
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

</style>
