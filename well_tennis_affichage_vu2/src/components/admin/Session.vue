<template>
  <!-- Conteneur principal -->
  <div>
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <div class="flex items-center session-hover">
      <span :class="{ 'rotate-180': isOpen }" class="material-symbols-outlined session-arrow transition-transform duration-300 mr-2">
        expand_more
      </span>
            <h3 class="font-bold text-lg session-title">Séances</h3>
      </div>

      <div class="flex space-x-2 justify-end w-full" v-if="!localIsMobile && userRole === 'ROLE_ADMIN'">
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Ajouter" @click="showPopup = true">add</span>
      </div>

      <!-- Popup d'ajout -->
      <div v-if="showPopup" class="fixed inset-0 bg-black bg-opacity-30 flex justify-center items-center z-50">
        <div class="bg-white p-6 rounded-lg shadow-md w-[90%] max-w-md">
          <h3 class="text-lg font-semibold mb-4 text-green-700 border-b pb-2">
            {{ editMode ? 'Modifier la contrainte' : 'Nouvelle contrainte de session' }}
          </h3>

          <form class="grid grid-cols-2 gap-4 text-sm text-gray-700">
            <!-- Âge -->
            <div>
              <label class="block mb-1 font-medium">Âge minimum</label>
              <input v-model="newConstraint.infAge" type="number" class="input-style"/>
            </div>
            <div>
              <label class="block mb-1 font-medium">Âge maximum</label>
              <input v-model="newConstraint.supAge" type="number" class="input-style"/>
            </div>

            <!-- Niveau -->
            <div>
              <label class="block mb-1 font-medium">Niveau minimum</label>
              <input v-model="newConstraint.infLevel" type="number" class="input-style"/>
            </div>
            <div>
              <label class="block mb-1 font-medium">Niveau maximum</label>
              <input v-model="newConstraint.supLevel" type="number" class="input-style"/>
            </div>

            <!-- Groupe -->
            <div>
              <label class="block mb-1 font-medium">Effectif min.</label>
              <input v-model="newConstraint.infGroup" type="number" class="input-style"/>
            </div>
            <div>
              <label class="block mb-1 font-medium">Effectif max.</label>
              <input v-model="newConstraint.supGroup" type="number" class="input-style"/>
            </div>

            <!-- Différences -->
            <div>
              <label class="block mb-1 font-medium">Écart d'âge max</label>
              <input v-model="newConstraint.ageDiff" type="number" class="input-style"/>
            </div>
            <div>
              <label class="block mb-1 font-medium">Écart de niveau max</label>
              <input v-model="newConstraint.levelDiff" type="number" class="input-style"/>
            </div>

            <!-- Durée -->
            <div class="col-span-2">
              <label class="block mb-1 font-medium">Durée (en minutes)</label>
              <input v-model="newConstraint.duration" type="number" class="input-style"/>
            </div>
          </form>

          <!-- Boutons -->
          <div class="flex justify-end space-x-2 mt-6">
            <button @click="showPopup = false" class="text-sm text-gray-600 hover:underline">Annuler</button>
            <button @click="editMode ? submitEditConstraint() : submitNewConstraint()"
                    class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 text-sm font-medium">
              {{ editMode ? 'Enregistrer' : 'Ajouter' }}
            </button>

          </div>
        </div>
      </div>


    </div>

    <div v-if="isOpen" class="mt-2">
      <!-- Liste des séances -->
      <div v-for="(constraint, index) in sessionConstraints" :key="constraint.id" class="mb-2">
        <!-- Ligne de titre pour chaque session -->
        <div class="flex justify-between items-center cursor-pointer py-1 session-sub-hover" @click="toggleSession(index)">
          <div class="flex items-center">
        <span :class="{ 'rotate-180': openSessions.includes(index) }"
              class="material-symbols-outlined session-sub-arrow transition-transform duration-300 mr-2 small-icon">
              expand_more
        </span>
            <input
                v-if="isDeleteMode"
                type="checkbox"
                class="mr-2"
                :value="constraint.id"
                v-model="selectedToDelete"
                @click.stop
            />

            <h4 class="font-semibold session-sub-title"> {{ constraint.infAge }} - {{ constraint.supAge }} ans</h4>
          </div>
          <div v-if="!localIsMobile && userRole === 'ROLE_ADMIN'" class="flex space-x-2 items-center">
            <span
                class="material-symbols-outlined cursor-pointer small-icon text-black-600 hover:text-green-600"
                title="Éditer cette contrainte"
                @click.stop="editConstraint(constraint)"
            >
              edit
            </span>
                      <span
                          class="material-symbols-outlined cursor-pointer small-icon text-black-600 hover:text-red-600"
                          title="Supprimer cette contrainte"
                          @click.stop="deleteConstraint(constraint.id)"
                      >
              delete
            </span>
          </div>
        </div>

        <!-- Contenu de chaque séance -->
        <div v-if="openSessions.includes(index)" class="ml-6 mt-2">
          <table class="w-full text-sm text-gray-600">
            <tbody>
            <tr>
              <td class="text-left">Âge</td>
              <td class="text-center">{{ constraint.infAge }} - {{ constraint.supAge }}</td>
            </tr>
            <tr>
              <td class="text-left">Effectif</td>
              <td class="text-center">{{ constraint.infGroup }} - {{ constraint.supGroup }}</td>
            </tr>
            <tr>
              <td class="text-left">Durée</td>
              <td class="text-center">{{ (constraint.duration / 60).toFixed(1) }}h</td>
            </tr>
            <tr>
              <td class="text-left">Diff. niveau</td>
              <td class="text-center">{{ constraint.levelDiff }}</td>
            </tr>
            <tr>
              <td class="text-left">Diff. âge</td>
              <td class="text-center">{{ constraint.ageDiff }}</td>
            </tr>
            <tr>
              <td class="text-left">Niveau</td>
              <td class="text-center">{{ constraint.infLevel }} - {{ constraint.supLevel }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import useSessionConstraint from "../../useJs/useSessionConstraint.js";

export default {
  name: "Sessions",
  props: {
    userRole: String,
  },
  setup() {
    const {
      sessionConstraints,
      fetchSessionConstraints,
      deleteSessionConstraint,
      createSessionConstraint,
      updateSessionConstraint,
    } = useSessionConstraint();

    const localIsMobile = ref(window.innerWidth < 768);

    const updateIsMobile = () => {
      localIsMobile.value = window.innerWidth < 768;
    };

    onMounted(() => {
      fetchSessionConstraints(); // Récupérer les contraintes de session
      window.addEventListener("resize", updateIsMobile);
    });

    return {
      sessionConstraints,
      fetchSessionConstraints,
      deleteSessionConstraint,
      createSessionConstraint,
      updateSessionConstraint,
      localIsMobile,
    };
  },
  data() {
    return {
      isOpen: true,
      openSessions: [],
      isDeleteMode: false,
      editMode: false,
      showPopup: false,
      editId: null,
      newConstraint: {
        infAge: 6,
        supAge: 12,
        infLevel: 0,
        supLevel: 10,
        infGroup: 2,
        supGroup: 6,
        ageDiff: 2,
        levelDiff: 2,
        duration: 60,
      },
    };
  },
  methods: {
    toggleAccordion() {
      this.isOpen = !this.isOpen;
    },
    toggleSession(index) {
      if (this.openSessions.includes(index)) {
        this.openSessions = this.openSessions.filter(i => i !== index);
      } else {
        this.openSessions.push(index);
      }
    },
    async deleteConstraint(id) {
      const confirmed = confirm("Supprimer cette contrainte ?");
      if (!confirmed) return;

      try {
        await this.deleteSessionConstraint(id);
        await this.fetchSessionConstraints();
      } catch (error) {
        console.error("Erreur lors de la suppression :", error);
        alert("Échec de la suppression.");
      }
    },

    async submitNewConstraint() {
      try {
        const {createSessionConstraint, fetchSessionConstraints} = useSessionConstraint();
        await createSessionConstraint(this.newConstraint);
        this.resetNewConstraint();
        await fetchSessionConstraints();
      } catch (error) {
        console.error("Erreur lors de l'ajout :", error);
        alert("Erreur lors de l'ajout de la contrainte.");
      }
    },
    editConstraint(constraint) {
      this.editMode = true;
      this.editId = constraint.id;
      this.newConstraint = { ...constraint };
      this.showPopup = true;
    },

    async submitEditConstraint() {
      try {
        const updatedConstraint = { ...this.newConstraint };
        await this.updateSessionConstraint(this.editId, updatedConstraint);
        this.showPopup = false;
        this.resetNewConstraint();
        await this.fetchSessionConstraints();
      } catch (error) {
        console.error("Erreur lors de la modification :", error);
        alert("Erreur lors de la modification de la contrainte.");
      }
    },

    resetNewConstraint() {
      this.newConstraint = {
        infAge: 6,
        supAge: 12,
        infLevel: 0,
        supLevel: 10,
        infGroup: 2,
        supGroup: 6,
        ageDiff: 2,
        levelDiff: 2,
        duration: 60,
      };
      this.editMode = false;
      this.editId = null;
    }

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

.session-hover, .session-sub-hover {
  transition: color 0.2s ease-in-out;
}

.session-title, .session-sub-title {
  transition: color 0.2s ease-in-out;
}

.session-arrow, .session-sub-arrow {
  transition: color 0.2s ease-in-out;
}

.session-hover:hover .session-title,
.session-hover:hover .session-arrow {
  color: #2F855A;
}

.input-style {
  width: 100%;
  padding: 8px 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  outline: none;
  font-size: 14px;
  transition: border-color 0.2s ease;
}

.input-style:focus {
  border-color: #2f855a;
  box-shadow: 0 0 0 1px #2f855a;
}


</style>
