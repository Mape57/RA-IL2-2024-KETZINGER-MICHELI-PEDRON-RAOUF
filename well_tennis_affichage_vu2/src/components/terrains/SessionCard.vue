<template>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <div>
    <div
        v-if="!isMobile"
        class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 shadow-sm mb-4 flex lg:flex-row items-center"
    >
      <div
          class="text-custom-color font-bold text-lg lg:w-[10%] text-center lg:border-r border-gray-800 lg:pr-4 mb-4 lg:mb-0 flex flex-col justify-center items-center">
        <div v-if="!isEditingTime" @click="enableTimeEditing" class="cursor-pointer hover:bg-gray-100 p-1 rounded">
          <p>{{ startTime }}</p>
          <p>{{ endTime }}</p>
        </div>
        <div v-else ref="timeEditArea" class="flex flex-col gap-2">
          <input
              type="time"
              v-model="editableSession.start"
              class="time-input border border-gray-300 rounded px-1 py-0.5 text-sm w-24"
          />
          <input
              type="time"
              v-model="editableSession.stop"
              class="time-input border border-gray-300 rounded px-1 py-0.5 text-sm w-24"
          />
          <div class="flex justify-center mt-2">
            <button
                @click="validateAndUpdateTime"
                class="text-xs text-white bg-green-600 hover:bg-green-700 rounded px-4 py-1 w-full"
            >
              Enregistrer
            </button>
          </div>
        </div>
      </div>

      <div class="w-[25%] h-[20%] lg:pl-2 mb-4 lg:mb-0 flex flex-col justify-center">
        <div class="mb-2">
          <div class="font-bold text-sm">Entraîneur :</div>

          <VueDraggable
              v-model="entraineur"
              :group="{ name: 'coach', pull: true, put: ['trainers', 'coach'] }"
              item-key="idtrainer"
              :sort="false"
              class="pr-1 coach-area"
              :class="{'drag-active': isDragging && draggedItemType === 'coach'}"
              @start="onDragStart"
              @end="onCoachDragEnd"
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
          <span class="font-bold text-sm">Âge :</span> {{ ageGroup }}
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
              :class="{'players-drag-active': isDragging && draggedItemType === 'player'}"
              @start="onDragStart"
              @end="onPlayerDragEnd"
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
              :class="{'players-drag-active': isDragging && draggedItemType === 'player'}"
              @start="onDragStart"
              @end="onPlayerDragEnd"
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
        <div
            class="trash-container"
            :class="{ 'visible': isDragging }"
        >
          <VueDraggable
              v-model="trashItems"
              class="trash-draggable"
              :group="{ name: 'trash', put: ['players', 'coach'] }"
              item-key="idtrash"
              :sort="false"
              @add="onTrashDrop"
              @end="onTrashDragEnd"
          >
            <div class="trash-area">
              <span class="material-icons trash-icon">delete_outline</span>
            </div>
          </VueDraggable>
        </div>
      </div>


      <div class="lg:w-[10%] flex justify-end" v-if="userRole === 'ROLE_ADMIN'">
        <button @click="showDeletePopup" class="delete-button">
          <span class="material-icons delete-icon">delete</span>
        </button>

        <!-- Confirmation delete Popup -->
        <div v-if="showDeleteConfirmation" class="delete-confirmation-popup">
          <div class="delete-confirmation-content">
            <p>Êtes-vous sûr de vouloir supprimer cette session ?</p>
            <div class="delete-confirmation-buttons">
              <button @click="confirmDelete" class="confirm-delete-btn">Supprimer</button>
              <button @click="cancelDelete" class="cancel-delete-btn">Annuler</button>
            </div>
          </div>
        </div>


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
          {{ showInfo ? (ageGroup + ', Niveau: ' + skillLevel) : (startTime + ' - ' + endTime) }}
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

    </div>
  </div>
</template>

<script>
import {VueDraggable} from "vue-draggable-plus";
import {watchEffect, ref, reactive} from "vue";
import {useSessionsStore} from "../../store/useSessionsStore";
import {usePlayersStore} from "../../store/usePlayersStore";

