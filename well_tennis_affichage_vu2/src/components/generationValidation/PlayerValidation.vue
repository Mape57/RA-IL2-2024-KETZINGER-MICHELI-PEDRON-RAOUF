<template>
  <div class="player-validation">
    <h3>Validation du joueur {{ justification.player.name }} {{ justification.player.surname }}</h3>
    <div class="player-validation-data">
      <div class="player-information">
        <div>
          <div class="player-info">
            <p>Âge : {{ justification.player.birthday }}</p>
            <p>Niveau : {{ justification.player.level }}</p>
          </div>
          <Disponibilities :disponibilities="justification.player.disponibilities"></Disponibilities>
        </div>
        <button class="w-full bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition">
          Valider le joueur
        </button>

      </div>
      <div class="player-sessions">
        <GroupSessionSelector v-if="justification.unavailabilities.length" :title="'Indisponibilités'"
                         :sessions="justification.unavailabilities"></GroupSessionSelector>
        <GroupSessionSelector v-if="justification.tooManySessionsPerDay.length" :title="'Session sur le même jour'"
                         :sessions="justification.tooManySessionsPerDay"></GroupSessionSelector>
      </div>
    </div>
  </div>
</template>

<script>
import {ref} from 'vue';
import Disponibilities from "./widget/Disponibilities.vue";
import SessionSelector from "./widget/SessionsSelector.vue";

const justification = ref(null);

export default {
  components: {GroupSessionSelector: SessionSelector, Disponibilities},
  props: {
    justification: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    justification.value = props.justification;
  }
};
</script>

<style>
h3 {
  font-size: 1.2rem;
  padding-bottom: 0.5rem;
}

.player-validation {
  padding: 0 1rem;
}

.player-validation-data {
  display: flex;
  justify-content: space-between;
  gap: 3rem;

  > * {
    flex: 1;
  }
}

.player-information {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  justify-content: space-between;
}

.player-info {
  display: flex;

  > * {
    flex: 1;
  }
}

.player-sessions > :not(:last-child) {
  padding-bottom: 0.5rem;
}
</style>