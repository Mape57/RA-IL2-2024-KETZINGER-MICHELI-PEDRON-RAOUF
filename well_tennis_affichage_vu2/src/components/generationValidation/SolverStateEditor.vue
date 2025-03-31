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
    <ValidationPopup/>
  </template>
  <label v-else-if="solverState === 'RUNNING'" class="button" title="Forcer l'arrêt du solveur">
    <span class="material-symbols-outlined">motion_sensor_idle</span>
    <input type="button" class="hidden" @click="stopSolver">
    Forcer l'arrêt
  </label>
</template>
<script>
import useSolver from "../../useJs/useSolver.js";
import ValidationPopup from "./ValidationPopup.vue";

export default {
  components: {ValidationPopup},
  props: ['solverState'],
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
      }
    },
    async stopSolver() {
      try {
        await useSolver().stopSolver();
        this.$emit('solver-state-changed', 'OVER');
      } catch (error) {
        console.error('Erreur lors de l\'arrêt du solveur:', error);
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
      }
    },
  }
}
</script>