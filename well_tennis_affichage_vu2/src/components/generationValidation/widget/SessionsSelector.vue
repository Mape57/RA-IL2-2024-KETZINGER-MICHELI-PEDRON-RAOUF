<template>
  <div>
    <div class="session-selector-title">
      <span
          v-if="sessions.length > 1"
          class="material-symbols-outlined checkbox-icon group-checkbox"
          @click="toggleAll"
          :style="{ color: '#AAB7AC' }"
      >
        {{
          allChecked ? 'check_box' : someChecked && !allChecked ? 'indeterminate_check_box' : 'check_box_outline_blank'
        }}
      </span>
      <span v-else class="checkbox-placeholder"></span>
      <label class="group-label" @click="toggleAll">{{ title }}</label>
    </div>
    <SessionSelector
        v-for="(session, index) in sessions"
        :key="index"
        :session="session"
        :checked="checkedState[index]"
        @update:checked="updateChecked(index, $event)"
    ></SessionSelector>
  </div>
</template>

<script>
import { computed, ref } from 'vue';
import SessionSelector from "./SessionSelector.vue";

export default {
  components: {SessionSelector},
  props: {
    title: {
      type: String,
      required: true,
    },
    sessions: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const checkboxId = computed(() => {
      return `checkbox-${props.title.replace(/\s+/g, '-').toLowerCase()}-${Math.random().toString(36).substring(2, 9)}`;
    });

    const checkedState = ref(props.sessions.map(() => true));

    const allChecked = computed(() => {
      return checkedState.value.length > 0 && checkedState.value.every(checked => checked);
    });

    const someChecked = computed(() => {
      return checkedState.value.some(checked => checked);
    });

    const toggleAll = () => {
      const newState = !allChecked.value;
      checkedState.value = checkedState.value.map(() => newState);
    };

    const updateChecked = (index, checked) => {
      checkedState.value[index] = checked;
    };

    return {
      checkboxId,
      checkedState,
      allChecked,
      someChecked,
      toggleAll,
      updateChecked
    };
  }
};
</script>

<style>
.session-selector-title {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #AAB7AC;
}

.checkbox-icon.group-checkbox {
  padding-right: 0.5rem;
  font-size: 1.25rem;
}

.checkbox-placeholder {
  width: 1.75rem; /* Accounts for icon width + padding-right */
}

.group-label {
  font-weight: bold;
  cursor: pointer;
}
</style>