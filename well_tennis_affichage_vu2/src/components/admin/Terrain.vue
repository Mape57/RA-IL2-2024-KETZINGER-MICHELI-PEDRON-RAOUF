<template>
  <div>
    <!-- Conteneur principal -->
    <div class="flex justify-between items-center py-2 border-b">
      <div class="flex items-center terrain-hover cursor-pointer" @click="toggleAccordion">
    <span :class="{ 'rotate-180': !isOpen }" class="material-symbols-outlined terrain-arrow transition-transform duration-300 mr-2">
      expand_more
    </span>
        <h3 class="font-bold text-lg terrain-title">Terrains</h3>
      </div>

      <div v-if="!localIsMobile && userRole === 'ROLE_ADMIN'">
    <span
        class="material-symbols-outlined small-icon cursor-pointer text-black hover:text-green-800"
        title="Ajouter un terrain"
        @click="openCreateTerrain"
    >add</span>
      </div>
    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <div v-for="terrain in sortedTerrains" :key="terrain.id" class="mb-2">
      <!-- Terrain principal -->
        <div class="flex justify-between items-center cursor-pointer py-1 terrain-sub-hover"
             @click="toggleTerrain(terrain.id)">
          <div class="flex items-center">
            <span :class="{ 'rotate-180': !openTerrains.includes(terrain.id) }"
                  class="material-symbols-outlined terrain-sub-arrow transition-transform duration-300 mr-2 small-icon">
                   expand_more
            </span>
            <h4 class="font-semibold terrain-sub-title">{{ terrain.name }}</h4>
          </div>
          <div class="flex space-x-2" v-if="!localIsMobile && userRole === 'ROLE_ADMIN'">
              <span
                  class="material-symbols-outlined small-icon cursor-pointer text-black-600 hover:text-green-600"
                  title="Modifier le terrain"
                  @click.stop="editTerrain(terrain)"
              >edit</span>

            <span
                class="material-symbols-outlined small-icon cursor-pointer text-black-600 hover:text-red-600"
                title="Supprimer le terrain"
                @click.stop="deleteTerrain(terrain.id)"
            >delete</span>
          </div>
        </div>

        <!-- Horaires des terrains -->
        <div v-if="openTerrains.includes(terrain.id)" class="ml-6 mt-2">
          <table class="w-full text-sm text-gray-600">
            <thead>
            <tr>
              <th class="text-left">Jour</th>
              <th class="text-center">Ouverture</th>
              <th class="text-center">Fermeture</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="time in terrain.times" :key="time.id">
              <td>{{ convertDay(time.dayWeek)}}</td>
              <td class="text-center">{{ time.start }}</td>
              <td class="text-center">{{ time.stop }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- Popup terrain (adapté visuellement à celui des Séances) -->
    <div v-if="showTerrainPopup" class="fixed inset-0 bg-black bg-opacity-30 flex justify-center items-center z-50">
      <div class="bg-white p-6 rounded-lg shadow-md w-[90%] max-w-md max-h-[90vh] overflow-y-auto">
        <h3 class="text-lg font-semibold mb-4 text-green-700 border-b pb-2">
          {{ editTerrainMode ? 'Modifier le terrain' : 'Nouveau terrain' }}
        </h3>

        <!-- Formulaire -->
        <form class="grid grid-cols-2 gap-4 text-sm text-gray-700">
          <!-- Nom -->
          <div class="col-span-2">
            <label class="block mb-1 font-medium">Nom du terrain</label>
            <input v-model="terrainForm.name" type="text" class="input-style" />
          </div>

          <!-- Horaires (boucle) -->
          <div class="col-span-2">
            <label class="block mb-1 font-medium">Horaires</label>
            <div v-for="(time, index) in terrainForm.times" :key="time.id" class="mb-2 border p-2 rounded">
              <div class="flex gap-2 mb-1 items-center">
                <select v-model="time.dayWeek" class="input-style w-full">
                  <option disabled value="">-- Jour --</option>
                  <option v-for="(day, idx) in daysOfWeek" :key="idx + 1" :value="idx + 1">{{ day }}</option>
                </select>
                <input type="time" v-model="time.start" class="input-style w-full" />
                <input type="time" v-model="time.stop" class="input-style w-full" />
                <button @click.prevent="removeTime(index)" class="text-red-600 hover:underline text-sm">Suppr</button>
              </div>
            </div>
            <button @click.prevent="addTime" class="text-green-700 hover:underline text-sm mt-1">+ Ajouter un horaire</button>
          </div>
        </form>

        <!-- Boutons -->
        <div class="flex justify-end space-x-2 mt-6">
          <button @click="cancelTerrainEdit" class="text-sm text-gray-600 hover:underline">Annuler</button>
          <button @click="editTerrainMode ? submitEditedTerrain() : submitNewTerrain()"
                  class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 text-sm font-medium">
            {{ editTerrainMode ? 'Enregistrer' : 'Ajouter' }}
          </button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { ref, computed, onMounted, onUnmounted } from "vue";
import useTerrain from "../../useJs/useTerrain";

export default {
  name: "Terrains",
  props: {
    terrains: {
      type: Array,
      required: true,
    },
    userRole: String,
  },
  setup() {
    const {
      terrains,
      fetchTerrains,
      createTerrain,
      updateTerrain,
      deleteTerrain: deleteTerrainService,
    } = useTerrain();

    const localIsMobile = ref(window.innerWidth < 768);

    const isOpen = ref(true);
    const openTerrains = ref([]);

    const showTerrainPopup = ref(false);
    const editTerrainMode = ref(false);
    const editedTerrainId = ref(null);

    const terrainForm = ref({
      name: "",
      times: [],
    });

    const daysOfWeek = [
      "Lundi",
      "Mardi",
      "Mercredi",
      "Jeudi",
      "Vendredi",
      "Samedi",
      "Dimanche",
    ];

    const updateIsMobile = () => {
      localIsMobile.value = window.innerWidth < 768;
    };

    const toggleAccordion = () => {
      isOpen.value = !isOpen.value;
    };

    const toggleTerrain = (id) => {
      if (openTerrains.value.includes(id)) {
        openTerrains.value = openTerrains.value.filter((tid) => tid !== id);
      } else {
        openTerrains.value.push(id);
      }
    };

    const editTerrain = (terrain) => {
      editTerrainMode.value = true;
      editedTerrainId.value = terrain.id;
      terrainForm.value = { ...terrain };
      showTerrainPopup.value = true;
    };

    const submitEditedTerrain = async () => {
      try {
        await updateTerrain(editedTerrainId.value, terrainForm.value);
        await fetchTerrains();
        cancelTerrainEdit();
      } catch (err) {
        console.error("Erreur lors de la mise à jour :", err);
        alert("Erreur lors de la mise à jour du terrain.");
      }
    };

    const submitNewTerrain = async () => {
      try {
        console.log("Terrain envoyé :", terrainForm.value);
        await createTerrain(terrainForm.value);
        await fetchTerrains();
        cancelTerrainEdit();
      } catch (err) {
        console.error("Erreur lors de la création du terrain :", err);
        alert("Erreur lors de la création du terrain.");
      }
    };

    const cancelTerrainEdit = () => {
      showTerrainPopup.value = false;
      editTerrainMode.value = false;
      editedTerrainId.value = null;
      terrainForm.value = {
        name: "",
        times: [],
      };
    };

    const deleteTerrain = async (id) => {
      try {
        const confirmed = confirm("Voulez-vous vraiment supprimer ce terrain ?");
        if (!confirmed) return;

        await deleteTerrainService(id);
        await fetchTerrains();
      } catch (err) {
        console.error("Erreur lors de la suppression du terrain :", err);
        alert("Erreur lors de la suppression du terrain.");
      }
    };

    const deleteSchedule = (id) => {
      if (userRole.value !== "ROLE_ADMIN") return;
      console.log("Suppression horaire ID :", id);
    };

    const addTime = () => {
      terrainForm.value.times.push({
        dayWeek: 1,
        start: "08:00",
        stop: "18:00",
      });
    };

    const removeTime = (index) => {
      terrainForm.value.times.splice(index, 1);
    };

    const convertDay = (num) => daysOfWeek[num - 1] || "Jour inconnu";

    const sortedTerrains = computed(() =>
        terrains.value.slice().sort((a, b) => a.name.localeCompare(b.name))
    );

    const openCreateTerrain = () => {
      editTerrainMode.value = false;
      terrainForm.value = {
        name: "",
        times: [],
      };
      showTerrainPopup.value = true;
    };

    onMounted(() => {
      fetchTerrains();
      updateIsMobile();
      window.addEventListener("resize", updateIsMobile);
    });

    onUnmounted(() => {
      window.removeEventListener("resize", updateIsMobile);
    });

    return {
      localIsMobile,
      isOpen,
      openTerrains,
      showTerrainPopup,
      editTerrainMode,
      editedTerrainId,
      terrainForm,
      sortedTerrains,
      daysOfWeek,

      createTerrain,
      toggleAccordion,
      toggleTerrain,
      editTerrain,
      submitEditedTerrain,
      submitNewTerrain,
      cancelTerrainEdit,
      deleteTerrain,
      deleteSchedule,
      convertDay,
      addTime,
      removeTime,
      openCreateTerrain,
    };
  },

};
</script>



<style scoped>
.material-symbols-outlined {
  cursor: pointer;
  transition: transform 0.3s ease, color 0.2s ease;
}

.rotate-180 {
  transform: rotate(270deg);
}

.small-icon {
  font-size: 16px;
}

.border-b {
  border-bottom: 1px solid #e0e0e0;
}

.ml-6 {
  margin-left: 1.5rem;
}

.text-center {
  text-align: center;
}

.w-full {
  width: 100%;
}

.terrain-hover, .terrain-sub-hover {
  transition: color 0.2s ease-in-out;
}

.terrain-title, .terrain-sub-title {
  transition: color 0.2s ease-in-out;
}

.terrain-arrow, .terrain-sub-arrow {
  transition: color 0.2s ease-in-out;
}

.terrain-hover:hover .terrain-title,
.terrain-hover:hover .terrain-arrow {
  color: #2F855A;
}

.terrain-sub-hover:hover .terrain-sub-title,
.terrain-sub-hover:hover .terrain-sub-arrow {
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
