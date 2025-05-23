<template>
  <button
      class="button secondary"
      @click="open = true"
  >
    <span class="material-symbols-outlined">rule_folder</span>
    Valider la génération
  </button>

  <Teleport to="body">
    <Transition name="fade">
      <div v-if="open" class="popup">
        <div>
          <div class="popup-header">
            <h2>Validation de la génération</h2>
            <button @click="kiSolver" class="button secondary" title="Fermer la popup">
              <span class="material-symbols-outlined">delete_forever</span>
              Valider l'ensemble des éléments
            </button>
            <button @click="open = false" class="button secondary red no-text" title="Fermer la popup">
              <span class="material-symbols-outlined">close</span>
            </button>
          </div>

          <div class="popup-content">
            <div v-if="isLoading" class="loader-container">
              <div class="loader"></div>
            </div>
            <SessionValidation
                v-else
                v-for="justification in justifications"
                :key="justification.session.id"
                :justification="justification"
                @session-handled="removeJustification"/>
            <p v-if="!isLoading && justifications.length === 0" class="no-results">
              Aucune validation nécessaire
            </p>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import {ref, watch} from 'vue';
import SessionValidation from './widget/SessionValidation.vue';
import useSolver from "../../useJs/useSolver.js";

const open = ref(false);
const isLoading = ref(false);

const emit = defineEmits(['close', 'state-changed']);
const justifications = ref([]);

watch(open, async (isOpen) => {
  if (isOpen) {
    isLoading.value = true;
    try {
      justifications.value = await useSolver().solverJustifications();
    } catch (error) {
      console.error("Erreur lors du chargement des justifications:", error);
    } finally {
      isLoading.value = false;
    }
  }
});

const removeJustification = (sessionId) => {
  justifications.value = justifications.value.filter(
      justification => justification.session.id !== sessionId
  );
};

const kiSolver = async () => {
  try {
    const response = await useSolver().kiGet();
    justifications.value = [];
    emit('state-changed', 'STOPPED');
    console.log("KI Solver response:", response);
  } catch (error) {
    console.error("Erreur lors de la récupération du ki:", error);
  }
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

  > * {
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


.loader-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
  color: #2F855A;
}

.no-results {
  text-align: center;
  color: #718096;
  padding: 2rem 0;
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 1rem 1rem 1rem 1.5rem;
  border-bottom: 1px solid #e2e8f0;
  gap: 20px;

  > h2 {
    flex: 1;
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

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}


/* HTML: <div class="loader"></div> */
.loader {
  width: 65px;
  aspect-ratio: 1;
  position: relative;
}
.loader:before,
.loader:after {
  content: "";
  position: absolute;
  border-radius: 50px;
  box-shadow: 0 0 0 3px inset var(--accent);
  animation: l5 2.5s infinite;
}
.loader:after {
  animation-delay: -1.25s;
  border-radius: 0;
}
@keyframes l5{
  0%    {inset:0    35px 35px 0   }
  12.5% {inset:0    35px 0    0   }
  25%   {inset:35px 35px 0    0   }
  37.5% {inset:35px 0    0    0   }
  50%   {inset:35px 0    0    35px}
  62.5% {inset:0    0    0    35px}
  75%   {inset:0    0    35px 35px}
  87.5% {inset:0    0    35px 0   }
  100%  {inset:0    35px 35px 0   }
}

</style>