<template>
  <button
      @click="open = true"
      class="bg-white text-[#528359] py-1.5 px-3 rounded-md flex items-center border border-[#528359] text-sm hover:bg-[#e6f4eb] transition"
  >
    <span class="material-symbols-outlined mr-1 text-base">rule_folder</span>
    Valider la génération
  </button>
  <Teleport to="body">
    <div v-if="open" class="modal">
      <div class="modal-dialog">
        <div class="modal-header">
          <h2>Validation de la génération</h2>
          <button @click="open = false" class="close-button">
            <span class="material-symbols-outlined">close</span>
          </button>
        </div>

        <div class="modal-content">
          <div v-for="(justification, index) in justifications" :key="index" class="justification-item">
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

        <div class="modal-footer">
          <button
              @click="open = false"
              class="bg-[#528359] text-white py-1.5 px-4 rounded-md hover:bg-[#456c4c] transition"
          >
            Fermer
          </button>
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

<style scoped>
.modal {
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
}

.modal-dialog {
  width: 60%;
  height: 70%;
  background-color: white;
  border-radius: 0.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #e2e8f0;
}

.modal-header h2 {
  font-size: 1.5rem;
  font-weight: bold;
  color: #2F855A;
  margin: 0;
}

.close-button {
  background: none;
  border: none;
  cursor: pointer;
  color: #4A5568;
}

.close-button:hover {
  color: #2F855A;
}

.modal-content {
  padding: 1rem;
  overflow-y: auto;
  flex: 1;
}

.justification-item {
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #e2e8f0;
}

.justification-item:last-child {
  border-bottom: none;
}

.modal-footer {
  padding: 1rem;
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid #e2e8f0;
}

@media (max-width: 768px) {
  .modal-dialog {
    width: 95%;
    max-height: 90vh;
  }
}
</style>