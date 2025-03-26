<template>
  <div class="session-validation">
    <h3>Validation de la session du {{ justification.session.dayWeek }} de {{ justification.session.start }} à {{ justification.session.stop }} sur le {{ justification.session.idCourt.name }}</h3>
    <div class="session-validation-data">
      <div class="session-information">
        <div>
          <div v-for="(score, just) in justification.sessionJustifications" class="session-justification">
            <p>{{ just }}</p><p>{{ score }}</p>
          </div>
        </div>
        <div class="session-buttons">
          <button class="w-full bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition">
            Valider la session
          </button>
          <!-- bouton secondaire -->
          <button class="w-full bg-white text-[#528359] border border-[#528359] py-1.5 px-4 rounded-md hover:bg-[#f0f0f0] transition">
            Supprimer la session
          </button>
        </div>
      </div>
      <div class="session-sessions">
        <TrainerDescription
            :trainer="justification.session.idTrainer"
            :checked="trainerChecked"
            @update:checked="trainerChecked = $event"
        />
        <PlayersDescription :players="justification.session.players" />
      </div>
    </div>
  </div>
</template>

<script>
import {ref} from 'vue';
import TrainerDescription from "./widget/TrainerDescription.vue";
import PlayersDescription from "./widget/PlayersDescription.vue";

const justification = ref(null);

export default {
  components: {
    PlayersDescription,
    TrainerDescription
  },
  props: {
    justification: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    justification.value = props.justification;
    const trainerChecked = ref(true);

    return {
      trainerChecked
    };
  }
};
</script>

<style>
h3 {
  font-size: 1.2rem;
  padding-bottom: 0.5rem;
}

.session-validation {
  padding: 0 1rem;
}

.session-validation-data {
  display: flex;
  justify-content: space-between;
  gap: 3rem;

  > * {
    flex: 1;
  }
}

.session-information {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  justify-content: space-between;
}

.session-sessions > :not(:last-child) {
  padding-bottom: 0.5rem;
}

.session-buttons {
  display: flex;
  flex-direction: row;
  gap: 1rem;
}

.session-justification {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  align-items: flex-end;
  padding: 0.5rem 0;
}

.session-justification:not(:last-child) {
  border-bottom: 1px solid #e2e8f0;
}
</style>