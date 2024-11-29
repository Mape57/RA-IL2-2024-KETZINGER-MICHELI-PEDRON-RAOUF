<template>
  <div class="min-h-screen flex flex-col">
    <div class="w-full h-[calc(100vh-40px)] flex space-x-4 px-4 mt-5 mb-5">
      <!-- Composant de gauche -->
      <LeftPanel />
      <!-- Composant de droite -->
      <RightPanel />
    </div>
  </div>
</template>

<script>
import LeftPanel from "../components/admin/LeftPanel.vue";
import RightPanel from "../components/admin/RightPanel.vue";

export default {
  name: "MainPage",
  components: {
    LeftPanel,
    RightPanel,
  },
};
</script>

<style>
body {
  background-color: #f5f0e8;
}
</style>
