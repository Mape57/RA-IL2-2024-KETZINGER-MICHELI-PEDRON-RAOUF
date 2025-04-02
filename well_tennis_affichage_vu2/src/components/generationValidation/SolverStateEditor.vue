<template>
  <label v-if="solverState === 'STOPPED'" class="button" title="Lancer le solveur">
    <span class="material-symbols-outlined">motion_sensor_active</span>
    <input type="button" class="hidden" @click="launchSolver">
    Lancer
  </label>
  <template v-else-if="solverState === 'OVER'">
    <label class="button" title="Télécharger la génération">
      <span class="material-symbols-outlined">download</span>
      <input type="button" class="hidden" @click="downloadRmpk">
      Télécharger
    </label>
    <ValidationPopup
        @state-changed="handleSolverStateChange"
    />
  </template>
  <label v-else-if="solverState === 'RUNNING'" class="button" title="Forcer l'arrêt du solveur">
    <span class="material-symbols-outlined">motion_sensor_idle</span>
    <input type="button" class="hidden" @click="stopSolver">
    Forcer l'arrêt
  </label>

  <!-- Error Popup -->
  <Teleport to="body">
    <Transition name="fade">
      <div v-if="showErrorPopup" class="error-popup">
        <div class="error-popup-content">
          <div class="error-popup-header">
            <span class="material-symbols-outlined error-icon">error</span>
            <h3>Erreur</h3>
            <button @click="showErrorPopup = false" class="button secondary red no-text">
              <span class="material-symbols-outlined">close</span>
            </button>
          </div>
          <div class="error-popup-body">
            <p>{{ errorMessage[1] }}</p>
          </div>
          <div class="error-popup-footer">
            <button @click="showErrorPopup = false" class="button secondary">Fermer</button>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script>
import useSolver from "../../useJs/useSolver.js";
import ValidationPopup from "./ValidationPopup.vue";

export default {
  components: {ValidationPopup},
  props: ['solverState'],
  data() {
    return {
      showErrorPopup: false,
      errorMessage: ''
    }
  },
  mounted() {
    console.log("SolverStateEditor mounted with state:", this.solverState);
  },
  methods: {
    async launchSolver() {
      try {
        await useSolver().startSolver();
        this.$emit('solver-state-changed', 'RUNNING');
      } catch (error) {
        console.error('Erreur lors du lancement du solveur:', error);
        this.errorMessage = [error.response?.data?.message, error.response?.data?.description];
        this.showErrorPopup = true;
      }
    },
    handleSolverStateChange(newState) {
      this.$emit('solver-state-changed', newState);
    },
    async stopSolver() {
      try {
        await useSolver().stopSolver();
        this.$emit('solver-state-changed', 'OVER');
      } catch (error) {
        console.error('Erreur lors de l\'arrêt du solveur:', error);
        this.errorMessage = [error.response?.data?.message, error.response?.data?.description];
        this.showErrorPopup = true;
      }
    },
    async downloadRmpk() {
      try {
        const data = await useSolver().rmpkGet();
        const blob = new Blob([data], {type: 'application/octet-stream'});
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'generation.rmpk';
        document.body.appendChild(a);
        a.click();
        URL.revokeObjectURL(url);
        document.body.removeChild(a);
      } catch (error) {
        console.error('Erreur lors du téléchargement du fichier RMPK:', error);
        this.errorMessage = [error.response?.data?.message, error.response?.data?.description];
        this.showErrorPopup = true;
      }
    },
  }
}
</script>

<style scoped>
.error-popup {
  position: fixed;
  z-index: 2000;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.error-popup-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  width: 90%;
  max-width: 400px;
  overflow: hidden;
}

.error-popup-header {
  display: flex;
  align-items: center;
  padding: 10px;
  background-color: #fff5f5;
  border-bottom: 1px solid #fed7d7;

  > button {
    width: 40px;
    aspect-ratio: 1;
  }
}

.error-icon {
  color: #e53e3e;
  font-size: 1.5rem;
  margin-right: 0.5rem;
}

.error-popup-header h3 {
  color: #e53e3e;
  margin: 0;
  flex: 1;
  font-size: 1.2rem;
}

.error-popup-body {
  padding: 1rem;
  color: #4a5568;
}

.error-popup-footer {
  padding: 1rem;
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid #e2e8f0;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>