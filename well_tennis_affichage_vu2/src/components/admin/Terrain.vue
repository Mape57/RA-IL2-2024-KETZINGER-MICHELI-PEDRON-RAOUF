<template>
  <div>
    <!-- Conteneur principal -->
    <div
        class="flex justify-between items-center cursor-pointer py-2 border-b"
        @click="toggleAccordion"
    >
      <div class="flex items-center terrain-hover">
        <span :class="{ 'rotate-180': isOpen }" class="material-symbols-outlined terrain-arrow transition-transform duration-300 mr-2">
          expand_more
        </span>
              <h3 class="font-bold text-lg terrain-title">Terrains</h3>
      </div>
    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <div v-for="terrain in sortedTerrains" :key="terrain.id" class="mb-2">
      <!-- Terrain principal -->
        <div class="flex justify-between items-center cursor-pointer py-1 terrain-sub-hover"
             @click="toggleTerrain(terrain.id)">
          <div class="flex items-center">
            <span :class="{ 'rotate-180': openTerrains.includes(terrain.id) }"
                  class="material-symbols-outlined terrain-sub-arrow transition-transform duration-300 mr-2 small-icon">
                   expand_more
            </span>
            <h4 class="font-semibold terrain-sub-title">{{ terrain.name }}</h4>
          </div>
          <div class="flex space-x-2" v-if="!localIsMobile">
            <span
                class="material-symbols-outlined small-icon cursor-pointer"
                @click="deleteTerrain(terrain.id)"
            >delete</span
            >
            <span class="material-symbols-outlined small-icon cursor-pointer"
            >playlist_add</span
            >
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
              <th class="text-center" v-if="!localIsMobile">Actions</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="time in terrain.times" :key="time.id">
              <td>{{ convertDay(time.dayWeek)}}</td>
              <td class="text-center">{{ time.start }}</td>
              <td class="text-center">{{ time.stop }}</td>
              <td class="text-center" v-if="!localIsMobile">
                  <span
                      class="material-symbols-outlined small-icon cursor-pointer"
                      @click="deleteSchedule(time.id)"
                  >delete
                  </span>
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
import { ref, onMounted, onUnmounted } from "vue";

export default {
  name: "Terrains",
  props: {
    terrains: {
      type: Array,
      required: true,
    },
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
      openTerrains: [],
      daysOfWeek: ["", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"],
    };
  },
  computed: {
    sortedTerrains() {
      return this.terrains.slice().sort((a, b) => {
        const nameA = a.name.toUpperCase();
        const nameB = b.name.toUpperCase();
        return nameA.localeCompare(nameB, undefined, { numeric: true });
      });
    },
  },
  methods: {
    toggleAccordion() {
      this.isOpen = !this.isOpen;
    },
    toggleTerrain(id) {
      if (this.openTerrains.includes(id)) {
        this.openTerrains = this.openTerrains.filter((terrainId) => terrainId !== id);
      } else {
        this.openTerrains.push(id);
      }
    },
    deleteTerrain(terrainId) {
      console.log(`Supprimer le terrain avec l'id: ${terrainId}`);
    },
    deleteSchedule(timeId) {
      console.log(`Supprimer l'horaire avec l'id: ${timeId}`);
    },
    convertDay(dayNumber) {
      return this.daysOfWeek[dayNumber] || "Jour inconnu";
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

</style>
