<template>
  <div class="validation">
    <h3>Validation de la session du {{ getDay(justification.session.dayWeek) }} de {{ justification.session.start }} à
      {{ justification.session.stop }} sur le {{ justification.session.idCourt.name }}</h3>
    <div>
      <div class="validation-left">
        <div>
          <div v-for="(score, just) in justification.sessionJustifications" class="information-split smaller">
            <p>{{ just }}</p>
            <p class="score">{{ getFormattedScore(score) }}</p>
          </div>
        </div>
        <div class="session-buttons">
          <button @click="updateSession"
                  class="w-full bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition">
            Valider la session
          </button>
          <button @click="deleteSession"
                  class="w-full bg-white text-[#528359] border border-[#528359] py-1.5 px-4 rounded-md hover:bg-[#f0f0f0] transition">
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
        <PlayersDescription
            :players="justification.session.players"
            :checked-players="playerCheckedStates"
            @update:checked-players="playerCheckedStates = $event"
        />
      </div>
    </div>
  </div>
</template>

<script>
import {ref} from 'vue';
import TrainerDescription from "./TrainerDescription.vue";
import PlayersDescription from "./PlayersDescription.vue";
import {getDay, getFormattedScore} from "../../../functionality/conversionUtils.js";
import {useSessionsStore} from "../../../store/useSessionsStore.js";

export default {
  methods: {getFormattedScore, getDay},
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
  emits: ['session-handled'],
  setup(props, ctx) {
    const trainerChecked = ref(true);
    const playerCheckedStates = ref(props.justification.session.players.map(() => true));

    const updateSession = async () => {
      const sessionData = {...props.justification.session};

      sessionData.players = props.justification.session.players
          .filter((_, index) => playerCheckedStates.value[index])

      if (!trainerChecked.value) {
        sessionData.idTrainer = null;
      }

      try {
        await useSessionsStore().updateSession(sessionData);
        console.log("Session updated successfully");
        ctx.emit('session-handled', props.justification.session.id);
      } catch (error) {
        console.error("Failed to update session:", error);
      }
    };

    const deleteSession = async () => {
      try {
        await useSessionsStore().deleteSession(props.justification.session.id);
        console.log("Session supprimée avec succès");
        ctx.emit('session-handled', props.justification.session.id);
      } catch (error) {
        console.error("Échec de la suppression de la session:", error);
      }
    };

    return {
      trainerChecked,
      playerCheckedStates,
      updateSession,
      deleteSession
    };
  }
};
</script>

<style scoped>
.session-buttons {
  display: flex;
  flex-direction: row;
  gap: 1rem;
}

.score {
  text-wrap: nowrap;
  font-weight: bold;
}

.session-content {
  > * {
    margin-bottom: 1rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid #e2e8f0;
  }

  > *:last-child {
    border-bottom: none;
  }
}
</style>