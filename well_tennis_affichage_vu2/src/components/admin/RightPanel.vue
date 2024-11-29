<template>
  <div class="flex flex-col justify-between w-[70%] h-full space-y-4">
    <div class="bg-white rounded-lg shadow-md h-[95%] p-6 overflow-auto">
      <div class="flex items-center justify-between mb-6 relative">
        <div class="absolute left-1/2 transform -translate-x-1/2 flex space-x-6">
          <!-- Onglets -->
          <button class="text-[#528359] font-bold transition relative focus:outline-none">
            Terrain 1
          </button>
          <button class="text-gray-500 font-bold transition relative focus:outline-none">
            Terrain 2
          </button>
          <button class="text-gray-500 font-bold transition relative focus:outline-none">
            Terrain 3
          </button>
        </div>
      </div>

      <!-- Contenu des terrains -->
      <div>
        <h2 class="text-[#528359] text-xl font-bold mb-4">Terrain 1</h2>
        <div class="bg-gray-50 border border-gray-300 rounded-lg p-4 mb-4">
          <p class="font-semibold text-[#528359]">Séance 1</p>
          <p class="text-sm text-gray-600">Détails de la séance 1</p>
        </div>
        <div class="bg-gray-50 border border-gray-300 rounded-lg p-4 mb-4">
          <p class="font-semibold text-[#528359]">Séance 2</p>
          <p class="text-sm text-gray-600">Détails de la séance 2</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RightPanel",
};
</script>
