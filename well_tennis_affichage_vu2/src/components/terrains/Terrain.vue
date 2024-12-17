<template>
  <Accordion title="Terrains" :defaultOpen="true">
    <div v-for="terrain in terrains" :key="terrain.id" class="mb-4">
      <!-- Titre du terrain avec flèche -->
      <div class="flex justify-between items-center cursor-pointer" @click="toggleTerrain(terrain.id)">
        <div class="flex items-center">
          <span
              :class="{ 'rotate-90': openTerrains.includes(terrain.id) }"
              class="material-symbols-outlined transition-transform duration-300 mr-2"
          >
            chevron_right
          </span>
          <h4 class="font-semibold">{{ terrain.name }}</h4>
        </div>
        <!-- Icônes d'actions -->
        <div class="flex space-x-2">
          <span class="material-symbols-outlined cursor-pointer">delete</span>
          <span class="material-symbols-outlined cursor-pointer">playlist_add</span>
        </div>
      </div>

      <!-- Contenu du terrain -->
      <div v-if="openTerrains.includes(terrain.id)" class="mt-2">
        <table class="w-full text-sm text-gray-600">
          <thead>
          <tr>
            <th class="text-left">Jour</th>
            <th class="text-center">Ouverture</th>
            <th class="text-center">Fermeture</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="schedule in terrain.schedule" :key="schedule.day">
            <td>{{ schedule.day }}</td>
            <td class="text-center">{{ schedule.open }}</td>
            <td class="text-center">{{ schedule.close }}</td>
            <td class="text-right">
              <span class="material-symbols-outlined cursor-pointer">delete</span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </Accordion>
</template>

<script>
import Accordion from "../shared/Accordion.vue";

export default {
  name: "Terrains",
  components: { Accordion },
  props: {
    terrains: Array, // Prop qui contient les données des terrains
  },
  data() {
    return {
      openTerrains: [], // Liste des terrains ouverts
    };
  },
  methods: {
    toggleTerrain(id) {
      // Ouvre ou ferme le terrain selon son ID
      if (this.openTerrains.includes(id)) {
        this.openTerrains = this.openTerrains.filter(terrainId => terrainId !== id);
      } else {
        this.openTerrains.push(id);
      }
    },
  },
};
</script>

<style scoped>
.material-symbols-outlined {
  transition: transform 0.3s ease;
}
.rotate-90 {
  transform: rotate(90deg);
}
</style>
