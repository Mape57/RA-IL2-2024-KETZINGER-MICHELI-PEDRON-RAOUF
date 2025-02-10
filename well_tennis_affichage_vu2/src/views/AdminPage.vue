<template>
  <div class="admin-page min-h-screen flex flex-col w-full">
    <!-- Autres éléments -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- Bouton Menu en mode mobile -->
    <button v-if="isMobile && !showLeftPanel" @click="toggleMenu" class="menu-button">
      <span class="material-symbols-outlined">menu</span>
    </button>

    <!-- Menu contextuel -->
    <div v-if="showMenu" class="menu-contextual">
      <button @click="handleLogout">
        <span class="material-icons">exit_to_app</span> Déconnexion
      </button>
      <button @click="toggleLeftPanel">
        <span class="material-icons">storage</span> Donnée
      </button>
    </div>

    <!-- Mode Mobile : Affichage uniquement du RightPanel ou LeftPanel -->
    <div v-if="isMobile" class="mobile-container">
      <transition name="slide-fade">
        <LeftPanel
            v-if="showLeftPanel"
            class="mobile-left-panel"
            :isMobile="true"
            @close="toggleLeftPanel"
        />
      </transition>
      <RightPanel class="mobile-right-panel" />
    </div>

    <!-- Mode Desktop : Affichage normal avec LeftPanel + RightPanel -->
    <div v-else class="flex w-full h-full">
      <LeftPanel class="desktop-left-panel" :isMobile="false" />
      <RightPanel class="desktop-right-panel" />
    </div>

    <!-- Panneau inférieur (Visible uniquement sur Desktop) -->
    <BottomPanel
        v-if="!isMobile" :statusMessage="statusMessage"
        @launch="handleLaunch"
        @submitChanges="handleSubmitChanges"
        @sendSchedule="handleSendSchedule" />
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
      showLeftPanel: false,
      showMenu: false,
    };
  },
  methods: {
    checkScreenSize() {
      clearTimeout(this.resizeTimeout);
      this.resizeTimeout = setTimeout(() => {
        this.isMobile = window.innerWidth < 768;
      }, 200);
    },
    toggleLeftPanel() {
      this.showLeftPanel = !this.showLeftPanel;
      this.showMenu = false;  // Fermez le menu contextuel
    },
    toggleMenu() {
      this.showMenu = !this.showMenu;
    },
    handleLogout() {
      // Ajoutez ici la logique de déconnexion
      console.log("Déconnexion");
      this.showMenu = false;  // Fermez le menu contextuel
    },
  },
  mounted() {
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
};
</script>


<style scoped>
.admin-page {
  background: #f5f5f5;
}
.menu-button:hover .material-symbols-outlined {
  color: #3a6242;
}

.menu-button .material-symbols-outlined {
  font-size: 32px;
  color: #528359;
  transition: color 0.3s ease-in-out;
}

.mobile-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.mobile-right-panel {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 1rem;
  overflow-x: hidden;
  box-sizing: border-box;
}

.mobile-left-panel {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  background: white;
  z-index: 50;
  overflow-y: auto;
}

.menu-button {
  position: fixed;
  top: 15px;
  left: 15px;
  z-index: 60;
  padding: 0.5rem;
  background: none;
  border: none;
  cursor: pointer;
}

.menu-contextual {
  position: fixed;
  top: 50px;
  left: 15px;
  background: white;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 70;
  border-radius: 8px;
  overflow: hidden;
}

.menu-contextual button {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0.75rem 1rem;
  background: none;
  border: none;
  text-align: left;
  cursor: pointer;
  font-size: 16px;
  color: #333;
  transition: background 0.3s, color 0.3s;
}

.menu-contextual button:hover {
  background: #f0f0f0;
  color: #007BFF;
}

.menu-contextual button + button {
  border-top: 1px solid #ddd;
}

.menu-contextual .material-icons {
  margin-right: 8px;
  font-size: 20px;
  color: #333;
}
</style>





