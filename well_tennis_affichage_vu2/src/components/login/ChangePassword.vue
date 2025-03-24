<template>
  <div class="bg-[#fefdf8] flex justify-center items-center p-4 lg:min-h-[100dvh]">
    <div class="flex flex-col lg:flex-row shadow-xl border border-gray-300 rounded-lg overflow-hidden w-full max-w-[1140px] h-[calc(100dvh-2rem)] sm:h-auto">
      <!-- Logo Section -->
      <LogoSection class="flex-shrink-0 h-[200px] sm:h-[300px] md:h-[400px] lg:h-[510px]" />

      <!-- Password Change Form -->
      <div class="w-full bg-white flex justify-center items-center shadow-md p-4 lg:p-0 lg:w-[760px] h-auto lg:h-[510px]">
        <div v-if="showForm" class="text-center w-full lg:w-auto">
          <h1 class="text-[#1A4220] text-xl lg:text-2xl font-bold mb-4 lg:mb-6">Changement de mot de passe</h1>
          <form class="space-y-4" @submit.prevent="handleSubmit">
            <div class="relative w-full lg:w-[380px] mx-auto">
              <input
                  id="password-field"
                  type="password"
                  placeholder="Nouveau mot de passe"
                  class="w-full lg:w-[380px] h-[50px] rounded-lg p-3 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
                  v-model="password"
                  required
              />
              <span
                  @click="togglePasswordVisibility('password-field')"
                  class="absolute right-3 top-1/2 -translate-y-1/2 text-[#528359] cursor-pointer material-symbols-outlined"
              >
                {{ passwordVisible ? 'visibility_off' : 'visibility' }}
              </span>
            </div>

            <div class="relative w-full lg:w-[380px] mx-auto">
              <input
                  id="confirm-password-field"
                  type="password"
                  placeholder="Confirmer le mot de passe"
                  class="w-full lg:w-[380px] h-[50px] rounded-lg p-3 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
                  v-model="confirmPassword"
                  required
              />
              <span
                  @click="togglePasswordVisibility('confirm-password-field')"
                  class="absolute right-3 top-1/2 -translate-y-1/2 text-[#528359] cursor-pointer material-symbols-outlined"
              >
                {{ confirmPasswordVisible ? 'visibility_off' : 'visibility' }}
              </span>
            </div>

            <button
                type="submit"
                class="w-full lg:w-[380px] h-[50px] bg-[#528359] text-white text-base rounded-lg shadow-md py-3 hover:bg-[#456c4c] transition mx-auto flex justify-center items-center"
            >
              Changer le mot de passe
            </button>
          </form>
        </div>

        <div v-else-if="isLoading" class="flex flex-col items-center justify-center max-w-md text-center px-4">
          <div class="animate-spin rounded-full h-16 w-16 border-4 border-gray-300 border-t-[#528359]"></div>
          <p class="mt-4 text-gray-600">Modification en cours...</p>
        </div>

        <div v-else-if="showError" class="flex flex-col items-center justify-center max-w-md text-center px-4">
          <div class="rounded-full bg-red-100 w-16 h-16 flex items-center justify-center">
            <span class="material-symbols-outlined text-4xl text-red-600">error</span>
          </div>
          <h3 class="text-2xl font-bold text-gray-800 mt-6">Erreur</h3>
          <div class="space-y-3 text-gray-600 mt-4">
            <p>Une erreur est survenue lors du changement de mot de passe.</p>
            <button @click="resetForm" class="text-red-600 hover:text-red-800 underline">
              Réessayer
            </button>
          </div>
        </div>

        <div v-else class="flex flex-col items-center justify-center max-w-md text-center px-4">
          <div class="rounded-full bg-green-100 w-16 h-16 flex items-center justify-center">
            <span class="material-symbols-outlined text-4xl text-green-600">check_circle</span>
          </div>
          <h3 class="text-2xl font-bold text-gray-800 mt-6">Mot de passe modifié !</h3>
          <div class="space-y-3 text-gray-600 mt-4">
            <p>Votre mot de passe a été changé avec succès.</p>
            <router-link to="/" class="text-[#528359] hover:underline">
              Retour à la page de connexion
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import trainersService from "../../services/TrainersService.js";
import LogoSection from "./LogoSection.vue";

export default {
  name: 'ChangePassword',
  components: {
    LogoSection
  },
  data() {
    return {
      password: '',
      confirmPassword: '',
      passwordVisible: false,
      confirmPasswordVisible: false,
      showForm: true,
      showError: false,
      isLoading: false,
      token: ''
    };
  },
  created() {
    const urlParams = new URLSearchParams(window.location.search);
    this.token = urlParams.get('token');
    if (!this.token) {
      this.$router.push('/');
    }
  },
  methods: {
    togglePasswordVisibility(fieldId) {
      if (fieldId === 'password-field') {
        this.passwordVisible = !this.passwordVisible;
        const passwordField = document.getElementById(fieldId);
        passwordField.type = this.passwordVisible ? 'text' : 'password';
      } else {
        this.confirmPasswordVisible = !this.confirmPasswordVisible;
        const confirmPasswordField = document.getElementById(fieldId);
        confirmPasswordField.type = this.confirmPasswordVisible ? 'text' : 'password';
      }
    },
    async handleSubmit() {
      if (!this.password || !this.confirmPassword) {
        alert('Veuillez remplir tous les champs');
        return;
      }

      if (this.password !== this.confirmPassword) {
        alert('Les mots de passe ne correspondent pas');
        return;
      }

      this.showForm = false;
      this.isLoading = true;

      try {
        await trainersService.changePassword({
          token: this.token,
          password: this.password,
          confirmPassword: this.confirmPassword
        });
        this.isLoading = false;
      } catch (error) {
        this.isLoading = false;
        this.showError = true;
        console.error('Erreur lors du changement de mot de passe:', error);
      }
    },
    resetForm() {
      this.showForm = true;
      this.showError = false;
      this.password = '';
      this.confirmPassword = '';
      this.passwordVisible = false;
      this.confirmPasswordVisible = false;
    }
  }
};
</script>