<template>
  <div>
    <div class="flex justify-between items-center cursor-pointer py-2 border-b"
         @click="toggleAccordion"
    >
      <div class="flex items-center trainer-hover">
       <span :class="[isOpen ? 'rotate-0' : 'rotate-270']"
             class="material-symbols-outlined trainer-arrow transition-transform duration-300 mr-2">
        expand_more
       </span>
        <h3 class="font-bold text-lg trainer-title">Entraîneurs</h3>
      </div>

      <div class="flex space-x-2" v-if="!localIsMobile && userRole === 'ROLE_ADMIN'">
          <span class="material-symbols-outlined small-icon cursor-pointer" title="Ajouter" ref="addTrainerButton"
                @click="addTrainer">
            person_add
          </span>

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
          <div class="text-left">Niveau Min•Max</div>
          <div class="text-center">Âge Min•Max</div>
          <div class="text-center">Nb. heures</div>
        </div>

        <VueDraggable
            v-model="filteredTrainers"
            :group="{ name: 'trainers', pull: 'clone', put: false }"
            item-key="id"
            :sort="false"
            @start="onDragStart"
            @end="onDragEnd"
            class="trainer-list"
        >


          <div v-for="trainer in filteredTrainers"
               :key="trainer.id"
               class="grid grid-cols-4 items-center py-1"
               :class="{ 'cursor-pointer': !isMobile }"
               :ref="'trainer-' + trainer.id"
               @click="!isMobile && showTrainerInfo(trainer)">
            <span>{{ trainer.name }}</span>
            <span class="text-left">{{ trainer.surname }}</span>
            <span class="text-left">{{ trainer.infLevel }} - {{ trainer.supLevel }}</span>
            <span class="text-center">{{ trainer.infAge }} - {{ trainer.supAge }}</span>
            <span class="text-center">{{
                Math.round(trainer.weeklyMinutes / 60)
              }} / {{ Math.round(trainer.infWeeklyMinutes / 60) }}</span>
          </div>

        </VueDraggable>


        <TrainerInfoView
            v-if="selectedTrainer && !isMobile && userRole === 'ROLE_ADMIN'"
            :trainer="selectedTrainer"
            @close="selectedTrainer = null"
            @delete="handleTrainerDeletion"
            @save="handleTrainerSave"
        />
      </div>
    </div>
  </div>

</template>

<script>
import {ref, onMounted, onUnmounted, computed} from "vue";
import { useTrainersStore } from "../../store/useTrainersStore.js";
import TrainerInfoView from "../vueInformations/TrainerInfoView.vue";
import {VueDraggable} from "vue-draggable-plus";

export default {
  name: "Trainers",
  components: {VueDraggable, TrainerInfoView},
  props: {
    isMobile: Boolean,
    userRole: String,
    searchQuery: String,
  },
  setup() {
    const trainersStore = useTrainersStore();
    
    // Get the trainers and loading state from the store
    const trainers = computed(() => trainersStore.trainers);
    const loading = computed(() => trainersStore.loading);
    const localIsMobile = ref(window.innerWidth < 768);

    const updateIsMobile = () => {
      localIsMobile.value = window.innerWidth < 768;
    };

    onMounted(() => {
      updateIsMobile();
      window.addEventListener("resize", updateIsMobile);
      
      // Fetch trainers data when component is mounted
      trainersStore.fetchTrainers();
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateIsMobile);
    });

    return {
      trainers,
      loading,
      trainersStore,
      localIsMobile,
    };
  },
  data() {
    return {
      isOpen: true,
      selectedTrainer: null,
      draggedTrainer: null,
    };
  },
  computed: {
    filteredTrainers() {
      return this.trainers
          .filter(trainer =>
              trainer.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
              trainer.surname.toLowerCase().includes(this.searchQuery.toLowerCase())
          );
    },
  },
  methods: {
    toggleAccordion(event) {
      const addButton = this.$refs.addTrainerButton;

      // Si le bouton "Ajouter" est cliqué et que le panneau est déjà ouvert, ne pas refermer
      if (addButton && addButton.contains(event.target) && this.isOpen) {
        return;
      }

      // Sinon, basculer l'état d'ouverture
      this.isOpen = !this.isOpen;
    },
    showTrainerInfo(trainer) {
      if (this.localIsMobile) return;
      this.selectedTrainer = this.selectedTrainer?.id === trainer.id ? null : trainer;
    },
    async handleTrainerDeletion(deletedTrainerId) {
      if (this.userRole !== "ROLE_ADMIN") return;
      await this.trainersStore.deleteTrainer(deletedTrainerId);
      this.selectedTrainer = null;
    },
    async handleTrainerSave(savedTrainer) {
      if (this.userRole !== "ROLE_ADMIN") return;
      if (!savedTrainer || typeof savedTrainer !== "object") return;
      
      try {
        if (savedTrainer.id) {
          // Update existing trainer
          await this.trainersStore.updateTrainer(savedTrainer.id, savedTrainer);
        } else {
          // Create new trainer
          savedTrainer = await this.trainersStore.createTrainer(savedTrainer);
        }

        this.$nextTick(() => {
          const newTrainerElement = this.$refs[`trainer-${savedTrainer.id}`]?.[0];
          if (newTrainerElement) {
            newTrainerElement.scrollIntoView({behavior: "smooth", block: "center"});

            // Ajouter une classe temporaire pour l'effet de mise en valeur
            newTrainerElement.classList.add("highlighted");
            setTimeout(() => newTrainerElement.classList.remove("highlighted"), 3000); // Retire l'effet après 3s
          }
        });
      } catch (error) {
        console.error("Error saving trainer:", error);
      }
    },
    addTrainer() {
      if (this.localIsMobile || this.userRole !== "ROLE_ADMIN") return;
      this.selectedTrainer = {
        id: null, // Pas encore défini
        name: "",
        surname: "",
        infLevel: 0,
        supLevel: 0,
        infAge: 0,
        supAge: 0,
        email: "",
        password: "",
        partTime: false,
        admin: false,
        disponibilities: [],
      }; // Ouvre TrainerInfoView avec ce nouveau joueur
    },
    onDragStart(event) {
      console.log('start')
      this.draggedTrainer = event.item.element;
    },
    onDragEnd() {
      console.log('update')
      this.$emit("trainer-dragged", this.draggedTrainer);
      this.draggedTrainer = null;
    }


  },
};
</script>


<style scoped>

.trainer-hover {
  transition: color 0.2s ease-in-out;
}

.trainer-title {
  transition: color 0.2s ease-in-out;
}

.trainer-arrow {
  transition: color 0.2s ease-in-out;
}

.trainer-hover:hover .trainer-title,
.trainer-hover:hover .trainer-arrow {
  color: #2f855a;
}

.grid {
  display: grid;
  grid-template-columns: 2fr 1.7fr 1fr 1fr 1fr;
  gap: 0.5rem;
}

.material-symbols-outlined {
  cursor: pointer;
  transition: transform 0.3s ease;
}

.small-icon {
  font-size: 18px;
}

.rotate-270 {
  transform: rotate(0deg);
}

.rotate-0 {
  transform: rotate(-90deg);
}
.border-b {
  border-bottom: 1px solid #e2e8f0;
}

::v-deep(.highlighted) {
  background-color: yellow;
  transition: background-color 1s ease-in-out;
}


::v-deep(.highlighted:hover) {
  background-color: lightyellow; /* Reste subtil au survol */
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
