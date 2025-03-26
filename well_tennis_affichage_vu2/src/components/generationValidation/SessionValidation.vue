<template>
  <div class="validation">
    <h3>Validation de la session du {{ justification.session.dayWeek }} de {{ justification.session.start }} à {{ justification.session.stop }} sur le {{ justification.session.idCourt.name }}</h3>
    <div>
      <div class="validation-left">
        <div>
          <div v-for="(score, just) in justification.sessionJustifications" class="information-split smaller">
            <p>{{ just }}</p><p>{{ score }}</p>
          </div>
        </div>
        <div class="session-buttons">
          <button class="w-full bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition">
            Valider la session
          </button>
          <button class="w-full bg-white text-[#528359] border border-[#528359] py-1.5 px-4 rounded-md hover:bg-[#f0f0f0] transition">
            Supprimer la session
          </button>
        </div>
      </div>
      <div class="validation-right">
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
.session-buttons {
  display: flex;
  flex-direction: row;
  gap: 1rem;
}
</style>