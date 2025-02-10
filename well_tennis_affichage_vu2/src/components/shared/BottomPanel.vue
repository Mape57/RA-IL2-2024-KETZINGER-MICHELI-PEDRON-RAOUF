<template>
  <div class="bottom-panel fixed bottom-3 right-10 bg-white rounded-lg shadow-md w-[67%] px-5 left-[32%] z-10">
    <div class="p-4 flex items-center justify-between w-full h-full tablet:flex-col-reverse tablet:gap-2">

      <!-- Groupe de Boutons Gauche -->
      <div class="flex space-x-4 items-center tablet:flex-col tablet:space-x-0 tablet:space-y-2">

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
      </div>

      <!-- Statut ou alerte (centré) -->
      <div class="flex items-center text-[#d97706] flex-1 justify-center tablet:order-2">
        <span class="material-symbols-outlined mr-1 text-sm">warning</span>
        <span class="text-xs">{{ statusMessage }}</span>
      </div>

      <!-- Bouton "Envoyer l'emploi du temps" (aligné à droite) -->
      <div class="flex lg:ml-auto">
        <button
            @click="sendSchedule"
            class="bg-white text-[#528359] py-1.5 px-3 rounded-md flex items-center border border-[#528359] text-sm hover:bg-[#e6f4eb] transition"
        >
          <span class="material-symbols-outlined mr-1 text-base">send</span>
          Envoyer l'emploi du temps
        </button>
      </div>

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
@media (min-width: 1024px) {
  .bottom-panel {
    width: 67%;
    left: 56.1vh;
    bottom: 1.3vh;
    height: 5vh;
  }
}

@media (min-width: 768px) and (max-width: 1024px) {
  .bottom-panel {
    width: 54%;
    left: 44.1%;
    bottom: 1.4%;
    height: auto;
    padding: 1rem;
  }

  .bottom-panel .flex {
    flex-wrap: wrap;
  }

  .tablet\:gap-2 {
    gap: 0.5rem;
  }

  .tablet\:order-2 {
    order: 2;
  }

  .bottom-panel button {
    font-size: 0.8rem;
    padding: 0.5rem 1rem;
  }

  .bottom-panel .material-symbols-outlined {
    font-size: 1rem;
  }

  @media (min-width: 810px) and (max-width: 900px) {
    .bottom-panel {
      width: 54%;
      left: 44.1%;
      bottom: 1.4%;
      height: auto;
      padding: 1rem;
    }

    .bottom-panel button {
      font-size: 0.8rem;
      padding: 0.5rem 1rem;
    }

    .bottom-panel .material-symbols-outlined {
      font-size: 1rem;
    }
  }
}
</style>