export default {
  components: {VueDraggable},
  name: "SessionCard",
  emits: ["update-players", "delete", "player-removed", "update-coach", "remove-coach", "times-updated"],
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
  setup(props) {
    const sessionsStore = useSessionsStore();
    const playersStore = usePlayersStore();

    const isEditingTime = ref(false);
    const editableSession = reactive({
      start: '',
      stop: ''
    });

    return {
      sessionsStore,
      playersStore,
      isEditingTime,
      editableSession
    };
  },
  data() {
    return {
      entraineur: [],
      isMobile: false,
      showInfo: false,
      leftPlayers: [],
      rightPlayers: [],
      lastRemovedPlayer: null,
      isDragging: false,
      draggedItemType: null,
      trashItems: [],
      showDeleteConfirmation: false,
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

      // Initialize editable session with current times
      this.editableSession.start = this.formatTimeForInput(this.startTime);
      this.editableSession.stop = this.formatTimeForInput(this.endTime);
    });
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.checkScreenSize);
    document.removeEventListener('mousedown', this.handleClickOutside);
  },
  methods: {
    handleClickOutside(event) {
      const timeEditArea = this.$refs.timeEditArea;
      if (this.isEditingTime && timeEditArea && !timeEditArea.contains(event.target)) {
        // Reset to original values
        this.editableSession.start = this.formatTimeForInput(this.startTime);
        this.editableSession.stop = this.formatTimeForInput(this.endTime);
        this.isEditingTime = false;

        // Remove event listener when editing is closed
        document.removeEventListener('mousedown', this.handleClickOutside);
      }
    },

    enableTimeEditing() {
      // Si l'utilisateur est un administrateur, activer le mode d'édition des horaires
      if (this.userRole === 'ROLE_ADMIN') {
        this.isEditingTime = true;

        // Add click outside listener
        this.$nextTick(() => {
          document.addEventListener('mousedown', this.handleClickOutside);
        });
      }
    },

    formatTimeForInput(timeString) {
      // Retourne directement la chaîne d'heure sans modification
      // (peut être étendu pour un formatage plus complexe si nécessaire)
      return timeString;
    },

    validateAndUpdateTime() {
      // Vérifie que l'heure de fin est bien après l'heure de début
      if (this.editableSession.start >= this.editableSession.stop) {
        alert("L'heure de fin doit être après l'heure de début");

        // Réinitialise les valeurs aux horaires d'origine en cas d'erreur
        this.editableSession.start = this.formatTimeForInput(this.startTime);
        this.editableSession.stop = this.formatTimeForInput(this.endTime);
        return;
      }

      // Si la validation est correcte, procéder à la mise à jour de la session
      this.updateSessionTimes();
    },

    async updateSessionTimes() {
      try {
        // Recherche la session actuelle dans le store à partir de son ID
        const currentSession = this.sessionsStore.sessions.find(s => s.id === this.sessionId);

        // Si la session n'existe pas, afficher une erreur et interrompre le processus
        if (!currentSession) {
          console.error("Session introuvable :", this.sessionId);
          return;
        }

        // Création d’un nouvel objet session avec les horaires mis à jour
        const sessionData = {
          ...currentSession,
          id: this.sessionId,
          start: this.editableSession.start,
          stop: this.editableSession.stop,
          // On conserve les joueurs et l'entraîneur actuels
          players: this.players,
          idTrainer: this.coach
        };

        // Envoi de la session mise à jour au backend via le store
        await this.sessionsStore.updateSession(sessionData);

        // Affiche une confirmation dans la console
        console.log("Horaires de la session mis à jour avec succès");

        // Sortie du mode d'édition
        this.isEditingTime = false;

        // Émet un événement vers le parent pour signaler que les horaires ont été modifiés
        // Inclut les anciens et nouveaux horaires pour pouvoir calculer la différence d'heures
        this.$emit('times-updated', {
          sessionId: this.sessionId,
          oldStart: this.startTime,
          oldStop: this.endTime,
          newStart: this.editableSession.start,
          newStop: this.editableSession.stop
        });
      } catch (error) {
        // En cas d’erreur durant la mise à jour, afficher une alerte et loguer l’erreur
        console.error("Erreur lors de la mise à jour des horaires :", error);
        alert("Une erreur est survenue lors de la mise à jour des horaires");
      }
    },

    checkScreenSize() {
      this.isMobile = window.innerWidth <= 1024;
    },

    moveValidator(evt) {
      // Si on essaie de déposer dans la zone coach, vérifier que c'est bien un entraîneur
      if (evt.to.classList.contains('coach-area')) {
        // Vérifier que l'élément vient du groupe "trainers"
        return (
        evt.from.className.includes('trainer-list') ||
        evt.from.className.includes('coach-area')
      );
      }
      return true; // Autoriser tous les autres déplacements
    },
    onDragStart(evt) {
      this.isDragging = true;
      // Check if it's a coach or player being dragged
      if (evt.from.className.includes('coach-area')) {
        this.draggedItemType = 'coach';
      } else if (evt.from.className.includes('trainer-list')) {
        this.draggedItemType = 'coach'; // Consider trainer-list drags as coach type
      } else {
        this.draggedItemType = 'player';
      }
      console.log("Type dragged:", this.draggedItemType);

      // Find all drop zones of the same type and apply highlight styling
      this.highlightDropZones();
    },

    highlightDropZones() {
      // This method is called when drag starts to apply highlighting to all valid drop zones
      document.querySelectorAll('.coach-area').forEach(zone => {
        if (this.draggedItemType === 'coach') {
          zone.classList.add('drag-active');
        }
      });

      document.querySelectorAll('.players-area').forEach(zone => {
        if (this.draggedItemType === 'player') {
          zone.classList.add('players-drag-active');
        }
      });
    },


    onPlayerDragEnd(evt) {
      console.log("bien fini de dragger");
      this.isDragging = false;
      this.removeDropZoneHighlights();

      // Si le drop n'est pas dans la corbeille
      if (!evt.to.classList.contains('trash-container')) {
        const updatedPlayers = [...this.leftPlayers, ...this.rightPlayers];
        this.$emit('update-players', {
          sessionId: this.sessionId,
          players: updatedPlayers
        });
      }
    },

    onPlayerAdded(evt) {
      console.log("Player added to session", this.sessionId);
      if (!evt.to.classList.contains('trash-container')) {
        const updatedPlayers = [...this.leftPlayers, ...this.rightPlayers];
        this.$emit('update-players', {
          sessionId: this.sessionId,
          players: updatedPlayers
        });
        
        // Forcer un rafraîchissement des sessions après l'ajout d'un joueur
        setTimeout(() => {
          this.sessionsStore.refreshSessions();
        }, 300);
      }
    },

    onPlayerRemoved(evt) {
      console.log("Player removed from session", this.sessionId);
      const playerIndex = evt.oldIndex;

      let removedPlayer;
      if (evt.from.className.includes('pr-1')) {
        removedPlayer = this.players[playerIndex];
      } else {
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
    onTrashDrop(evt) {
      console.log("Élément déposé dans la corbeille:", evt.from.className);

      if (evt.from.classList.contains('coach-area')) {
        console.log("Suppression de l'entraîneur pour la session:", this.sessionId);
        const oldCoach = this.coach; // Enregistrer le coach actuel avant de le supprimer
        
        this.$emit('update-coach', {
          sessionId: this.sessionId,
          coach: null,
          oldCoach: oldCoach // Passer l'ancien coach pour la mise à jour des heures
        });
        
        this.entraineur = [];
        // Vider immédiatement la corbeille pour le coach
        this.trashItems = [];
        
        // Forcer un rafraîchissement des sessions juste après avoir supprimé un coach
        setTimeout(() => {
          this.sessionsStore.refreshSessions();
        }, 300); // Attendre que le backend ait eu le temps de traiter
      } else {
        console.log("Début suppression joueur");

        // Récupération directe de l'élément déposé
        const draggedIndex = evt.oldIndex;
        const isFromLeftSide = evt.from.classList.contains('pr-1');

        // Détermine de quel côté provient le joueur
        let playerToRemove = null;
        let updatedPlayers = [];

        if (isFromLeftSide) {
          playerToRemove = this.leftPlayers[draggedIndex];
          console.log("Joueur à supprimer (gauche):", playerToRemove);

          // Créer une nouvelle liste sans le joueur
          updatedPlayers = [...this.players].filter(p =>
              !(p.id === playerToRemove.id)
          );
        } else {
          playerToRemove = this.rightPlayers[draggedIndex];
          console.log("Joueur à supprimer (droite):", playerToRemove);

          // Créer une nouvelle liste sans le joueur
          updatedPlayers = [...this.players].filter(p =>
              !(p.id === playerToRemove.id)
          );
        }

        console.log("Joueur supprimé:", playerToRemove?.id);
        console.log("Liste mise à jour:", updatedPlayers);

        // Nous ne gérons plus la décrémentation localement car cela peut causer des erreurs
        // Le backend mettra à jour le nombre de sessions et nous récupérerons les données à jour

        // Vider immédiatement la corbeille pour le prochain drag
        this.trashItems = [];

        // Envoyer la liste mise à jour sans le joueur supprimé
        this.$emit('update-players', {
          sessionId: this.sessionId,
          players: updatedPlayers
        });

          // Forcer un rafraîchissement des sessions après suppression d'un joueur
          setTimeout(() => {
            this.sessionsStore.refreshSessions();
          }, 300); // Attendre que le backend ait eu le temps de traiter
      }

      this.isDragging = false;
      this.removeDropZoneHighlights();
    },

    onTrashDragEnd() {
      console.log("Fin du drag dans la corbeille");
      this.isDragging = false;
      this.draggedItemType = null;
      this.removeDropZoneHighlights();

      // S'assurer que la corbeille est vide
      this.trashItems = [];
    },

    onCoachDragEnd() {
      console.log("Fin du drag d'un entraîneur");
      this.isDragging = false;
      this.draggedItemType = null;
      this.removeDropZoneHighlights();

      // Vider la corbeille pour s'assurer qu'elle est prête pour le prochain drag
      this.trashItems = [];
    },

    onCoachDropped(evt) {
      if (evt.from.className.includes('trainer-list' ) || evt.from.className.includes('coach-area')) {
        let newCoach = null;
        newCoach = this.entraineur[0];

        if (newCoach && (newCoach.id || newCoach.idtrainer)) {
          // Make sure the coach object has a valid ID for backend persistence
          const coachToSend = {
            id: newCoach.id || newCoach.idtrainer,
            name: newCoach.name,
            surname: newCoach.surname
          };
          
          // Inclure l'ancien coach dans l'événement pour permettre la mise à jour des heures
          this.$emit("update-coach", {
            sessionId: this.sessionId,
            coach: coachToSend,
            oldCoach: this.coach
          });

          console.log("Coach ajouté à la session avec ID:", coachToSend.id);
          
          // Forcer un rafraîchissement des sessions après l'ajout d'un coach
          setTimeout(() => {
            this.sessionsStore.refreshSessions();
          }, 300);
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
    },

    removeDropZoneHighlights() {
      // Remove all highlighting classes when drag ends
      document.querySelectorAll('.coach-area').forEach(zone => {
        zone.classList.remove('drag-active');
      });

      document.querySelectorAll('.players-area').forEach(zone => {
        zone.classList.remove('players-drag-active');
      });
    },

    // Delete confirmation methods
    showDeletePopup() {
      this.showDeleteConfirmation = true;
    },

    confirmDelete() {
      this.$emit('delete');
      this.showDeleteConfirmation = false;
    },

    cancelDelete() {
      this.showDeleteConfirmation = false;
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

.coach-area:empty {
  background-color: rgba(82, 131, 89, 0.1);
  border: 1px dashed #528359;
}

.players-area {
  min-height: 2rem;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.trash-container {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 8px;
  opacity: 0; /* Remplace O par 0 */
  transition: opacity 0.3s ease;
}

.trash-container.visible {
  opacity: 1; /* Visible pendant le drag */
}

.trash-area {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ffebeb;
  border: 2px dashed #e3342f;
}

.trash-area:hover {
  background-color: #ffebeb;
  transform: scale(1.1);
}

.trash-icon {
  color: #e3342f;
  font-size: 24px;
}

/* Drag and drop highlight styles */
.drag-active {
  background-color: rgba(82, 131, 89, 0.15);
  border-radius: 8px;
  border: 2px groove #528359;
  transition: all 0.3s ease;
}

.players-drag-active {
  background-color: rgba(82, 131, 89, 0.15);
  border-radius: 6px;
  border: 2px groove #528359;
  padding: 4px;
  min-height: 40px;
  transition: all 0.3s ease;
}

/* Delete confirmation popup styles */
.delete-confirmation-popup {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.delete-confirmation-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  text-align: center;
  max-width: 400px;
  width: 90%;
}

.delete-confirmation-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 20px;
}

.confirm-delete-btn {
  background-color: #e3342f;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.2s;
}

.confirm-delete-btn:hover {
  background-color: #c42b26;
}

.cancel-delete-btn {
  background-color: #528359;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.2s;
}

.cancel-delete-btn:hover {
  background-color: #4a5568;
}
</style>

<style>
.time-input::-webkit-calendar-picker-indicator {
  background-color: transparent;
  cursor: pointer;
}
</style>
