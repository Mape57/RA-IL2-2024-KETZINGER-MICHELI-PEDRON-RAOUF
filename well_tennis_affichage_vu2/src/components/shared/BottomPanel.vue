<template>
  <div class="bottom-panel fixed bottom-[1vh] bg-white rounded-lg shadow-md w-[67%] left-[auto] right-[1.37rem] z-10">
    <div class="p-4 flex items-center justify-between w-full h-full tablet:flex-col-reverse tablet:gap-2">

      <!-- Groupe de Boutons Gauche -->
      <div class="flex space-x-4 items-center tablet:flex-col tablet:space-x-0 tablet:space-y-2">

        <!-- Bouton "Lancer" -->
        <button
            @click="launch"
            class="bg-[#528359] text-white py-1.5 px-3 rounded-md flex items-center text-sm hover:bg-[#456c4c] transition"
        >
          <span v-if="!running" class="material-symbols-outlined mr-1 text-base">play_arrow</span>
          <span v-else class="material-symbols-outlined mr-1 text-base">pause</span>
          Lancer
        </button>

        <!-- Bouton "Envoyer les modifications" -->
        <button
            @click="submitChanges"
            class="bg-white text-[#528359] py-1.5 px-3 rounded-md flex items-center border border-[#528359] text-sm hover:bg-[#e6f4eb] transition"
        >
          <span class="material-symbols-outlined mr-1 text-base">event_repeat</span>
          Recharger les modifications
        </button>
      <!-- Statut ou alerte (centré) -->
      <div v-if="statusMessage" class="flex items-center text-[#d97706] flex-1 justify-center tablet:order-2">
        <span class="material-symbols-outlined mr-1 text-lg">warning</span>
        <span class="text-sm">{{ statusMessage }}</span>
      </div>
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
import useSolver from "../../useJs/useSolver.js";
import RightPanel from "../terrains/RightPanel.vue";

export default {
  name: "BottomPanel",
  data() {
    return {
      running: false,
      statusMessage: "",
    }
  },
  methods: {
    launch() {
      const { startSolver, stopSolver } = useSolver();
      if (this.running) {
        stopSolver();
      } else {
        startSolver();
      }
      this.running = !this.running;
    },
    submitChanges() {
      const { saveSolver, statusSolver } = useSolver();
      saveSolver().then(() => {
        this.$emit("refreshContent");
        statusSolver().then((status) => {
          this.statusMessage = status;
        });
      });
    },
    sendSchedule() {
      this.$emit("sendSchedule");
    },
  },
};
</script>

<style scoped>

.bottom-panel {
  z-index: 10;
  right: 1.37rem; /* Alignement avec le RightPanel */
  width: 67%; /* Même largeur que le RightPanel */
  height: 7vh; /* Hauteur ajustée */
  background-color: white;
  border-radius: 0.5rem;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 1.25rem;
}

@media (min-width: 1024px) {
  .bottom-panel {
    width: 67%;
    right: 1.37rem;
    left: auto;
    height: 6vh;
  }
}

@media (min-width: 768px) and (max-width: 1024px) {
  .bottom-panel {
    width: 54%;
    left: auto;
    right: 2%;
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
    font-size: 0.85rem;
    padding: 0.5rem 1rem;
  }

  .bottom-panel .material-symbols-outlined {
    font-size: 1rem;
  }
}

@media (max-width: 768px) {
  .bottom-panel {
    width: 100%;
    height: 9vh;
    left: 0;
    right: 0;
    padding: 0.75rem;
    border-radius: 0;
  }
}
</style>
