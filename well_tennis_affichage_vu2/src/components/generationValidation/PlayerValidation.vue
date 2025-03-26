<template>
  <div class="validation">
    <h3>Validation du joueur {{ justification.player.name }} {{ justification.player.surname }}</h3>
    <div>
      <div class="validation-left">
        <div>
          <div class="information-split">
            <p>Âge : {{ justification.player.birthday }}</p>
            <p>Niveau : {{ justification.player.level }}</p>
          </div>
          <Disponibilities :disponibilities="justification.player.disponibilities"></Disponibilities>
        </div>
        <button class="w-full bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition">
          Valider le joueur
        </button>
      </div>
      <div class="validation-right">
        <SessionsSelector v-if="justification.unavailabilities.length" :title="'Indisponibilités'"
                         :sessions="justification.unavailabilities"></SessionsSelector>
        <SessionsSelector v-if="justification.tooManySessionsPerDay.length" :title="'Session sur le même jour'"
                         :sessions="justification.tooManySessionsPerDay"></SessionsSelector>
      </div>
    </div>
  </div>
</template>

<script>
import Disponibilities from "./widget/Disponibilities.vue";
import SessionsSelector from "./widget/SessionsSelector.vue";

export default {
  components: {SessionsSelector, Disponibilities},
  props: {
    justification: {
      type: Object,
      required: true,
    },
  }
};
</script>