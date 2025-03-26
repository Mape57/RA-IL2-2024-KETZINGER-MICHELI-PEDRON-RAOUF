<template>
  <div>
    <div class="trainer-selector-title">
      <span class="checkbox-placeholder"></span>
      <label class="group-label">Entraîneur</label>
      <label class="group-label" v-if="trainer">Préf. âge • Préf. niveau</label>
    </div>
    <div class="trainer-selector">
      <div class="trainer-selector-title">
        <span
            v-if="trainer"
            class="material-symbols-outlined checkbox-icon"
            @click="toggleChecked"
            :style="{ color: '#1A4220' }"
        >
          {{ checked ? 'check_box' : 'check_box_outline_blank' }}
        </span>
        <span v-else class="checkbox-placeholder"></span>
        <label v-if="trainer" class="trainer-label" @click="toggleChecked">
          <span>{{ trainer.name }} {{ trainer.surname }} </span>
          <span>{{ trainer.infLevel }} - {{ trainer.supLevel }} • {{ trainer.infAge }} - {{ trainer.supAge }}</span>
        </label>
        <label v-else class="trainer-label">
          <span>Pas d'entraîneur sur la session</span>
        </label>
      </div>
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

<style>
.trainer-selector-title {
  display: flex;
  align-items: center;
  cursor: pointer;

  > * {
    color: #AAB7AC;
  }
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

.trainer-selector-title {
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

.trainer-label {
  cursor: pointer;
  color: #1A4220;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
}
</style>