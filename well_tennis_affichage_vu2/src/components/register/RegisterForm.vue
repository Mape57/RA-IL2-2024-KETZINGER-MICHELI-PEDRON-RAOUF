<template>
  <div class="flex flex-col justify-center items-center w-full p-6 lg:w-2/3 bg-white">
    <h2 class="text-2xl font-semibold text-gray-700 mb-4">Inscription</h2>
    <form @submit.prevent="validateAndSubmit" class="w-full max-w-md space-y-4">
      <!-- Champ Nom -->
      <div>
        <label for="nom" class="block text-sm font-medium text-gray-600">Nom</label>
        <input v-model="form.nom" id="nom" type="text" required class="input-field" placeholder="Entrez votre nom" />
      </div>

      <!-- Champ Prénom -->
      <div>
        <label for="prenom" class="block text-sm font-medium text-gray-600">Prénom</label>
        <input v-model="form.prenom" id="prenom" type="text" required class="input-field" placeholder="Entrez votre prénom" />
      </div>

      <!-- Champ Date de naissance -->
      <div>
        <label for="age" class="block text-sm font-medium text-gray-600">Date de naissance</label>
        <input v-model="form.age" id="age" type="date" required class="input-field" />
      </div>

      <!-- Champ Nombre de cours -->
      <div>
        <label for="cours" class="block text-sm font-medium text-gray-600">Nombre de cours (max 3)</label>
        <input v-model="form.cours" id="cours" type="number" required min="1" max="3" class="input-field" />
      </div>

      <!-- Champ Disponibilité -->
      <div>
        <label for="disponibilite" class="block text-sm font-medium text-gray-600">Disponibilité</label>
        <div class="availability-box">
          <div class="flex space-x-2 mb-2">
            <select v-model="newDisponibilite.jour" class="availability-input">
              <option disabled value="">Choisissez un jour</option>
              <option v-for="(num, jour) in dayMapping" :key="num" :value="jour">{{ jour }}</option>
            </select>
            <input type="time" v-model="newDisponibilite.debut" class="availability-input" />
            <input type="time" v-model="newDisponibilite.fin" class="availability-input" />
          </div>
          <button type="button" @click="addDisponibilite" class="add-button">Ajouter</button>
          <ul class="mt-2">
            <li v-for="(dispo, index) in form.disponibilites" :key="index" class="dispo-item">
              <span>{{ dispo.jour }}: {{ dispo.open }} - {{ dispo.close }}</span>
              <button type="button" @click="removeDisponibilite(index)" class="delete-button">Supprimer</button>
            </li>
          </ul>
        </div>
      </div>

      <!-- Champ Email -->
      <div>
        <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
        <input v-model="form.email" id="email" type="email" required class="input-field" placeholder="Entrez votre email" />
      </div>

      <!-- Bouton Inscription -->
      <button type="submit" class="submit-button">S'inscrire</button>
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
    };

    const isValidTimeFormat = (time) => {
      const [hours, minutes] = time.split(":").map(Number);
      return minutes === 0 || minutes === 30;
    };

    const addDisponibilite = () => {
      const { jour, debut, fin } = newDisponibilite.value;

      if (!jour || !debut || !fin) {
        return alert("Remplissez tous les champs !");
      }

      if (debut >= fin) {
        return alert("L'heure de début doit être avant l'heure de fin.");
      }

      if (debut < "08:00" || fin > "23:30") {
        return alert("Les disponibilités doivent être comprises entre 08:00 et 23:30.");
      }

      if (!isValidTimeFormat(debut) || !isValidTimeFormat(fin)) {
        return alert("Les heures doivent être sous le format HH:00 ou HH:30.");
      }

      const debutMinutes = parseInt(debut.split(":")[0]) * 60 + parseInt(debut.split(":")[1]);
      const finMinutes = parseInt(fin.split(":")[0]) * 60 + parseInt(fin.split(":")[1]);

      if (finMinutes - debutMinutes < 30) {
        return alert("Les disponibilités doivent être d'au moins 30 minutes.");
      }

      const isOverlap = form.value.disponibilites.some((dispo) => {
        if (dispo.jour === jour) {
          const dispoDebutMinutes = parseInt(dispo.open.split(":")[0]) * 60 + parseInt(dispo.open.split(":")[1]);
          const dispoFinMinutes = parseInt(dispo.close.split(":")[0]) * 60 + parseInt(dispo.close.split(":")[1]);
          return !(finMinutes <= dispoDebutMinutes || debutMinutes >= dispoFinMinutes);
        }
        return false;
      });

      if (isOverlap) {
        return alert("Les créneaux ne doivent pas se chevaucher.");
      }

      form.value.disponibilites.push({
        jour,
        dayWeek: dayMapping[jour],
        open: debut,
        close: fin,
      });

      newDisponibilite.value = { jour: "", debut: "", fin: "" };
    };

    const removeDisponibilite = (index) => {
      form.value.disponibilites.splice(index, 1);
    };

    const validateAndSubmit = async () => {
      if (!form.value.nom || !form.value.prenom || !form.value.age || !form.value.email || form.value.disponibilites.length === 0) {
        return alert("Veuillez remplir tous les champs !");
      }

      const birthYear = new Date(form.value.age).getFullYear();
      const currentYear = new Date().getFullYear();
      if (birthYear < 1900 || birthYear > currentYear) {
        return alert("L'année de naissance doit être comprise entre 1900 et " + currentYear + ".");
      }

      const inscriptionData = {
        name: form.value.nom,
        surname: form.value.prenom,
        birthday: form.value.age,
        courses: parseInt(form.value.cours),
        level: 0,
        email: form.value.email,
        validate: false,
        disponibilities: form.value.disponibilites.map(d => ({
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
    };
  },
};
</script>



<style scoped>
.input-field {
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 8px;
  margin-top: 4px;
  focus:outline-none;
  focus:ring-2;
  focus:ring-blue-500;
}

.availability-box {
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 10px;
  background-color: #f9f9f9;
}

.availability-input {
  width: 30%;
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.add-button {
  background-color: #528359;
  color: white;
  font-weight: bold;
  padding: 6px 12px;
  border-radius: 6px;
  transition: 0.3s;
}

.add-button:hover {
  background-color: #3b6345;
}

.delete-button {
  color: red;
  cursor: pointer;
  transition: 0.2s;
}

.delete-button:hover {
  color: darkred;
}

.submit-button {
  width: 100%;
  background-color: #528359;
  color: white;
  font-weight: bold;
  padding: 10px;
  border-radius: 8px;
  transition: 0.3s;
}

.submit-button:hover {
  background-color: #3b6345;
}
</style>
