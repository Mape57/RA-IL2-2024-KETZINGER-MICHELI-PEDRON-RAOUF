<template>
  <div class="w-full bg-white flex justify-center items-center shadow-md p-4 lg:p-0 lg:w-[760px] h-auto lg:h-[510px]">
    <div class="text-center w-full lg:w-auto">
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
  </div>
</template>

<script>
export default {
  name: 'ForgotPasswordForm',
  data() {
    return {
      mailoruser: '',
    };
  },
  methods: {
    handleSubmit() {
      console.log(`Email: ${this.mailoruser}`);
      this.$router.push('/');
    },
  },
};
</script>
