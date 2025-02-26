<template>
  <div>
    <div class="flex justify-between items-center cursor-pointer py-2 border-b" @click="toggleAccordion">
      <div class="flex items-center trainer-hover">
       <span :class="{ 'rotate-180': isOpen }"
             class="material-symbols-outlined trainer-arrow transition-transform duration-300 mr-2">
        expand_more
       </span>
        <h3 class="font-bold text-lg trainer-title">Entraîneurs</h3>
      </div>

      <div class="flex space-x-2" v-if="!localIsMobile && userRole === 'ADMIN'">
          <span class="material-symbols-outlined small-icon cursor-pointer" title="Ajouter" ref="addTrainerButton"
                @click="addTrainer">
            person_add
          </span>

    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <!-- En-têtes des colonnes -->
      <div class="grid grid-cols-4 font-semibold text-gray-400 text-sm mb-2">
        <div class="text-left">Nom</div>
        <div class="text-left">Prénom</div>
        <div class="text-left">Niveau Min•Max</div>
        <div class="text-center">Âge Min•Max</div>
      </div>
      <div v-for="trainer in trainers"
           :key="trainer.id"
           class="grid grid-cols-4 items-center py-1"
           :class="{ 'cursor-pointer': !isMobile }"
           :ref="'trainer-' + trainer.id"
           @click="!isMobile && showTrainerInfo(trainer)">
        <span>{{ trainer.name }}</span>
        <span class="text-left">{{ trainer.surname }}</span>
        <span class="text-left">{{ trainer.infLevel }} - {{ trainer.supLevel }}</span>
        <span class="text-center">{{ trainer.infAge }} - {{ trainer.supAge }}</span>
      </div>


      <TrainerInfoView
          v-if="selectedTrainer && !isMobile && userRole === 'ADMIN'"
          :trainer="selectedTrainer"
          @close="selectedTrainer = null"
          @delete="handleTrainerDeletion"
          @save="handleTrainerSave"
      />

    </div>
  </div>
</template>

<script>
import {ref, onMounted, onUnmounted} from "vue";
import TrainerInfoView from "../vueInformations/TrainerInfoView.vue";

export default {
  name: "Trainers",
  components: {TrainerInfoView},
  props: {
    trainers: Array,
    isMobile: Boolean,
    userRole: String,
  },
  setup() {
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
      localIsMobile,
    };
  },
  data() {
    return {
      isOpen: true,
      selectedTrainer: null,
    };
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
    handleTrainerDeletion(deletedTrainerId) {
      if (this.userRole !== "ADMIN") return;
      this.$emit('update:trainers', this.trainers.filter(trainer => trainer.id !== deletedTrainerId));
      this.selectedTrainer = null;
    },
    handleTrainerSave(savedTrainer) {
      if (this.userRole !== "ADMIN") return;
      if (!savedTrainer || typeof savedTrainer !== "object") return;
      const index = this.trainers.findIndex(t => t.id === savedTrainer.id);
      if (index !== -1) {
        // Mise à jour d'un joueur existant
        this.trainers.splice(index, 1, savedTrainer);
      } else {
        // Ajout d'un nouveau joueur
        this.trainers.push(savedTrainer);
      }

      // Émet la liste mise à jour au parent
      this.$emit("update:trainers", [...this.trainers]);

      this.$nextTick(() => {
        const newTrainerElement = this.$refs[`trainer-${savedTrainer.id}`]?.[0];
        if (newTrainerElement) {
          newTrainerElement.scrollIntoView({ behavior: "smooth", block: "center" });

          // Ajouter une classe temporaire pour l'effet de mise en valeur
          newTrainerElement.classList.add("highlighted");
          setTimeout(() => newTrainerElement.classList.remove("highlighted"), 3000); // Retire l'effet après 3s
        }
      });
    },
    addTrainer() {
      if (this.localIsMobile || this.userRole !== "ADMIN") return;
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
  grid-template-columns: 2fr 1.7fr 1fr 1fr;
  gap: 0.5rem;
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
  background-color: yellow;
  transition: background-color 1s ease-in-out;
}


::v-deep(.highlighted:hover) {
  background-color: lightyellow; /* Reste subtil au survol */
}

</style>
