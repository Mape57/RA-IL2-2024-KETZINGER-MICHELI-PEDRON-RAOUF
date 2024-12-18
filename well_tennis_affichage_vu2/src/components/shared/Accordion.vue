<template>
  <div class="accordion border border-gray-300 rounded-lg shadow-sm mb-4">
    <!-- En-tête de l'accordion -->
    <div
        class="accordion-header flex justify-between items-center cursor-pointer px-4 py-2 bg-gray-100 hover:bg-gray-200"
        @click="toggle"
    >
      <div class="flex items-center">
        <span :class="iconClass" class="material-symbols-outlined mr-2 transition-transform duration-300">
          expand_more
        </span>
        <h3 class="font-bold text-lg">{{ title }}</h3>
      </div>
    </div>
    <!-- Contenu de l'accordion -->
    <div v-if="isOpen" class="accordion-content p-4">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "Accordion",
  props: {
    title: {
      type: String,
      required: true, // Le titre de l'accordion
    },
    initiallyOpen: {
      type: Boolean,
      default: false, // Indique si l'accordion est ouvert par défaut
    },
  },
  data() {
    return {
      isOpen: this.initiallyOpen, // État ouvert/fermé
    };
  },
  computed: {
    iconClass() {
      return this.isOpen ? "rotate-180" : ""; // Tourne l'icône si l'accordion est ouvert
    },
  },
  methods: {
    toggle() {
      this.isOpen = !this.isOpen;
    },
  },
};
</script>

<style scoped>
/* Transition pour l'icône */
.material-symbols-outlined {
  transition: transform 0.3s ease-in-out;
}
/* Icône tourné */
.rotate-180 {
  transform: rotate(180deg);
}
</style>
