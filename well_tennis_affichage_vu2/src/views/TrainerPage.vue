<template>
  <div class="admin-page min-h-screen flex flex-col w-full">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- Bouton du menu hamburger -->
    <button v-if="isMobile" @click="toggleMenu" class="menu-button">
      <span class="material-symbols-outlined">menu</span>
    </button>

    <div v-if="isMobile" class="mobile-container">
      <transition name="slide-fade">
        <LeftPanel v-if="showLeftPanel" class="mobile-left-panel" :isMobile="true" @close="toggleLeftPanel" />
      </transition>
      <RightPanel class="mobile-right-panel" />
    </div>

    <div v-else class="flex w-full h-full flex-1">
      <LeftPanel class="desktop-left-panel" :isMobile="false" :userRole="'TRAINER'" />
      <RightPanel class="desktop-right-panel" />
    </div>
  </div>
</template>

<script>
import LeftPanel from "../components/admin/LeftPanel.vue";
import RightPanel from "../components/terrains/RightPanel.vue";
import BottomPanel from "../components/shared/BottomPanel.vue";
import usePlayers from "../useJs/usePlayers";
import {watch} from "vue";

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
  setup() {
    const { fetchPlayers } = usePlayers();
    return { fetchPlayers };
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
      this.showMenu = false;
    },
    toggleMenu() {
      this.showMenu = !this.showMenu;
    },
    handleLogout() {
      console.log("Déconnexion");
      this.showMenu = false;
    },
  },
  mounted() {
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
    watch(
        () => this.isMobile,
        (isMobile) => {
          if (isMobile) {
            this.showLeftPanel = false;
          }
        }
    );

  },
  beforeUnmount() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
};
</script>

<style scoped>

.admin-page {
  background: #eaf1ee;
}

.desktop-right-panel
{
  margin-top : 1.2rem;
  height: 97vh;
}
/* Bouton menu hamburger */
.menu-button {
  position: fixed;
  top: 15px;
  left: 15px;
  z-index: 200;
  padding: 0.5rem;
  border: none;
  cursor: pointer;
  border-radius: 8px;
}

.menu-button:hover {
  background: #e6e6e6;
}

.menu-button .material-symbols-outlined {
  font-size: 32px;
  color: #528359;
  transition: color 0.3s ease-in-out;
}


.menu-contextual button {
  background: none;
  border: none;
  padding: 10px;
  text-align: left;
  font-size: 16px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
  transition: background 0.3s ease;
}

.menu-contextual button:hover {
  background: #f0f0f0;
}

.mobile-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

/* Panel gauche pour mobile */
.mobile-left-panel {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  background: white;
  z-index: 1500;
  overflow-y: auto;
  transition: transform 0.3s ease-in-out;
}

/* Panel droit */
.mobile-right-panel {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 1rem;
  overflow-x: hidden;
  box-sizing: border-box;
}

</style>

