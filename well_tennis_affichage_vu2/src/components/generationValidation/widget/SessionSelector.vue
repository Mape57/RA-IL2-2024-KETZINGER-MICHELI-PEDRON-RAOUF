<template>
  <div class="selecteur">
    <div
        class="material-symbols-outlined checkbox-icon"
        @click="$emit('update:checked', !checked)"
    >
      {{ checked ? 'check_box' : 'check_box_outline_blank' }}
    </div>
    <span @click="$emit('update:checked', !checked)">
      {{ session.court }} le {{ session.dayWeek }} de {{ session.start }} à {{ session.stop }}
    </span>
  </div>
</template>

<script>
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
  emits: ['update:checked']
}
</script>