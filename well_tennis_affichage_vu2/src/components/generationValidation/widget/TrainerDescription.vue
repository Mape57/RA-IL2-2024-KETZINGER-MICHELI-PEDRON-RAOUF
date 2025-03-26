<template>
  <div>
    <div class="title">
      <span class="icon-placeholder"></span>
      <span>Entraîneur</span>
      <span v-if="trainer">Préf. âge • Préf. niveau</span>
    </div>
    <div class="selecteur">
      <div
          v-if="trainer"
          class="material-symbols-outlined checkbox-icon"
          @click="toggleChecked"
      >
        {{ checked ? 'check_box' : 'check_box_outline_blank' }}
      </div>
      <div v-else class="icon-placeholder"></div>

      <span v-if="trainer" @click="toggleChecked">
        <span>{{ trainer.name }} {{ trainer.surname }} </span>
        <span>{{ trainer.infLevel }} - {{ trainer.supLevel }} • {{ trainer.infAge }} - {{ trainer.supAge }}</span>
      </span>
      <span v-else>
        <span>Pas d'entraîneur sur la session</span>
      </span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    trainer: {
      type: Object,
      required: false,
      default: null
    },
    checked: {
      type: Boolean,
      default: false
    }
  },
  emits: ['update:checked'],
  setup(props, {emit}) {
    const toggleChecked = () => {
      emit('update:checked', !props.checked);
    };

    return {
      toggleChecked
    };
  }
};
</script>