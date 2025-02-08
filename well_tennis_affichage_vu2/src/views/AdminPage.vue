<template>
  <div class="admin-page min-h-screen flex flex-col bg-[#fefdf8] w-full">
    <!-- Mode Mobile : Seul RightPanel est affiché et centré -->
    <div v-if="isMobile" class="centered-mobile">
      <RightPanel class="mobile-style" />
    </div>

    <!-- Mode Desktop : Affichage normal avec LeftPanel + RightPanel -->
    <div v-else class="flex w-full h-[calc(100vh-5rem)]">
      <LeftPanel class="w-[30%] h-[calc(100%-2rem)] overflow-y-auto" /> <!-- Panel gauche -->
      <RightPanel class="w-[10%] h-[calc(100%-2rem)] overflow-y-auto" /> <!-- Panel droit -->
    </div>




    <!-- Panneau inférieur (Visible uniquement sur Desktop) -->
    <BottomPanel
        v-if="!isMobile"
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
      statusMessage: "Placement réalisé à 95%",
      isMobile: false,
    };
  },
  methods: {
    checkScreenSize() {
      clearTimeout(this.resizeTimeout); // Debounce resize events
      this.resizeTimeout = setTimeout(() => {
        this.isMobile = window.innerWidth < 768;
      }, 200);
    },
  },
  mounted() {
    // Initialisation de l'état mobile et écoute du redimensionnement
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
};
</script>


<style scoped>
.admin-page {
  display: flex;
  flex-direction: column;
  width: 100vw; /* Prend toute la largeur */
  height: 100vh; /* Prend toute la hauteur */
  overflow: hidden; /* Pas de défilement */
}

.centered-mobile {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background-color: #fefdf8;
}


.mobile-style {
  position: relative;
  width: 100%;
  height: 100%;
  padding: 1rem;
  box-sizing: border-box;
  background-color: white;
  overflow-y: auto; /* Allow scrolling for long content */
  border-radius: 0;
}
</style>

