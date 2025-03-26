<template>
  <div>
    <div class="title">
      <div
          v-if="sessions.length > 1"
          class="material-symbols-outlined checkbox-icon"
          @click="toggleAll"
      >
        {{
          allChecked ? 'check_box' : someChecked && !allChecked ? 'indeterminate_check_box' : 'check_box_outline_blank'
        }}
      </div>
      <span v-else class="icon-placeholder"></span>
      <span @click="toggleAll">{{ title }}</span>
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