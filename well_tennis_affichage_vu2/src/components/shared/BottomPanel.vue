<template>
  <div class="bottom-panel fixed bottom-3 right-10 bg-white rounded-lg shadow-md w-[67%] px-5 left-[32%] z-10">
    <div class="p-4 flex justify-between items-center">
      <!-- Boutons -->
      <div class="flex space-x-4 items-center">
        <!-- Bouton "Lancer" -->
        <button
            @click="launch"
            class="bg-[#528359] text-white py-1.5 px-3 rounded-md flex items-center text-sm hover:bg-[#456c4c] transition"
        >
          <span class="material-symbols-outlined mr-1 text-base">play_arrow</span>
          Lancer
        </button>

        <!-- Bouton "Envoyer les modifications" -->
        <button
            @click="submitChanges"
            class="bg-white text-[#528359] py-1.5 px-3 rounded-md flex items-center border border-[#528359] text-sm hover:bg-[#e6f4eb] transition"
        >
          <span class="material-symbols-outlined mr-1 text-base">event_repeat</span>
          Envoyer les modifications
        </button>

        <!-- Statut ou alerte -->
        <div class="flex items-center text-[#d97706] pl-12">
          <span class="material-symbols-outlined mr-1 text-sm">warning</span>
          <span class="text-xs">{{ statusMessage }}</span>
        </div>
      </div>

      <!-- Bouton "Envoyer l'emploi du temps" -->
      <button
          @click="sendSchedule"
          class="bg-white text-[#528359] py-1.5 px-3 rounded-md flex items-center border border-[#528359] text-sm hover:bg-[#e6f4eb] transition"
      >
        <span class="material-symbols-outlined mr-1 text-base">send</span>
        Envoyer l'emploi du temps
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "BottomPanel",
  props: {
    statusMessage: {
      type: String,
      default: "Placement réalisé à 98%",
    },
  },
  methods: {
    launch() {
      this.$emit("launch");
    },
    submitChanges() {
      this.$emit("submitChanges");
    },
    sendSchedule() {
      this.$emit("sendSchedule");
    },
  },
};
</script>

<style scoped>
.bottom-panel:hover {
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
</style>
