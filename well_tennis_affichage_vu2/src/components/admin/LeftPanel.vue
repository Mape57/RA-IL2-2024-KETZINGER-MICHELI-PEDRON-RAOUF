<template>
  <div class="bg-white rounded-lg shadow-md w-[30%] h-full p-[20px_30px] flex flex-col">
    <!-- Onglets -->
    <div class="flex items-center mb-4 relative">
      <div class="flex space-x-6 flex-grow">
        <button
            class="text-[#528359] font-bold transition relative focus:outline-none w-full text-center flex items-center justify-center space-x-2"
        >
          <span class="material-symbols-outlined">database</span>
          <span>Données</span>
        </button>
        <button
            class="text-gray-500 font-bold transition relative focus:outline-none w-full text-center flex items-center justify-center space-x-2"
        >
          <span class="material-symbols-outlined">gavel</span>
          <span>Contraintes</span>
        </button>
      </div>
      <button class="flex items-center text-[#528359] hover:text-[#456c4c] focus:outline-none ml-4">
        <span class="material-symbols-outlined">download</span>
      </button>
    </div>

    <!-- Contenu -->
    <div class="flex-1 overflow-auto">
      <!-- Barre de recherche -->
      <div class="flex items-center border border-gray-300 rounded-lg px-4 py-2 focus-within:ring-2 focus-within:ring-[#528359] mb-6">
        <span class="material-symbols-outlined text-gray-500 text-lg mr-2">search</span>
        <input
            type="text"
            placeholder="Filtrer les données"
            class="w-full text-sm focus:outline-none"
        />
      </div>

      <!-- Section des entraîneurs -->
      <div class="mb-6">
        <div class="flex justify-between items-center cursor-pointer">
          <div class="flex items-center">
            <span class="material-symbols-outlined text-[#528359] mr-2">expand_more</span>
            <h3 class="text-[#528359] font-bold text-lg">Entraîneurs</h3>
          </div>
        </div>
        <hr class="border-t border-gray-300 mb-2">
        <div class="space-y-2">
          <!-- Entraîneurs -->
          <div class="grid grid-cols-3 items-center">
            <span>Athena Garrett</span>
            <span class="text-sm text-gray-500 text-right">0-17</span>
            <span class="text-sm text-gray-500 text-right">17</span>
          </div>
          <div class="grid grid-cols-3 items-center">
            <span>Billie Sharpe</span>
            <span class="text-sm text-gray-500 text-right">12-16</span>
            <span class="text-sm text-gray-500 text-right">18</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LeftPanel",
};
</script>
