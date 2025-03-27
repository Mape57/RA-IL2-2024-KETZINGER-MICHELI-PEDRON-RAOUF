<template>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <div>
    <div
        v-if="!isMobile"
        class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 shadow-sm mb-4 flex lg:flex-row items-center"
    >
      <div class="text-custom-color font-bold text-lg lg:w-[10%] text-center lg:border-r border-gray-800 lg:pr-4 mb-4 lg:mb-0 flex flex-col justify-center items-center">
        <p>{{ startTime }}</p>
        <p>{{ endTime }}</p>
      </div>

      <div class="w-[25%] h-[20%] lg:pl-2 mb-4 lg:mb-0 flex flex-col justify-center">
        <div class="mb-2">
          <div class="font-bold text-sm">Entraîneur : </div>


          <VueDraggable
              v-model="entraineur"
              :group="{ name: 'coach', pull: false, put: ['trainers'] }"
              item-key="idtrainer"
              :sort="false"
              class="pr-1 coach-area"
              @add="onCoachDropped"
              :move="moveValidator"
          >
            <div
                v-if="entraineur.length"
                :key="entraineur[0].id"
            >
              {{ entraineur[0].name }} {{ entraineur[0].surname }}
            </div>
            <div v-else class="italic text-gray-400">Aucun entraîneur</div>
          </VueDraggable>


        </div>
        <div class="mb-2">
          <span class="font-bold text-sm">Âge :</span> {{ ageGroup }} ans
        </div>
        <div>
          <span class="font-bold text-sm">Niveau :</span> {{ skillLevel }}
        </div>
      </div>

      <div class="flex-1 pl-2 ml-0">
        <div class="flex mt-1">

          <VueDraggable
              v-model="leftPlayers"
              :group="{ name: 'players', pull: true, put: true }"
              item-key="id"
              :sort="false"
              class="pr-1 players-area"
              @end="onDragEnd"
              @add="onPlayerAdded"
              @remove="onPlayerRemoved"
          >
            <template v-for="(player, index) in leftPlayers" :key="player.id || index">
              <li class="list-disc list-inside text-sm text-gray-700">
                {{ player.name }} {{ player.surname }}
              </li>
            </template>
          </VueDraggable>



          <VueDraggable
              v-model="rightPlayers"
              :group="{ name: 'players', pull: true, put: true }"
              item-key="id"
              :sort="false"
              class="pl-2 players-area"
              @end="onDragEnd"
              @add="onPlayerAdded"
              @remove="onPlayerRemoved"
          >
            <template v-for="(player, index) in rightPlayers" :key="player.id || index">
              <li class="list-disc list-inside text-sm text-gray-700">
                {{ player.name }} {{ player.surname }}
              </li>
            </template>
          </VueDraggable>

        </div>
      </div>


      <div class="lg:w-[10%] flex justify-end" v-if="userRole === 'ROLE_ADMIN'">
        <button @click="$emit('delete')" class="delete-button">
          <span class="material-icons delete-icon">delete</span>
          <span class="delete-text">Supprimer</span>
        </button>


      </div>
    </div>

    <div
        v-else
        class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 shadow-sm mb-4 flex flex-col"
    >
      <div class="flex justify-between items-center border-b border-gray-300 pb-2 mb-2">
        <div class="text-custom-color font-bold text-lg">
          {{ coach }}
        </div>
        <div class="text-custom-color text-sm font-medium" @click="showInfo = !showInfo">
          {{ showInfo ? (ageGroup + ' ans, Niveau: ' + skillLevel) : (startTime + ' - ' + endTime) }}
        </div>
      </div>

      <div class="grid no-gap-grid">
        <span
            v-for="(player, index) in players.slice(0, 8)"
            :key="index"
            class="text-sm text-gray-700"
        >
          • {{ player }}
        </span>
      </div>

      <div class="mt-1 flex justify-end" v-if="userRole === 'ROLE_ADMIN'">
        <button @click="$emit('delete')" class="delete-button">
          <span class="material-icons delete-icon">delete</span>
          <span class="delete-text">Supprimer</span>
        </button>


      </div>
    </div>
  </div>
</template>

<script>
import {VueDraggable} from "vue-draggable-plus";
import {watchEffect} from "vue";

