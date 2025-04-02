<template>
  <div>
    <div class="title">
      <span
          v-if="players.length > 1"
          class="material-symbols-outlined checkbox-icon"
          @click="toggleAll"
      >
        {{
          allChecked ? 'check_box' : someChecked && !allChecked ? 'indeterminate_check_box' : 'check_box_outline_blank'
        }}
      </span>
      <span v-else class="icon-placeholder"></span>
      <span @click="toggleAll">Joueurs</span>
      <span @click="toggleAll">Âge • Niveau</span>
    </div>
    <PlayerSelector
        v-for="(player, index) in players"
        :key="index"
        :player="player"
        :checked="checkedPlayers[index]"
        @update:checked="updateChecked(index, $event)"
    ></PlayerSelector>
  </div>
</template>

<script>
import { computed, watchEffect } from 'vue';
import PlayerSelector from "./PlayerSelector.vue";

export default {
  components: {PlayerSelector},
  props: {
    players: {
      type: Array,
      required: true,
    },
    checkedPlayers: {
      type: Array,
      required: true
    }
  },
  emits: ['update:checked-players'],
  setup(props, { emit }) {
    const allChecked = computed(() => {
      return props.checkedPlayers.length > 0 && props.checkedPlayers.every(checked => checked);
    });

    const someChecked = computed(() => {
      return props.checkedPlayers.some(checked => checked);
    });

    const toggleAll = () => {
      const newState = !allChecked.value;
      const newCheckedStates = props.checkedPlayers.map(() => newState);
      emit('update:checked-players', newCheckedStates);
    };

    const updateChecked = (index, checked) => {
      const newCheckedStates = [...props.checkedPlayers];
      newCheckedStates[index] = checked;
      emit('update:checked-players', newCheckedStates);
    };

    return {
      allChecked,
      someChecked,
      toggleAll,
      updateChecked
    };
  }
};
</script>