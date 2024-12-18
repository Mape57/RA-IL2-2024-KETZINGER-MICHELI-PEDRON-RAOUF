<template>
  <!-- Conteneur principal -->
  <div>
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <!-- Titre Entraîneurs -->
      <div class="flex items-center">
        <span
            :class="{ 'rotate-180': isOpen }"
            class="material-symbols-outlined transition-transform duration-300 mr-2"
        >
          expand_more
        </span>
        <h3 class="font-bold text-lg">Entraîneurs</h3>
      </div>
      <!-- Boutons d'action -->
      <div class="flex space-x-2">
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Supprimer">delete</span>
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Ajouter">person_add</span>
      </div>
    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <!-- En-têtes des colonnes -->
      <div class="grid grid-cols-4 font-semibold text-gray-400 text-sm mb-2">
        <div class="text-left">Nom</div>
        <div class="text-center">Prénom</div>
        <div class="text-center">Niveau Min•Max</div>
        <div class="text-right">Âge Min•Max</div>
      </div>

        <!-- Liste des entraîneurs -->
        <div v-for="trainer in trainers"
             :key="trainer.id"
             class="grid grid-cols-4 items-center py-1"
             >
        <!-- Nom de l'entraîneur -->
        <span>{{ trainer.name }}</span>
        <!-- Prénom de l'entraîneur -->
        <span class="text-center">{{ trainer.surname }}</span>
        <!-- Niveau de l'entraîneur -->
        <span class="text-center">{{ trainer.levels }}</span>
        <!-- Tranche d'âge -->
        <span class="text-right">{{ trainer.ages }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Trainers",
  props: {
    trainers: Array,
  },
  data() {
    return {
      isOpen: true,
    };
  },
  methods: {
    toggleAccordion() {
      this.isOpen = !this.isOpen;
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
