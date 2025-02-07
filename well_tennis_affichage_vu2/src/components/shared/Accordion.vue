<template>
  <div class="accordion-section mb-6">
    <div class="flex justify-between items-center cursor-pointer" @click="toggle">
      <h3 class="font-bold text-lg">{{ title }}</h3>
      <span
          :class="{ 'rotate-180': isOpen }"
          class="material-symbols-outlined transition-transform duration-300"
      >
        expand_more
      </span>
    </div>
    <hr class="border-gray-300 mb-2" />

    <!-- Contenu dynamique -->
    <div v-if="isOpen">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "Accordion",
  props: {
    title: String,
    defaultOpen: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      isOpen: this.defaultOpen,
    };
  },
  methods: {
    toggle() {
      this.isOpen = !this.isOpen;
    },
  },
};
</script>

<style scoped>
.material-symbols-outlined {
  transition: transform 0.3s ease;
}
.rotate-180 {
  transform: rotate(180deg);
}
</style>
