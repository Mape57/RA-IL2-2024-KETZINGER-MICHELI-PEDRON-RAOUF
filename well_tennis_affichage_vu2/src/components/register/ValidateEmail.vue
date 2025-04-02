<template>
  <div class="flex flex-col items-center justify-center min-h-screen bg-[#fefdf8] p-4">
    <div class="bg-white shadow-md rounded-lg p-8 max-w-md w-full text-center">
      <h1 class="text-[#1A4220] text-2xl lg:text-3xl font-bold mb-6">Validation de l'inscription</h1>

      <div v-if="error" class="mb-4 text-red-600">
        {{ error }}
      </div>

      <div v-if="success" class="mb-4 text-green-600">
        Votre compte a été validé avec succès!<br>
        Vous pouvez maintenant quitter cette page.
      </div>

      <button
          @click="validateInscription"
          class="w-full h-[50px] bg-[#528359] text-white text-base rounded-lg shadow-md py-3 hover:bg-[#456c4c] transition"
          :disabled="loading"
      >
        {{ loading ? 'Validation en cours...' : 'Valider mon inscription' }}
      </button>
    </div>
  </div>
</template>

<script>
import inscriptionService from '../../services/InscriptionService';

export default {
  name: 'ValidateEmail',
  data() {
    return {
      token: null,
      playerData: null,
      loading: false,
      error: null,
      success: false
    };
  },
  created() {
    // Get token and player data from URL query parameters
    const params = new URLSearchParams(window.location.search);
    this.token = params.get('token');

    try {
      // Decode and parse the player data
      const encodedPlayer = params.get('player');
      if (encodedPlayer) {
        this.playerData = JSON.parse(decodeURIComponent(encodedPlayer));
      }
    } catch (e) {
      this.error = "Données de joueur invalides";
    }

    if (!this.token || !this.playerData) {
      this.error = "Paramètres de validation manquants";
    }
  },
  methods: {
    async validateInscription() {
      if (this.loading || this.success) return;
      this.loading = true;
      this.error = null;


      await inscriptionService.validateEmail(this.token, this.playerData)
          .then(response => {
            this.success = true;
          })
          .catch(e => {
            this.error = "Erreur lors de la validation de l'inscription. Veuillez réessayer.";
            console.error("Erreur de validation:" + e);
          });
      this.loading = false;
    }
  }
  }
</script>

<style scoped>
button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>