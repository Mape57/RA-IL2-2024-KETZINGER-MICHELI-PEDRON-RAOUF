<template>
  <p>Liste des disponibilités :</p>
  <ul>
    <li v-for="disponibility in disponibilities">
      {{ days[disponibility.dayWeek] }} de {{ disponibility.open }} à {{ disponibility.close }}
    </li>
  </ul>
</template>

<script>
export default {
  props: {
    disponibilities: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      days: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche']
    };
  }
};
</script>

<style>
ul {
  list-style-type: disc;
  padding-inline-start: 1.5em;
}
</style>
