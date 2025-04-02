<template>
  <div class="w-full bg-white flex justify-center items-center shadow-md p-4 lg:p-0 lg:w-[760px] h-auto lg:h-[710px]">
    <div v-if="showForm" class="text-center w-full lg:w-auto">
      <h1 class="text-[#1A4220] text-xl lg:text-2xl font-bold mb-4 lg:mb-6">Well Tennis Club</h1>
      <p class="text-gray-600 text-sm lg:text-base mb-4">
        Entrez votre adresse e-mail pour recevoir un lien de réinitialisation de mot de passe.
      </p>
      <form class="space-y-3" @submit.prevent="handleSubmit">
        <input
            type="email"
            placeholder="Adresse e-mail"
            class="w-full lg:w-[380px] h-[50px] border border-gray-300 rounded-lg px-4 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
            v-model="mailoruser"
            required
        />
        <button
            type="submit"
            class="w-full lg:w-[380px] h-[50px] bg-[#528359] text-white text-base rounded-lg shadow-md py-3 hover:bg-[#456c4c] transition mx-auto"
        >
          Envoyer un mail de récupération
        </button>
      </form>
      <router-link
          to="/"
          class="block mt-4 text-sm text-[#528359] hover:underline"
      >
        Retour à la page d'accueil
      </router-link>
    </div>

    <div v-else-if="isLoading" class="flex flex-col items-center justify-center max-w-md text-center px-4">
      <div class="animate-spin rounded-full h-16 w-16 border-4 border-gray-300 border-t-[#528359]"></div>
      <p class="mt-4 text-gray-600">Envoi en cours...</p>
    </div>

    <div v-else-if="showError" class="flex flex-col items-center justify-center max-w-md text-center px-4">
      <div class="rounded-full bg-red-100 w-16 h-16 flex items-center justify-center">
        <span class="material-symbols-outlined text-4xl text-red-600">error</span>
      </div>
      <h3 class="text-2xl font-bold text-gray-800 mt-6">Erreur</h3>
      <div class="space-y-3 text-gray-600 mt-4">
        <p>Une erreur est survenue lors de l'envoi. Veuillez réessayer.</p>
        <button @click="resetForm" class="text-red-600 hover:text-red-800 underline">
          Réessayer
        </button>
      </div>
    </div>

    <div v-else class="flex flex-col items-center justify-center max-w-md text-center px-4">
      <div class="rounded-full bg-green-100 w-16 h-16 flex items-center justify-center">
        <span class="material-symbols-outlined text-4xl text-green-600">check_circle</span>
      </div>
      <h3 class="text-2xl font-bold text-gray-800 mt-6">Email envoyé !</h3>
      <div class="space-y-3 text-gray-600 mt-4">
        <p>Un email de réinitialisation a été envoyé à votre adresse.</p>
        <p class="text-sm italic">Veuillez vérifier votre boîte de réception.</p>
        <router-link to="/" class="text-[#528359] hover:underline">
          Retour à la page d'accueil
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import trainersService from "../../services/TrainersService.js";

export default {
  name: 'ForgotPasswordForm',
  data() {
    return {
      mailoruser: '',
      showForm: true,
      showError: false,
      isLoading: false
    };
  },
  methods: {
    async handleSubmit() {
      this.showForm = false;
      this.isLoading = true;

      try {
        await trainersService.resetPassword(this.mailoruser);
        this.isLoading = false;
      } catch (error) {
        this.isLoading = false;
        this.showError = true;
        console.error('Erreur lors de la réinitialisation:', error);
      }
    },
    resetForm() {
      this.showForm = true;
      this.showError = false;
      this.mailoruser = '';
    }
  }
};
</script>