<template>
  <transition name="fade">
    <div v-if="visible" class="confirm-backdrop">
      <div class="confirm-dialog">
        <span class="material-symbols-outlined icon">help</span>
        <p class="message">{{ message }}</p>
        <div class="actions">
          <button class="button secondary" @click="$emit('cancel')">Annuler</button>
          <button class="button primary" @click="$emit('confirm')">Valider</button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "ConfirmDialog",
  props: {
    visible: { type: Boolean, default: false },
    message: { type: String, default: "Êtes-vous sûr de faire cette action ?" },
  },
};
</script>

<style scoped>
.confirm-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(30, 30, 30, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000;
}

.confirm-dialog {
  background: white;
  border-radius: 10px;
  padding: 1.5rem 2rem;
  max-width: 400px;
  width: 90%;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  border: 1px solid #ddd;
}

.confirm-dialog .icon {
  font-size: 2.2rem;
  color: var(--accent);
  margin-bottom: 0.5rem;
}

.message {
  font-size: 1rem;
  color: #333;
  margin-bottom: 1.5rem;
}

.actions {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.button {
  flex: 1;
  padding: 0.6rem 1.2rem;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  border: 1px solid transparent;
  transition: background 0.2s;
  text-align: center;
  justify-content: center;
  display: flex;
}


.button.primary {
  background-color: var(--accent);
  color: white;
  border-color: var(--accent);
}

.button.primary:hover {
  background-color: #345e3b;
}

.button.secondary {
  background-color: white;
  color: var(--accent);
  border: 1px solid var(--accent);
}

.button.secondary:hover {
  background-color: #e6f4eb;
}
</style>
