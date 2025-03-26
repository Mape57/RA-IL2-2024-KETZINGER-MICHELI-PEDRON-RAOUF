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