<template>
  <div>
    <div class="player-selector-title">
      <span
          v-if="players.length > 1"
          class="material-symbols-outlined checkbox-icon group-checkbox"
          @click="toggleAll"
          :style="{ color: '#AAB7AC' }"
      >
        {{
          allChecked ? 'check_box' : someChecked && !allChecked ? 'indeterminate_check_box' : 'check_box_outline_blank'
        }}
      </span>
      <span v-else class="checkbox-placeholder"></span>
      <label class="group-label" @click="toggleAll">Joueurs</label>
      <label class="group-label" @click="toggleAll">Âge • Niveau</label>
    </div>
    <PlayerSelector
        v-for="(player, index) in players"
        :key="index"
        :player="player"
        :checked="checkedState[index]"
        @update:checked="updateChecked(index, $event)"
    ></PlayerSelector>
  </div>
</template>

<script>
  import { computed, ref } from 'vue';
  import PlayerSelector from "./PlayerSelector.vue";

export default {
  components: {PlayerSelector},
  props: {
    players: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const checkedState = ref(props.players.map(() => true));

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
.player-selector-title {
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

  &:not(:last-child) {
    flex: 1;
  }
}
</style>