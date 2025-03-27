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
          <button @click="open = false" class="material-symbols-outlined text-[#2F855A] text-4xl transition-transform transform hover:scale-110 hover:text-red-500">
            close
          </button>
        </div>

        <div class="popup-content">
          <SessionValidation
              v-for="justification in justifications"
              :key="justification.session.id"
              :justification="justification"
              @session-handled="removeJustification" />
        </div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import {ref, watch} from 'vue';
import SessionValidation from './SessionValidation.vue';
import useSolver from "../../useJs/useSolver.js";

const open = ref(false);

defineEmits(['close']);
const justifications = ref([]);

watch(open, async (isOpen) => {
  if (isOpen) {
    justifications.value = await useSolver().solverJustifications();
  }
});

const removeJustification = (sessionId) => {
  justifications.value = justifications.value.filter(
      justification => justification.session.id !== sessionId
  );
};
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
    width: 90%;
    height: 90%;
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
  padding: 2rem 5% 1rem;
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
    align-items: center;
    gap: 0.5rem;
  }
}

.divider-line {
  flex-grow: 1;
  height: 1px;
  background-color: #e2e8f0;
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
  padding: 0 1rem 1.5rem;

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
          gap: 2rem;
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