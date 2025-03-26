<template>
  <div class="session-selector">
    <div class="session-selector-title">
      <span
          class="material-symbols-outlined checkbox-icon"
          @click="$emit('update:checked', !checked)"
          :style="{ color: '#1A4220' }"
      >
        {{ checked ? 'check_box' : 'check_box_outline_blank' }}
      </span>
      <label class="session-label" @click="$emit('update:checked', !checked)">
        {{ session.court }} le {{ session.dayWeek }} de {{ session.start }} à {{ session.stop }}
      </label>
    </div>
  </div>
</template>

<script>
import {computed} from "vue";

export default {
  props: {
    session: {
      type: Object,
      required: true,
    },
    checked: {
      type: Boolean,
      default: false
    }
  },
  emits: ['update:checked'],
  setup(props) {
    const checkboxId = computed(() => {
      return `checkbox-${Math.random().toString(36).substring(2, 9)}`;
    });

    return {
      checkboxId
    };
  }
}
</script>

<style >
.session-selector-title {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #1A4220;
}

.checkbox-icon {
  padding-right: 0.5rem;
  font-size: 1.25rem;
  color: #1A4220;
}

.session-label {
  cursor: pointer;
  color: #1A4220;
}
</style>