export default {
  components: {VueDraggable},
  name: "SessionCard",
  emits: ["update-players", "delete", "player-removed","update-coach"],
  props: {
    sessionId: String,
    startTime: {
      type: String,
      required: true,
    },
    endTime: {
      type: String,
      required: true,
    },
    coach: {
      type: Object,
      required: false,
      default: null
    },
    ageGroup: {
      type: String,
      required: true,
    },
    skillLevel: {
      type: String,
      required: true,
    },
    players: {
      type: Array,
      required: true,
    },
    userRole: String,
  },
  data() {
    return {
      entraineur: [],
      isMobile: false,
      showInfo: false,
      leftPlayers: [],
      rightPlayers: [],
      lastRemovedPlayer: null,
    };
  },
  mounted() {
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);

    watchEffect(() => {
      if (this.players && this.players.length) {
        const half = Math.ceil(this.players.length / 2);
        this.leftPlayers = this.players.slice(0, half);
        this.rightPlayers = this.players.slice(half);
      }
      if (this.coach) {
        this.entraineur = [this.coach];
      } else {
        this.entraineur = [];
      }
    });
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
  methods: {
    checkScreenSize() {
      this.isMobile = window.innerWidth <= 1024;
    },

    moveValidator(evt) {
      // Si on essaie de déposer dans la zone coach, vérifier que c'est bien un entraîneur
      if (evt.to.classList.contains('coach-area')) {
        // Vérifier que l'élément vient du groupe "trainers"
        return evt.from.className.includes('trainer-list');
      }
      return true; // Autoriser tous les autres déplacements
    },

    onDragEnd(evt) {
      // Combine left and right players to get complete list
      const updatedPlayers = [...this.leftPlayers, ...this.rightPlayers];

      // Emit event with session ID and updated players
      this.$emit('update-players', {
        sessionId: this.sessionId,
        players: updatedPlayers
      });
    },

    onPlayerAdded(evt) {
      console.log("Player added to session", this.sessionId);
      const updatedPlayers = [...this.leftPlayers, ...this.rightPlayers];

      this.$emit('update-players', {
        sessionId: this.sessionId,
        players: updatedPlayers
      });
    },

    onPlayerRemoved(evt) {
      console.log("Player removed from session", this.sessionId);
      const movedPlayerEl = evt.item;
      const playerIndex = evt.oldIndex;

      // Store information about the removed player for tracking
      let removedPlayer;
      if (evt.from.className.includes('pr-1')) {
        // Player was from leftPlayers
        removedPlayer = this.players[playerIndex];
      } else {
        // Player was from rightPlayers
        const half = Math.ceil(this.players.length / 2);
        removedPlayer = this.players[half + playerIndex];
      }

      if (removedPlayer) {
        this.$emit('player-removed', {
          player: removedPlayer,
          fromSessionId: this.sessionId
        });
      }
    },

    onCoachDropped(evt) {
      // Vérifier si l'élément déposé est bien un entraîneur
      if (evt.from.className.includes('trainer-list')) {
        let newCoach = null;
        newCoach = this.entraineur[0];

        if (newCoach && (newCoach.id)) {
          this.$emit("update-coach", {
            sessionId: this.sessionId,
            coach: newCoach
          });

          console.log("Coach ajouté à la session:", newCoach);
        } else {
          console.warn("Coach drop event doesn't contain valid coach data", evt);
        }
      } else {
        console.warn("Tentative de déposer un non-entraîneur dans la zone entraîneur");
        if (this.coach) {
          this.entraineur = [this.coach];
        } else {
          this.entraineur = [];
        }
      }
    }

  },
};
</script>

<style scoped>
.delete-button {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  color: #e3342f;
  font-size: 18px;
  font-weight: bold;
  text-decoration: none;
  position: relative;
  transition: color 0.2s ease-in-out;
}

.delete-icon {
  font-size: 20px;
  line-height: 1;
  vertical-align: middle;
  position: relative;
  top: 1px;
}

.delete-text {
  font-size: 18px;
  line-height: 1;
  display: flex;
  align-items: center;
}

.delete-button::after {
  content: "";
  position: absolute;
  left: 5px;
  bottom: -2px;
  width: 96%;
  height: 2px;
  background-color: #e3342f;
  transform: scaleX(0);
  transition: transform 0.2s ease-in-out;
}

.delete-button:hover::after {
  transform: scaleX(1);
}

/* Ajout de styles pour indiquer visuellement les zones de drop */
.coach-area {
  min-height: 2rem;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.coach-area:empty, .coach-area.sortable-drag-active {
  background-color: rgba(82, 131, 89, 0.1);
  border: 1px dashed #528359;
}

.players-area {
  min-height: 2rem;
}
</style>
