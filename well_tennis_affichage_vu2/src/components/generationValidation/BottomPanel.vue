<template>
  <SolverStateEditor
      :solver-state="solverState"
      @solver-state-changed="handleSolverStateChange"
  />
  <span class="divider-line"></span>
  <label class="button secondary" title="Importer une génération">
    <span class="material-symbols-outlined">upload</span>
    <input type="file" accept=".rmpk" @change="uploadRmpk" class="hidden"/>
    Importer
  </label>
</template>

<script>
import useSolver from "../../useJs/useSolver.js";
import ValidationPopup from "./ValidationPopup.vue";
import {useSessionsStore} from "../../store/useSessionsStore.js";
import SolverStateEditor from "./SolverStateEditor.vue";

export default {
  name: "BottomPanel",
  components: {SolverStateEditor, ValidationPopup},
  data() {
    return {
      solverState: "STOPPED",
      sessionStore: useSessionsStore(),
      showValidationPopup: false,
    }
  },
  methods: {
    async updateSolverState() {
      this.solverState = await useSolver().statusSolver();
      console.log("Solver state updated:", this.solverState);
    },
    handleSolverStateChange(newState) {
      this.solverState = newState;
      console.log("Solver state changed to:", newState);
    },
    async uploadRmpk(event) {
      const file = event.target.files[0];
      if (!file) return;

      try {
        const reader = new FileReader();
        reader.onload = async (e) => {
          const content = e.target.result;
          await useSolver().rmpkPost(content);
          await this.updateSolverState();
        };
        reader.readAsText(file);
        event.target.value = null;
      } catch (error) {
        console.error('Erreur lors de l\'importation du fichier RMPK:', error);
      }
    },
  },
  mounted() {
    this.updateSolverState();
  },
};
</script>

<style>
:root {
  --accent: #528359;
}

.button {
  height: 100%;
  background-color: var(--accent);
  color: white;
  border-radius: 6px;
  display: flex;
  align-items: center;
  transition: all 0.2s ease;
  border: 1px solid var(--accent);
  cursor: pointer;

  &:hover {
    background-color: #345e3b;
  }

  > span {
    height: 100%;
    font-size: 1.3rem;
    aspect-ratio: 1;
    display: flex;
    align-items: center;
  }

  &.no-text > span {
    justify-content: center;
  }

  &:not(.no-text) {
    padding: .2rem 1.5rem;
  }

  &.secondary {
    background-color: white;
    color: var(--accent);

    &:hover {
      background-color: #e6f4eb;
    }
  }

  &.red {
    color: #E53E3E;

    &.secondary {
      border: 1px solid #E53E3E;
    }

    &:hover {
      background-color: #fff5f5;
    }
  }

  &.black {
    color: black;
    border: 1px solid black;


    &:hover {
      background-color: #f7fafc;
    }
  }

  > input {
    display: none;
  }
}

.input {
  width: 100%;
  cursor: text;
  transition-duration: .1s;

  > span {
    transition-duration: .1s;
    font-weight: bold;
  }

  > input {
    box-shadow: 0 1px 0 var(--accent);
    transition-duration: .1s;

    &:focus {
      outline: none;
      box-shadow: 0 3px 0 var(--accent);
    }
  }

  &.top-label {
    display: flex;
    flex-direction: column;
  }
}

.checkbox {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
  transition: all 0.2s ease;

  &:hover {
    color: #345e3b;
  }

  .material-symbols-outlined {
    font-size: 1.5rem;
  }

  .hidden {
    display: none;
  }

  > span:not(.material-symbols-outlined) {
    font-size: 1rem;
  }
}
</style>
