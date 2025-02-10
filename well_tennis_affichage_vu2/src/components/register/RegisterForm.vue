<template>
  <div class="flex flex-col justify-center items-center w-full p-6 lg:w-2/3 bg-white">
    <h2 class="text-2xl font-semibold text-gray-700 mb-4">Inscription</h2>
    <form @submit.prevent="validateAndRedirect" class="w-full max-w-md space-y-4">
      <!-- Champ Nom -->
      <div>
        <label for="nom" class="block text-sm font-medium text-gray-600">Nom</label>
        <input
            v-model="form.nom"
            id="nom"
            type="text"
            required
            class="mt-1 w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Entrez votre nom"
        />
      </div>

      <!-- Champ Prénom -->
      <div>
        <label for="prenom" class="block text-sm font-medium text-gray-600">Prénom</label>
        <input
            v-model="form.prenom"
            id="prenom"
            type="text"
            required
            class="mt-1 w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Entrez votre prénom"
        />
      </div>

      <!-- Champ Âge -->
      <div>
        <label for="age" class="block text-sm font-medium text-gray-600">Âge</label>
        <input
            v-model="form.age"
            id="age"
            type="number"
            required
            min="1"
            max="120"
            class="mt-1 w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Entrez votre âge"
        />
      </div>

      <!-- Champ Nombre de cours -->
      <div>
        <label for="cours" class="block text-sm font-medium text-gray-600">Nombre de cours (max 3)</label>
        <input
            v-model="form.cours"
            id="cours"
            type="number"
            required
            min="1"
            max="3"
            class="mt-1 w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Entrez le nombre de cours"
        />
      </div>

      <!-- Champ Disponibilité -->
      <div>
        <label for="disponibilite" class="block text-sm font-medium text-gray-600">Disponibilité</label>
        <div class="mt-1 w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <div class="flex space-x-2 mb-2">
            <select
                v-model="newDisponibilite.jour"
                class="w-1/2 p-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option disabled value="">Choisissez un jour</option>
              <option value="Lundi">Lundi</option>
              <option value="Mardi">Mardi</option>
              <option value="Mercredi">Mercredi</option>
              <option value="Jeudi">Jeudi</option>
              <option value="Vendredi">Vendredi</option>
              <option value="Samedi">Samedi</option>
            </select>
            <input
                type="time"
                v-model="newDisponibilite.debut"
                class="w-1/4 p-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Début"
            />
            <input
                type="time"
                v-model="newDisponibilite.fin"
                class="w-1/4 p-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Fin"
            />
          </div>
          <button
              type="button"
              @click="addDisponibilite"
              class="bg-[#528359] text-white font-semibold py-1 px-2 rounded-lg hover:bg-green-600 transition-colors"
          >
            Ajouter
          </button>
          <ul class="mt-2">
            <li v-for="(dispo, index) in form.disponibilites" :key="index" class="flex justify-between items-center">
              <span>{{ dispo.jour }}: {{ dispo.debut }} - {{ dispo.fin }}</span>
              <button
                  type="button"
                  @click="removeDisponibilite(index)"
                  class="text-red-500 hover:text-red-700"
              >
                Supprimer
              </button>
            </li>
          </ul>
        </div>
      </div>

      <!-- Champ Email -->
      <div>
        <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
        <input
            v-model="form.email"
            id="email"
            type="email"
            required
            class="mt-1 w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Entrez votre email"
        />
      </div>

      <!-- Bouton Inscription -->
      <button
          type="submit"
          class="w-full bg-[#528359] text-white font-semibold py-2 rounded-lg hover:bg-green-600 transition-colors"
      >
        S'inscrire
      </button>
    </form>
  </div>
</template>

<script>
import usePlayers from "../../useJs/usePlayers.js";

export default {
  name: "RegisterForm",
  data() {
    return {
      form: {
        nom: "",
        prenom: "",
        age: "",
        cours: "",
        disponibilites: [],
        email: "",
      },
      newDisponibilite: {
        jour: "",
        debut: "",
        fin: "",
      },
    };
  },
  setup() {
    const { createPlayer } = usePlayers();
    return { createPlayer };
  },
  methods: {
    addDisponibilite() {
      const { jour, debut, fin } = this.newDisponibilite;
      if (jour && debut && fin) {
        if (debut >= fin) {
          alert("L'heure de début doit être inférieure à l'heure de fin.");
          return;
        }
        const exists = this.form.disponibilites.some(
            (dispo) => dispo.jour === jour && dispo.debut === debut && dispo.fin === fin
        );
        if (exists) {
          alert("Cette disponibilité existe déjà.");
          return;
        }
        this.form.disponibilites.push({ jour, debut, fin });
        this.newDisponibilite = { jour: "", debut: "", fin: "" };
      } else {
        alert("Veuillez remplir tous les champs de disponibilité avant d'ajouter.");
      }
    },
    removeDisponibilite(index) {
      this.form.disponibilites.splice(index, 1);
    },
    async validateAndRedirect() {
      if (
          !this.form.nom ||
          !this.form.prenom ||
          !this.form.age ||
          !this.form.cours ||
          this.form.disponibilites.length === 0 ||
          !this.form.email
      ) {
        alert("Veuillez remplir tous les champs correctement !");
        return;
      }
      try {
        await this.createPlayer(this.form);
        alert("Inscription réussie !");
        this.$router.push("/");
      } catch (error) {
        alert("Erreur lors de l'inscription !");
        console.error(error);
      }
    },
  },
};
</script>