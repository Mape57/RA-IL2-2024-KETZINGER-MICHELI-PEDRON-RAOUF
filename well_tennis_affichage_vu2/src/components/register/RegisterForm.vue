<template>
  <div class="flex flex-col justify-center items-center w-full p-6 lg:w-2/3 bg-white">
    <h2 class="text-2xl font-semibold text-gray-700 mb-4">Inscription</h2>
    <form @submit.prevent="validateAndSubmit" class="w-full max-w-md space-y-4">
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

      <!-- Champ Date de naissance -->
      <div>
        <label for="age" class="block text-sm font-medium text-gray-600">Date de naissance</label>
        <input
            v-model="form.age"
            id="age"
            type="date"
            required
            class="mt-1 w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            placeholder="Entrez votre date de naissance"
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
              <span>{{ getJourLabel(dispo.dayWeek) }}: {{ dispo.open }} - {{ dispo.close }}</span>
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
import { ref } from "vue";
import useInscription from "../../useJs/useInscription";

export default {
  name: "RegisterForm",
  setup() {


    const { createInscription } = useInscription();

    const form = ref({
      nom: "",
      prenom: "",
      age: "",
      cours: "",
      disponibilites: [],
      email: "",
    });

    const newDisponibilite = ref({
      jour: "",
      debut: "",
      fin: "",
    });

    const dayMapping = {
      "Lundi": 1,
      "Mardi": 2,
      "Mercredi": 3,
      "Jeudi": 4,
      "Vendredi": 5,
      "Samedi": 6,
      "Dimanche": 7,
    };

    const addDisponibilite = () => {
      const { jour, debut, fin } = newDisponibilite.value;
      if (!jour || !debut || !fin) return alert("Remplissez tous les champs !");
      if (debut >= fin) return alert("L'heure de début doit être avant l'heure de fin.");

      form.value.disponibilites = [
        ...form.value.disponibilites,
        {
          dayWeek: dayMapping[jour],
          open: debut,
          close: fin,
        }
      ];

      newDisponibilite.value = { jour: "", debut: "", fin: "" };
    };

    const getJourLabel = (dayWeek) => {
      const jourInverse = Object.entries(dayMapping).find(([key, value]) => value === dayWeek);
      return jourInverse ? jourInverse[0] : "Inconnu";
    };

    const removeDisponibilite = (index) => {
      form.value.disponibilites.splice(index, 1);
    };

    const validateAndSubmit = async () => {
      if (!form.value.nom || !form.value.prenom || !form.value.age || !form.value.email || form.value.disponibilites.length === 0) {
        return alert("Veuillez remplir tous les champs !");
      }

      const inscriptionData = {
        name: form.value.nom,
        surname: form.value.prenom,
        birthday: form.value.age,
        courses: parseInt(form.value.cours),
        level: 0,
        email: form.value.email,
        validate: false,
        disponibilities: form.value.disponibilites
            .filter(d => d.dayWeek && d.open && d.close)
            .map(d => ({
              dayWeek: d.dayWeek,
              open: d.open,
              close: d.close,
            })),
      };

      try {
        await createInscription(inscriptionData);
        alert("Inscription réussie !");
      } catch (error) {
        alert("Erreur lors de l'inscription !");
        console.error(error);
      }
    };

    return {
      form,
      newDisponibilite,
      dayMapping,
      addDisponibilite,
      removeDisponibilite,
      validateAndSubmit,
      getJourLabel,
    };
  },
};
</script>


