<template>
  <div class="admin-page min-h-screen flex flex-col w-full">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <button v-if="isMobile && !showLeftPanel" @click="toggleMenu" class="menu-button">
      <span class="material-symbols-outlined">menu</span>
    </button>
    <div v-if="showMenu" class="menu-contextual">
      <button @click="handleLogout">
        <span class="material-icons">exit_to_app</span> Déconnexion
      </button>
      <button @click="toggleLeftPanel">
        <span class="material-icons">storage</span> Donnée
      </button>
    </div>
    <div v-if="isMobile" class="mobile-container">
      <transition name="slide-fade">
        <LeftPanel v-if="showLeftPanel" class="mobile-left-panel" :isMobile="true" @close="toggleLeftPanel" />
      </transition>
      <RightPanel class="mobile-right-panel" :key="updateValue"/>
    </div>
    <div v-else class="flex w-full h-full flex-1">
      <LeftPanel class="desktop-left-panel" :isMobile="false" />
      <RightPanel class="desktop-right-panel" :key="updateValue"/>
    </div>
    <BottomPanel @refreshContent="update" v-if="!isMobile" @launch="handleLaunch" @submitChanges="handleSubmitChanges" @sendSchedule="handleSendSchedule" class="aligned-bottom-panel" />
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
      updateValue: 0,
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

@media (min-width: 768px) and (max-width: 1024px) {
  .aligned-bottom-panel {
    width: 54%;
    left: 44.1%;
    bottom: 1.4%;
    height: auto;
    padding: 1rem;
  }
}

</style>
