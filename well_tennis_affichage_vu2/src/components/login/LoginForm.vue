<template>
  <div class="w-full bg-white flex justify-center items-center shadow-md p-4 lg:p-0 lg:w-[760px] h-auto lg:h-[510px]">
    <div class="text-center w-full lg:w-auto">
      <h1 class="text-[#1A4220] text-xl lg:text-2xl font-bold mb-4 lg:mb-6">Well Tennis Club</h1>
      <form class="space-y-3" @submit.prevent="Connectlogin">
        <input
            type="text"
            placeholder="Identifiant"
            class="w-full lg:w-[380px] h-[40px] border border-gray-300 rounded-lg p-2 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
            v-model="email"
        />
        <div class="relative w-full lg:w-[380px] mx-auto">
          <input
              id="password-field"
              type="password"
              placeholder="Mot de passe"
              class="w-full h-[40px] border border-gray-300 rounded-lg p-2 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
              v-model="password"
          />
          <span
              @click="togglePasswordVisibility"
              class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 cursor-pointer material-symbols-outlined"
          >
            {{ passwordVisible ? 'visibility_off' : 'visibility' }}
          </span>
        </div>

        <router-link
            class="text-[#528359] text-sm underline block text-left w-full lg:w-[380px] mx-auto"
            to="/forgot-password"
        >
          Mot de passe oublié ?
        </router-link>

        <!-- Bouton Se connecter -->
        <button
            class="w-full lg:w-[380px] h-[60px] bg-[#528359] text-white text-base rounded-lg shadow-md py-3 hover:bg-[#456c4c] transition mx-auto flex justify-center items-center"
        >
          Se connecter
        </button>

        <!-- Bouton S'inscrire -->
        <router-link
            class="w-full lg:w-[380px] h-[60px] bg-white text-[#528359] border border-[#528359] text-base rounded-lg shadow-md py-3 hover:bg-[#ebf5eb] transition mx-auto flex justify-center items-center"
            to="/register"
        >
          S'inscrire
        </router-link>
      </form>
    </div>
  </div>
</template>

<script>
import {accountService} from "../../services/authService";

export default {
  name: 'LoginForm',
  data() {
    return {
      email: '',
      password: '',
      passwordVisible: false,
    };
  },
  methods: {
    togglePasswordVisibility() {
      this.passwordVisible = !this.passwordVisible;
      const passwordField = document.getElementById('password-field');
      passwordField.type = this.passwordVisible ? 'text' : 'password';
    },


    async Connectlogin() {
      sessionStorage.removeItem("token"); // Supprime tout ancien token au chargement

      if (!this.email || !this.password) {
        console.error("Veuillez entrer un email et un mot de passe.");
        return;
      }

      try {
        const response = await accountService.login(this.email, this.password);
        console.log("Connexion réussie :", response.data);
        accountService.saveToken(response.data.token);
        this.$router.push('/admin'); // Redirection après connexion
      } catch (error) {
        console.error("Erreur lors de la connexion :", error.response?.data || error.message);
      }
    },
  },
};
</script>
