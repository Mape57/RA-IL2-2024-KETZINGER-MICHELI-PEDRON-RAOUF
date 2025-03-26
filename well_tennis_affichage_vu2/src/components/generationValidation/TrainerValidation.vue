<template>
  <div class="trainer-validation">
    <h3>Validation de l'entraîneur {{ justification.trainer.name }} {{ justification.trainer.surname }}</h3>
    <div class="trainer-validation-data">
      <div class="trainer-information">
        <div>
          <div class="trainer-info">
            <p>Préf. âge : {{ justification.trainer.infLevel }}-{{ justification.trainer.supLevel }}</p>
            <p>Préf. niveau : {{ justification.trainer.infAge }}-{{ justification.trainer.supAge }}</p>
          </div>
          <Disponibilities :disponibilities="justification.trainer.disponibilities"></Disponibilities>
        </div>
        <button class="w-full bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition">
          Valider l'entraîneur'
        </button>

      </div>
      <div class="trainer-sessions">
        <GroupSessionSelector v-if="justification.unavailabilities.length" :title="'Indisponibilités'"
                              :sessions="justification.unavailabilities"></GroupSessionSelector>
        <GroupSessionSelector v-if="justification.overlappingSessions.length" :title="'Session en simultanée'"
                              :sessions="justification.overlappingSessions"></GroupSessionSelector>
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

.trainer-validation {
  padding: 0 1rem;
}

.trainer-validation-data {
  display: flex;
  justify-content: space-between;
  gap: 3rem;

  > * {
    flex: 1;
  }
}

.trainer-information {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  justify-content: space-between;
}

.trainer-info {
  display: flex;

  > * {
    flex: 1;
  }
}

.trainer-sessions > :not(:last-child) {
  padding-bottom: 0.5rem;
}
</style>