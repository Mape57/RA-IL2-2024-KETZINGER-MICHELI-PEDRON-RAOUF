<template>
  <!-- Conteneur principal pour Terrains -->
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
        <h3 class="font-bold text-lg">Terrains</h3>
      </div>

    </div>

    <!-- Contenu déroulant -->
    <div v-if="isOpen" class="mt-2">
      <div v-for="terrain in terrains" :key="terrain.id" class="mb-2">
        <!-- Ligne pour chaque terrain -->
        <div class="flex justify-between items-center cursor-pointer py-1" @click="toggleTerrain(terrain.id)">
          <div class="flex items-center">
            <span
                :class="{ 'rotate-180': openTerrains.includes(terrain.id) }"
                class="material-symbols-outlined transition-transform duration-300 mr-2 small-icon"
            >
              expand_more
            </span>
            <h4 class="font-semibold text-black-800">{{ terrain.name }}</h4>
          </div>
          <!-- Icônes spécifiques pour chaque terrain -->
          <div class="flex space-x-2">
            <span class="material-symbols-outlined small-icon cursor-pointer" @click="deleteTerrain(terrain.id)">delete</span>
            <span class="material-symbols-outlined small-icon cursor-pointer">playlist_add</span>
          </div>
        </div>

        <!-- Contenu déroulant de chaque terrain -->
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
            <tr v-for="schedule in terrain.schedule" :key="schedule.day">
              <td>{{ schedule.day }}</td>
              <td class="text-center">{{ schedule.open }}</td>
              <td class="text-center">{{ schedule.close }}</td>
              <td class="text-center">
                <span class="material-symbols-outlined small-icon cursor-pointer" @click="deleteSchedule(schedule.day)">delete</span>
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
  name: "Terrains",
  props: {
    terrains: Array,
  },
  data() {
    return {
      isOpen: true,
      openTerrains: [],
    };
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
    deleteSchedule(day) {
      console.log(`Supprimer l'horaire pour le jour: ${day}`);
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
</style>
