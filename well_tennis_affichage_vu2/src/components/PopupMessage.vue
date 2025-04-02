<template>
  <transition name="fade">
    <div v-if="visible" class="popup-message" :class="type">
      <span class="material-symbols-outlined icon">
        {{ icon }}
      </span>
      <span class="text">{{ message }}</span>
    </div>
  </transition>
</template>

<script>
export default {
  name: "PopupMessage",
  props: {
    message: String,
    type: {
      type: String,
      default: "success", // 'success', 'error', 'warning', 'info'
    },
    duration: {
      type: Number,
      default: 3000,
    },
  },
  data() {
    return {
      visible: true,
    };
  },
  computed: {
    icon() {
      switch (this.type) {
        case "error":
          return "error";
        case "warning":
          return "warning";
        case "info":
          return "info";
        default:
          return "check_circle";
      }
    },
  },
  mounted() {
    setTimeout(() => {
      this.visible = false;
    }, this.duration);
  },
};
</script>

<style scoped>
.popup-message {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #e6f4eb;
  border: 1px solid var(--accent);
  color: var(--accent);
  padding: 1rem 1.5rem;
  border-radius: 10px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
  display: flex;
  align-items: center;
  gap: 0.7rem;
  z-index: 10000;
  font-size: 1rem;
  font-weight: 500;
  text-align: center;
  max-width: 90%;
}

.popup-message.success {
  background-color: #e6f4eb;
  border-color: var(--accent);
  color: var(--accent);
}

.popup-message.error {
  background-color: #ffe6e6;
  border-color: #e53e3e;
  color: #e53e3e;
}

.popup-message.warning {
  background-color: #fff9e6;
  border-color: #ecc94b;
  color: #b7791f;
}

.popup-message.info {
  background-color: #e6f0ff;
  border-color: #4299e1;
  color: #2b6cb0;
}

.icon {
  font-size: 1.6rem;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

</style>
