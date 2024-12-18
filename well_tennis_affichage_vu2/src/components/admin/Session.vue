<template>
  <!-- Conteneur principal -->
  <div>
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <div class="flex items-center">
        <span
            :class="{ 'rotate-180': isOpen }"
            class="material-symbols-outlined transition-transform duration-300 mr-2"
        >
          expand_more
        </span>
        <h3 class="font-bold text-lg">Séances</h3>
      </div>
      <!-- Boutons d'action, déplacés à droite -->
      <div class="flex space-x-2 justify-end w-full">
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Supprimer">delete</span>
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Ajouter">person_add</span>
      </div>
    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <!-- Liste des séances -->
      <div v-for="(session, index) in sessions" :key="index" class="mb-2">
        <!-- Ligne de titre pour chaque session -->
        <div class="flex justify-between items-center cursor-pointer py-1" @click="toggleSession(index)">
          <div class="flex items-center">
            <span
                :class="{ 'rotate-180': openSessions.includes(index) }"
                class="material-symbols-outlined transition-transform duration-300 mr-2 small-icon"
            >
              expand_more
            </span>
            <h4 class="font-semibold text-black-800">{{ session.title }}</h4>
          </div>
          <!-- Ajout de l'icône interdit à côté de chaque ligne -->
          <div>
            <span class="material-symbols-outlined cursor-pointer small-icon" title="Interdit">block</span>
          </div>
        </div>

        <!-- Contenu de chaque séance -->
        <div v-if="openSessions.includes(index)" class="ml-6 mt-2">
          <table class="w-full text-sm text-gray-600">
            <tbody>
            <tr>
              <td class="text-left">Âge</td>
              <td class="text-right">{{ session.age }}</td>
              <!-- Icône interdit dans la dernière colonne -->
              <td class="text-center">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Effectif</td>
              <td class="text-right">{{ session.effective }}</td>
              <td class="text-center">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Durée</td>
              <td class="text-right">{{ session.duration }}h</td>
              <td class="text-center">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Diff. niveau</td>
              <td class="text-right">{{ session.levelDifference }}</td>
              <td class="text-center">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Diff. âge</td>
              <td class="text-right">{{ session.ageGroup }}</td>
              <td class="text-center">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Niveau</td>
              <td class="text-right">{{ session.level }}</td>
              <td class="text-center">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Sessions",
  props: {
    sessions: Array,
  },
  data() {
    return {
      isOpen: true,
      openSessions: [],
    };
  },
  methods: {
    toggleAccordion() {
      this.isOpen = !this.isOpen;
    },
    toggleSession(index) {
      // Bascule l'état de chaque session
      if (this.openSessions.includes(index)) {
        this.openSessions = this.openSessions.filter(i => i !== index);
      } else {
        this.openSessions.push(index);
      }
    },
  },
};
</script>

<style scoped>
.material-symbols-outlined {
  cursor: pointer;
  transition: transform 0.3s ease, color 0.2s ease;
}

.rotate-180 {
  transform: rotate(180deg);
}

.small-icon {
  font-size: 16px;
}

.border-b {
  border-bottom: 1px solid #e2e8f0;
}

.ml-6 {
  margin-left: 1.5rem;
}

.text-center {
  text-align: center;
}

.justify-end {
  justify-content: flex-end;
}

.w-full {
  width: 100%;
}
</style>
