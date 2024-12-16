<template>
  <div class="admin-page min-h-screen flex flex-col bg-[#f5f0e8] w-full">
    <!-- Conteneur principal -->
    <div class="flex flex-grow">
      <!-- Panneau gauche -->
      <LeftPanel />


      <!-- Panneau droit -->
      <RightPanel />


    </div>

    <!-- Panneau inférieur -->
    <BottomPanel
        :statusMessage="statusMessage"
        @launch="handleLaunch"
        @submitChanges="handleSubmitChanges"
        @sendSchedule="handleSendSchedule"
    />



  </div>
</template>

<script>
import LeftPanel from "../components/admin/LeftPanel.vue";
import RightPanel from "../components/terrains/RightPanel.vue";
import BottomPanel from "../components/shared/BottomPanel.vue";

export default {
  name: "AdminPage",
  components: {
    LeftPanel,
    RightPanel,
    BottomPanel,
  },
  data() {
    return {
      statusMessage: "Placement réalisé à 95%", // Message dynamique
    };
  },
  methods: {
    handleLaunch() {
      console.log("Action principale déclenchée (Lancer)");
      // Logique pour l'action principale
    },
    handleSubmitChanges() {
      console.log("Modifications soumises");
      // Logique pour soumettre les modifications
    },
    handleSendSchedule() {
      console.log("Emploi du temps envoyé");
      // Logique pour envoyer l'emploi du temps
    },
  },
};
</script>

<style scoped>
.admin-page {
  display: flex;
  flex-direction: column;
}
.flex-grow {
  display: flex;
  flex-grow: 1;
}
</style>
