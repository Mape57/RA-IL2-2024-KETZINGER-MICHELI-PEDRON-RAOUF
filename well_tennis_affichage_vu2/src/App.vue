<template>
  <router-view />
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
/* Styles globaux */
body {
  background-color: #f5f0e8;
}


#app {
  font-family: var(--font-family-base);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  letter-spacing: 0.015em;
}
</style>