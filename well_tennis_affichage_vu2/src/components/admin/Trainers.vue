<template>
  <div>
    <div class="flex justify-between items-center cursor-pointer py-2 border-b" @click="toggleAccordion">
      <div class="flex items-center">
        <span :class="{ 'rotate-180': isOpen }" class="material-symbols-outlined transition-transform duration-300 mr-2">
          expand_more
        </span>
        <h3 class="font-bold text-lg">Entraîneurs</h3>
      </div>
      <!-- Boutons d'action -->
      <div class="flex space-x-2">
        <span class="material-symbols-outlined small-icon cursor-pointer"
              title="Ajouter"
              ref="addTrainerButton"
              @click="addTrainer"
        >person_add</span>
      </div>
    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <!-- En-têtes des colonnes -->
      <div class="grid grid-cols-4 font-semibold text-gray-400 text-sm mb-2">
        <div class="text-left">Nom</div>
        <div class="text-center">Prénom</div>
        <div class="text-center">Niveau Min•Max</div>
        <div class="text-center">Âge Min•Max</div>
      </div>
      <div v-for="trainer in trainers" :key="trainer.id" class="grid grid-cols-4 items-center py-1" :class="{ 'cursor-pointer': !isMobile }" @click="!isMobile && showTrainerInfo(trainer)">
        <span>{{ trainer.name }}</span>
        <!-- Prénom de l'entraîneur -->
        <span class="text-center">{{ trainer.surname }}</span>
        <span class="text-center">{{ trainer.infLevel }} - {{ trainer.supLevel }}</span>
        <span class="text-center">{{ trainer.infAge }} - {{ trainer.supAge }}</span>
      </div>
      <TrainerInfoView v-if="selectedTrainer && !isMobile" :trainer="selectedTrainer" @close="selectedTrainer = null" @delete="handleTrainerDeletion" @save="handleTrainerSave" />
    </div>
  </div>
</template>

<script>
import TrainerInfoView from "../vueInformations/TrainerInfoView.vue";

export default {
  name: "Trainers",
  components: { TrainerInfoView },
  props: { trainers: Array },
  data() {
    return {
      isOpen: true,
      selectedTrainer: null, // Joueur sélectionné pour afficher les détails
      isMobile: window.innerWidth < 768,
    };
  },
  mounted() {
    window.addEventListener("resize", this.updateIsMobile);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.updateIsMobile);
  },
  methods: {
    updateIsMobile() {
      this.isMobile = window.innerWidth < 768;
    },
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
      if (this.isMobile) return;
      if (this.selectedTrainer && this.selectedTrainer.id === trainer.id) {
        // Si le trainer sélectionné est cliqué à nouveau, on ferme l'onglet
        this.selectedTrainer = null;
      } else {
        // Sinon, on met à jour le trainer sélectionné
        this.selectedTrainer = trainer;
      }
    },
    handleTrainerDeletion(deletedTrainerId) {
      const updatedTrainers = this.trainers.filter(trainer => trainer.id !== deletedTrainerId);
      this.$emit('update:trainers', updatedTrainers); // Émet la liste mise à jour au parent
      this.selectedTrainer = null; // Ferme l'affichage des détails
    },
    handleTrainerSave(savedTrainer) {
      if (!savedTrainer || typeof savedTrainer !== "object") {
        console.error("Données invalides reçues dans handleTrainerSave :", savedTrainer);
        return;
      }
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
    },
    addTrainer() {
      if (this.isMobile) return;
      // Initialise un trainer vide
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
.grid {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr 1fr;
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
</style>
