<template>
  <div class="admin-page">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- Bouton du menu hamburger -->
    <button v-if="isMobile" @click="toggleMenu" class="menu-button">
      <span class="material-symbols-outlined">menu</span>
    </button>

    <!-- Menu contextuel avec animation -->
    <transition name="fade">
      <div v-if="showMenu" class="menu-contextual" @click.self="toggleMenu">
        <button @click="handleLogout">
          <span class="material-icons">exit_to_app</span> Déconnexion
        </button>
        <button @click="toggleLeftPanel">
          <span class="material-icons">storage</span> Données
        </button>
      </div>
    </transition>

    <div v-if="isMobile" class="mobile-container">
      <transition name="slide-fade">
        <LeftPanel v-if="showLeftPanel" class="mobile-left-panel" :isMobile="true"  :isVisible="showLeftPanel" @close="toggleLeftPanel" />
      </transition>
      <RightPanel class="mobile-right-panel" :userRole="'ROLE_ADMIN'" :key="updateValue"/>
    </div>

    <div class="main-layout">
      <div class="left-panel full-height">
        <LeftPanel :isMobile="isMobile" :userRole="'ROLE_ADMIN'" />
      </div>
      <div class="right-panel full-height">

      <div class="right-scrollable">
          <RightPanel :isMobile="isMobile" :userRole="'ROLE_ADMIN'" :key="updateValue" />
        </div>
        <div class="bottom-panel">
          <BottomPanel :isMobile="isMobile" :userRole="'ROLE_ADMIN'" />
        </div>
      </div>
    </div>
  </div>


</template>

<script>
import LeftPanel from "../components/admin/LeftPanel.vue";
import RightPanel from "../components/terrains/RightPanel.vue";
import {watch} from "vue";
import BottomPanel from "../components/generationValidation/BottomPanel.vue";
import {usePlayersStore} from "../store/usePlayersStore.js";

export default {
  name: "AdminPage",
  components: {
    LeftPanel,
    RightPanel,
    BottomPanel,
  },

  data() {
    return {
      updateValue: 0,
      statusMessage: "Placement réalisé à 95%",
      isMobile: false,
      showLeftPanel: false,
      showMenu: false,
    };
  },
  setup() {
    const { fetchPlayers } = usePlayersStore()
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
    update() {
      this.updateValue++;
      console.log("Update value", this.updateValue);
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
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  padding: 10px;
  box-sizing: border-box;
  background: #eaf1ee;
}

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

/* Menu contextuel */
.menu-contextual {
  position: fixed;
  top: 60px;
  left: 15px;
  background: white;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1500;
  border-radius: 8px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  padding: 10px;
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
  background: white;
  margin: 0;
  padding: 0;
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

.main-layout {
  flex: 1;
  display: flex;
  overflow: hidden;
  gap: 10px;
  height: 100%;
}

.left-panel {
  margin-bottom: 10px;
  width: 35%;
  height: calc(100% - 10px);
  max-width: 500px;
  min-width: 320px;
  background: white;
  overflow-y: auto;
  border-radius: 8px;
}

.full-height {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.right-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border-radius: 8px;
  position: relative;
  gap: 10px;
}

.right-scrollable {
  flex: 1;
  overflow-y: auto;
  background: white;
  border-radius: 8px;
  max-height: calc(100vh - 70px - 10px - 10px);
  box-sizing: border-box;
  position: relative;
  z-index: 2;
}

.bottom-panel {
  height: 70px;
  padding: 10px;
  background: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 15px;
  border-radius: 8px;
  flex-shrink: 0;
  position: relative;
  z-index: 1;
}

@media (max-width: 767px) {
  .admin-page {
    padding: 0;
  }
}

</style>

