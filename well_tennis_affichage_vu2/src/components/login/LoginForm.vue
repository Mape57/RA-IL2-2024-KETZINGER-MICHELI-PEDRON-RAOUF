<template>
  <div class="w-full bg-white flex justify-center items-center shadow-md p-4 lg:p-0 lg:w-[760px] h-auto lg:h-[510px]">
    <div class="text-center w-full lg:w-auto">
      <h1 class="text-[#1A4220] text-2xl lg:text-4xl font-bold mb-6 lg:mb-8">Well Tennis Club</h1>
      <form class="space-y-3" @submit.prevent="Connectlogin">
        <input
            type="text"
            placeholder="Identifiant"
            class="w-full lg:w-[380px] h-[50px] rounded-lg p-3 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
            v-model="email"
        />
        <div class="relative w-full lg:w-[380px] mx-auto">
          <input
              id="password-field"
              type="password"
              placeholder="Mot de passe"
              class="w-full lg:w-[380px] h-[50px] rounded-lg p-3 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
              v-model="password"
          />
          <span
              @click="togglePasswordVisibility"
              class="absolute right-3 top-1/2 -translate-y-1/2 text-[#528359] cursor-pointer material-symbols-outlined"
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
            class="w-full lg:w-[380px] h-[50px] bg-[#528359] text-white text-base rounded-lg shadow-md py-3 hover:bg-[#456c4c] transition mx-auto flex justify-center items-center"
        >
          Se connecter
        </button>

        <!-- Bouton S'inscrire -->
        <router-link
            class="w-full lg:w-[380px] h-[50px] bg-white text-[#528359] border border-[#528359] text-base rounded-lg shadow-md py-3 hover:bg-[#ebf5eb] transition mx-auto flex justify-center items-center"
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
        if (response.token && response.role) {
         accountService.saveToken(response.token);
          sessionStorage.setItem("role", response.role);
           const userRole = response.role.toUpperCase();
         // this.$router.push('/admin'); // Redirection après connexion
          if (userRole === "TRAINER") {
            this.$router.push('/trainer');
          } else if (userRole === "ROLE_ADMIN") {
            this.$router.push('/admin');
          } else {
            this.$router.push('/');
          }
        }
        else {
          console.error("Réponse invalide de l'API. Vérifiez votre backend.");
        }
      }catch (error) {
        console.error("Erreur lors de la connexion :", error.response?.data || error.message);
      }
    },
  },
};
</script>
