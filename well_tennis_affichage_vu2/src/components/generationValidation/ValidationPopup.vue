<template>
  <button
      @click="open = true"
      class="bg-white text-[#528359] py-1.5 px-3 rounded-md flex items-center border border-[#528359] text-sm hover:bg-[#e6f4eb] transition"
  >
    <span class="material-symbols-outlined mr-1 text-base">rule_folder</span>
    Valider la génération
  </button>
  <Teleport to="body">
    <div v-if="open" class="popup">
      <div>
        <div class="popup-header">
          <h2>Validation de la génération</h2>
          <button @click="open=false" class="close-button">
            <span class="material-symbols-outlined">close</span>
          </button>
        </div>

        <div class="popup-content">
          <div v-for="(justification, index) in justifications" :key="index">
            <PlayerValidation
                v-if="justification.type === 'PLAYER'"
                :justification="justification"
            />
            <SessionValidation
                v-else-if="justification.type === 'SESSION'"
                :justification="justification"
            />
            <TrainerValidation
                v-else-if="justification.type === 'TRAINER'"
                :justification="justification"
            />
          </div>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import {ref, watch} from 'vue';
import PlayerValidation from './PlayerValidation.vue';
import SessionValidation from './SessionValidation.vue';
import TrainerValidation from './TrainerValidation.vue';
import useSolver from "../../useJs/useSolver.js";

const open = ref(false);

defineEmits(['close']);
const justifications = ref([]);

watch(open, async (isOpen) => {
  if (isOpen) {
    justifications.value = await useSolver().solverJustifications();
  }
});

</script>

<style>
.popup {
  position: fixed;
  z-index: 1500;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  
  > div {
    width: 60%;
    height: 70%;
    background-color: white;
    border-radius: 0.5rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    overflow: hidden;
  }
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #e2e8f0;

  > h2 {
    font-size: 1.5rem;
    font-weight: bold;
    color: #2F855A;
    margin: 0;
  }
}

.popup-content {
  padding: 1rem;
  overflow-y: auto;
  flex: 1;

  > * {
    margin-bottom: 1rem;
    padding-bottom: 1rem;
    border-bottom: 1px solid #e2e8f0;
  }

  > *:last-child {
    border-bottom: none;
  }
}

.selecteur {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #1A4220;

  > .checkbox-icon {
    padding-right: 0.5rem;
    font-size: 1.25rem;
    color: inherit;
  }

  > span {
    width: 100%;
    cursor: pointer;
    color: inherit;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
}

.icon-placeholder {
  width: 1.75rem;
  flex-basis: 1.75rem;
}

.checkbox-icon {
  padding-right: 0.5rem;
  font-size: 1.25rem;
  color: #AAB7AC;
}

.title {
  display: flex;
  align-items: center;
  cursor: pointer;

  > span:not(.checkbox-icon) {
    color: #AAB7AC;
    font-weight: bold;
    cursor: pointer;

    &:nth-child(2) {
      flex: 1;
    }
  }
}

.validation {
  padding: 0 1rem;

  > h3 {
    font-size: 1.2rem;
    padding-bottom: 0.5rem;
  }

  > div {
    display: flex;
    justify-content: space-between;
    gap: 3rem;

    .validation-left {
      display: flex;
      flex-direction: column;
      gap: 1rem;
      justify-content: space-between;

      .information-split {
        display: flex;

        &.smaller {
          justify-content: space-between;
          gap: 1rem;
          align-items: flex-end;
        }

        &:not(.smaller) > * {
          flex: 1;
        }
      }
    }

    .validation-right > :not(:last-child) {
      padding-bottom: 0.5rem;
    }

    > * {
      flex: 1;
    }
  }
}
</style>