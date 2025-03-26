<template>
  <div class="player-selector">
    <div class="player-selector-title">
      <span
          class="material-symbols-outlined checkbox-icon"
          @click="$emit('update:checked', !checked)"
          :style="{ color: '#1A4220' }"
      >
        {{ checked ? 'check_box' : 'check_box_outline_blank' }}
      </span>
      <label class="player-label" @click="$emit('update:checked', !checked)">
        <span>{{ player.name }} {{ player.surname }}</span>
        <span>{{ player.birthday }}ans • {{ player.level }}</span>
      </label>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    player: {
      type: Object,
      required: true,
    },
    checked: {
      type: Boolean,
      default: false
    }
  },
  emits: ['update:checked']
}
</script>

<style >
.player-selector-title {
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

.player-label {
  cursor: pointer;
  color: #1A4220;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
}
</style>