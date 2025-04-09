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
  background-color: white;
}


#app {
  font-family: var(--font-family-base);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  letter-spacing: 0.015em;
}
</style>