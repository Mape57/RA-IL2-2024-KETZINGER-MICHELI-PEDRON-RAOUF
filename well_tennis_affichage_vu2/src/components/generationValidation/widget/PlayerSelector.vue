<template>
    <div class="selecteur">
      <div
          class="material-symbols-outlined checkbox-icon"
          @click="$emit('update:checked', !checked)"
      >
        {{ checked ? 'check_box' : 'check_box_outline_blank' }}
      </div>
      <span @click="$emit('update:checked', !checked)">
        <span>{{ player.name }} {{ player.surname }}</span>
        <span class="divider-line"></span>
        <span>{{ getSportsAge(player.birthday) }} ans • {{ player.level }}</span>
      </span>
    </div>
</template>

<script>

import {getSportsAge} from "../../../functionality/conversionUtils.js";

export default {
  methods: {getSportsAge},
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
  emits: ['update:checked'],
}


</script>