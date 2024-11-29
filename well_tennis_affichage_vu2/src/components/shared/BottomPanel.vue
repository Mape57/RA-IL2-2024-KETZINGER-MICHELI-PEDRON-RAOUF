<template>
  <div class=" w-[100%] h-[8vh] bg-white rounded-lg shadow-md p-4 flex justify-between items-center">

    <!-- Groupe de boutons à gauche -->
    <div class="flex space-x-4 items-center">
      <!-- Bouton "Lancer" -->
      <button
          @click="launch"
          class="bg-[#528359] text-white py-2 px-4 rounded-lg flex items-center hover:bg-[#456c4c] transition"
      >
        <span class="material-symbols-outlined mr-2">play_arrow</span>
        Lancer
      </button>

      <!-- Bouton "Envoyer les modifications" -->
      <button
          @click="submitChanges"
          class="bg-white text-[#528359] py-2 px-4 rounded-lg flex items-center border border-[#528359] hover:bg-[#e6f4eb] transition"
      >
        <span class="material-symbols-outlined mr-2">event_repeat</span>
        Envoyer les modifications
      </button>

      <!-- Statut ou alerte -->
      <div class="flex items-center text-[#d97706] pl-12">
        <span class="material-symbols-outlined mr-2">warning</span>
        <span class="text-sm">{{ statusMessage }}</span>
      </div>
    </div>

    <!-- Bouton "Envoyer l'emploi du temps" -->
    <button
        @click="sendSchedule"
        class="bg-white text-[#528359] py-2 px-4 rounded-lg flex items-center border border-[#528359] hover:bg-[#e6f4eb] transition"
    >
      <span class="material-symbols-outlined mr-2">send</span>
      Envoyer l'emploi du temps
    </button>
  </div>
</template>

<script>
export default {
  name: "BottomPanel",
  props: {
    statusMessage: {
      type: String,
      default: "Placement réalisé à 98%", // Message d'état ou de statut
    },
  },
  methods: {
    launch() {
      this.$emit("launch"); // Émet un événement vers le parent pour lancer une action
    },
    submitChanges() {
      this.$emit("submitChanges"); // Émet un événement pour soumettre des modifications
    },
    sendSchedule() {
      this.$emit("sendSchedule"); // Émet un événement pour envoyer l'emploi du temps
    },
  },
};
</script>

<style scoped>
.bottom-panel:hover {
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
</style>
