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

      <div class="flex space-x-2 justify-end w-full" v-if="!localIsMobile && userRole === 'ADMIN'">
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Supprimer">delete</span>
        <span class="material-symbols-outlined small-icon cursor-pointer" title="Ajouter">person_add</span>
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
            <h4 class="font-semibold session-sub-title"> {{ constraint.infAge }} - {{ constraint.supAge }} ans</h4>
          </div>
          <div v-if="!localIsMobile && userRole === 'ADMIN'">
            <span class="material-symbols-outlined cursor-pointer small-icon" title="Interdit">block</span>
          </div>
        </div>

        <!-- Contenu de chaque séance -->
        <div v-if="openSessions.includes(index)" class="ml-6 mt-2">
          <table class="w-full text-sm text-gray-600">
            <tbody>
            <tr>
              <td class="text-left">Âge</td>
              <td class="text-center">{{ constraint.infAge }} - {{ constraint.supAge }}</td>
              <td class="text-center" v-if="!localIsMobile && userRole === 'ADMIN'">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Effectif</td>
              <td class="text-center">{{ constraint.infGroup }} - {{ constraint.supGroup }}</td>
              <td class="text-center" v-if="!localIsMobile && userRole === 'ADMIN'">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Durée</td>
              <td class="text-center">{{ (constraint.duration / 60).toFixed(1) }}h</td>
              <td class="text-center" v-if="!localIsMobile && userRole === 'ADMIN'">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Diff. niveau</td>
              <td class="text-center">{{ constraint.levelDiff }}</td>
              <td class="text-center" v-if="!localIsMobile && userRole === 'ADMIN'">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Diff. âge</td>
              <td class="text-center">{{ constraint.ageDiff }}</td>
              <td class="text-center" v-if="!localIsMobile && userRole === 'ADMIN'">
                <span class="material-symbols-outlined small-icon cursor-pointer" title="Interdit">block</span>
              </td>
            </tr>
            <tr>
              <td class="text-left">Niveau</td>
              <td class="text-center">{{ constraint.infLevel }} - {{ constraint.supLevel }}</td>
              <td class="text-center" v-if="!localIsMobile && userRole === 'ADMIN'">
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
import { ref, onMounted } from "vue";
import useSessionConstraint from "../../useJs/useSessionConstraint.js";

export default {
  name: "Sessions",
  props: {
    userRole: String,
  },
  setup() {
    const { sessionConstraints, fetchSessionConstraints } = useSessionConstraint();
    const localIsMobile = ref(window.innerWidth < 768);

    const updateIsMobile = () => {
      localIsMobile.value = window.innerWidth < 768;
    };

    onMounted(() => {
      fetchSessionConstraints(); // Récupérer les contraintes de session
      window.addEventListener("resize", updateIsMobile);
    });

    return {
      localIsMobile,
      sessionConstraints
    };
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

.session-sub-hover:hover .session-sub-title,
.session-sub-hover:hover .session-sub-arrow {
  color: #2F855A;
}

</style>
