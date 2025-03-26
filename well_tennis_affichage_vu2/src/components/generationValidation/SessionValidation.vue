<template>
  <div class="validation">
    <h3>Validation de la session du {{ getDay(justification.session.dayWeek) }} de {{ justification.session.start }} à {{ justification.session.stop }} sur le {{ justification.session.idCourt.name }}</h3>
    <div>
      <div class="validation-left">
        <div>
          <div v-for="(score, just) in justification.sessionJustifications" class="information-split smaller">
            <p>{{ just }}</p><p class="score">{{ getFormattedScore(score) }}</p>
          </div>
        </div>
        <div class="session-buttons">
          <button @click="updateSession" class="w-full bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition">
            Valider la session
          </button>
          <button @click="deleteSession" class="w-full bg-white text-[#528359] border border-[#528359] py-1.5 px-4 rounded-md hover:bg-[#f0f0f0] transition">
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
import TrainerDescription from "./widget/TrainerDescription.vue";
import PlayersDescription from "./widget/PlayersDescription.vue";
import useSessions from "../../useJs/useSessions.js";
import {getDay, getFormattedScore} from "../../functionality/conversionUtils.js";

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
  setup(props) {
    const trainerChecked = ref(true);
    const playerCheckedStates = ref(props.justification.session.players.map(() => true));

    const updateSession = () => {
      const sessionData = { ...props.justification.session };

      if (!trainerChecked.value) {
        sessionData.idTrainer = null;
      } else if (sessionData.idTrainer) {
        sessionData.idTrainer = sessionData.idTrainer.id;
      }

      sessionData.playerIds = props.justification.session.players
          .filter((_, index) => playerCheckedStates.value[index])
          .map(player => player.id);
      sessionData.playerIds = sessionData.playerIds || [];

      if (sessionData.idCourt && typeof sessionData.idCourt === 'object') {
        sessionData.idCourt = sessionData.idCourt.id;
      }

      useSessions().updateSession(sessionData)
          .then(() => console.log("Session updated successfully"))
          .catch(error => console.error("Failed to update session:", error));
    };

    const deleteSession = () => {
      if (confirm('Êtes-vous sûr de vouloir supprimer cette session ?')) {
        useSessions().deleteSession(props.justification.session.id)
            .then(() => {
              console.log("Session supprimée avec succès");
            })
            .catch(error => console.error("Échec de la suppression de la session:", error));
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

<style>
.session-buttons {
  display: flex;
  flex-direction: row;
  gap: 1rem;
}

.score {
  text-wrap: nowrap;
  font-weight: bold;
}
</style>