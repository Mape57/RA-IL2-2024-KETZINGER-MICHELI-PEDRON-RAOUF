<template>
  <div
      class="flex flex-col justify-start items-center w-full p-6 lg:w-2/3 bg-white h-[calc(100vh-2rem)] lg:h-[710px] overflow-y-auto">
    <h2 v-if="showForm" class="text-2xl font-semibold text-gray-700 mb-4">Inscription</h2>
    <form v-if="showForm" @submit.prevent="validateAndSubmit" class="w-full max-w-md space-y-4">
      <!-- Champ Nom -->
      <div>
        <label for="nom" class="block text-sm font-medium text-gray-600">Nom</label>
        <input v-model="form.nom" id="nom" type="text" required class="input-field" placeholder="Entrez votre nom"/>
      </div>

      <!-- Champ Prénom -->
      <div>
        <label for="prenom" class="block text-sm font-medium text-gray-600">Prénom</label>
        <input v-model="form.prenom" id="prenom" type="text" required class="input-field"
               placeholder="Entrez votre prénom"/>
      </div>

      <!-- Champ Date de naissance -->
      <div>
        <label for="age" class="block text-sm font-medium text-gray-600">Date de naissance</label>
        <input v-model="form.age" id="age" type="date" required class="input-field"/>
      </div>

      <!-- Champ Nombre de cours -->
      <div>
        <label for="cours" class="block text-sm font-medium text-gray-600">Nombre de cours (max 3)</label>
        <input v-model="form.cours" id="cours" type="number" required min="1" max="3" class="input-field"/>
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
            <select v-model="newDisponibilite.debut" class="availability-input">
              <option disabled value="">Heure début</option>
              <option v-for="hour in generateTimeSlots()" :key="hour" :value="hour">
                {{ hour }}
              </option>
            </select>
            <select v-model="newDisponibilite.fin" class="availability-input">
              <option disabled value="">Heure début</option>
              <option v-for="hour in generateTimeSlots()" :key="hour" :value="hour">
                {{ hour }}
              </option>
            </select>
          </div>
          <button type="button" @click="addDisponibilite" class="add-button">Valider la disponibilité</button>
          <ul class="mt-2">
            <li v-for="(dispo, index) in form.disponibilites" :key="index" class="dispo-item">
              <span>{{ dispo.jour }}: {{ dispo.open }} - {{ dispo.close }}</span>
              <button type="button" @click="removeDisponibilite(index)" class="delete-button" style="padding-left: .5rem">Supprimer</button>
            </li>
          </ul>
        </div>
      </div>

      <!-- Champ Email -->
      <div>
        <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
        <input v-model="form.email" id="email" type="email" required class="input-field"
               placeholder="Entrez votre email"/>
      </div>

      <div>
        <label for="confirmEmail" class="block text-sm font-medium text-gray-600">Confirmer l'email</label>
        <input
            v-model="form.confirmEmail"
            id="confirmEmail"
            type="email"
            required
            class="input-field"
            placeholder="Confirmez votre email"
            @paste.prevent
            @copy.prevent
            @cut.prevent
        />
      </div>


      <!-- Champ Numéro de téléphone avec préfixe -->
      <div>
        <label for="numero" class="block text-sm font-medium text-gray-600">Numéro de téléphone</label>
        <div class="flex space-x-2">
          <select v-model="form.prefixe" class="w-1/3 border border-gray-300 rounded-md p-2">
            <option value="+33">+33 (FR)</option>
            <!-- Ajoute d'autres préfixes ici si nécessaire -->
          </select>
          <input
              v-model="form.numero"
              id="numero"
              type="tel"
              required
              class="w-2/3 border border-gray-300 rounded-md p-2"
              placeholder="6 12 34 56 78"
          />
        </div>
      </div>

      <div>
        <label for="numero2" class="block text-sm font-medium text-gray-600">Numéro de téléphone 2</label>
        <div class="flex space-x-2">
          <select v-model="form.prefixe" class="w-1/3 border border-gray-300 rounded-md p-2">
            <option value="+33">+33 (FR)</option>
            <!-- Ajoute d'autres préfixes ici si nécessaire -->
          </select>
          <input
              v-model="form.numero2"
              id="numero2"
              type="tel"
              required
              class="w-2/3 border border-gray-300 rounded-md p-2"
              placeholder="6 12 34 56 78"
          />
        </div>
      </div>


      <!-- Autorisation photo -->
      <div class="text-sm text-gray-600">
        <p class="mb-2">
          Autorisez-vous la prise de photos ?<br>
          <span class="italic text-xs text-gray-500">*Pour les parents, cela concerne les photos de leur(s) enfant(s)</span>
        </p>
        <div class="flex space-x-4 items-center">
          <div class="flex items-center">
            <input
                type="radio"
                id="photoYes"
                value="yes"
                v-model="form.acceptPhoto"
                class="mr-1"
            />
            <label for="photoYes">Oui</label>
          </div>
          <div class="flex items-center">
            <input
                type="radio"
                id="photoNo"
                value="no"
                v-model="form.acceptPhoto"
                class="mr-1"
            />
            <label for="photoNo">Non</label>
          </div>
        </div>
      </div>



      <div class="flex items-center">
        <input v-model="form.acceptTerms" id="acceptTerms" type="checkbox" class="mr-2"/>
        <label for="acceptTerms" class="text-sm text-gray-600">J'accepte l'utilisation de mes données</label>
      </div>

      <!-- Bouton Inscription -->
      <button type="submit" class="submit-button">S'inscrire</button>
    </form>
    <div v-else-if="isLoading" class="flex flex-col items-center justify-center h-[510px] max-w-md text-center px-4 -mt-4">
      <div class="animate-spin rounded-full h-16 w-16 border-4 border-gray-300 border-t-[#528359]"></div>
      <p class="mt-4 text-gray-600">Inscription en cours...</p>
    </div>
    <div v-else-if="showError" class="flex flex-col items-center justify-center h-[510px] max-w-md text-center px-4 -mt-4">
      <div class="rounded-full bg-red-100 w-16 h-16 flex items-center justify-center">
        <span class="material-symbols-outlined text-4xl text-red-600">error</span>
      </div>
      <h3 class="text-2xl font-bold text-gray-800 mt-6">Erreur</h3>
      <div class="space-y-3 text-gray-600 mt-4">
        <p>Une erreur est survenue lors de l'inscription. Veuillez réessayer. Si le problème persiste, contactez-nous.</p>
        <button @click="showForm = true; showError = false" class="text-red-600 hover:text-red-800 underline">
          Réessayer
        </button>
      </div>
    </div>
    <div v-else class="flex flex-col items-center justify-center h-[510px] max-w-md text-center px-4 -mt-4">
      <div class="rounded-full bg-green-100 w-16 h-16 flex items-center justify-center">
        <span class="material-symbols-outlined text-4xl text-green-600">check_circle</span>
      </div>
      <h3 class="text-2xl font-bold text-gray-800 mt-6">Inscription enregistrée !</h3>
      <div class="space-y-3 text-gray-600 mt-4">
        <p>Pour valider votre inscription, veuillez consulter votre boîte mail.</p>
        <p class="text-sm italic">Vous pouvez désormais quitter cette page.</p>
      </div>
    </div>
  </div>
</template>

<script>
import {ref} from "vue";
import useInscription from "../../useJs/useInscription";

export default {
  name: "RegisterForm",
  setup() {
    const {createInscription} = useInscription();

    const form = ref({
      nom: "",
      prenom: "",
      age: "",
      cours: "",
      disponibilites: [],
      email: "",
      acceptTerms: false,
      acceptPhoto: "",
      confirmEmail: "",
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

    const generateTimeSlots = () => {
      const slots = [];
      for (let hour = 8; hour <= 23; hour++) {
        const formattedHour = hour.toString().padStart(2, '0');
        slots.push(`${formattedHour}:00`);
        if (hour !== 23) {
          slots.push(`${formattedHour}:30`);
        }
      }
      return slots;
    };

    const addDisponibilite = () => {
      const {jour, debut, fin} = newDisponibilite.value;

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

      newDisponibilite.value = {jour: "", debut: "", fin: ""};
    };

    const removeDisponibilite = (index) => {
      form.value.disponibilites.splice(index, 1);
    };

    const showForm = ref(true);
    const showError = ref(false);
    const isLoading = ref(false);

    const validateAndSubmit = async () => {
      const { nom, prenom, age, email, confirmEmail, numero, numero2, prefixe, acceptPhoto, acceptTerms, disponibilites, cours } = form.value;

      // Champs obligatoires
      if (!nom || !prenom || !age || !email || !confirmEmail || !numero || !numero2 || disponibilites.length === 0) {
        return alert("Veuillez remplir tous les champs obligatoires.");
      }

      // Numéro de téléphone FR mobile (06 ou 07)
      const cleanedNumber = numero.replace(/\D/g, ''); // enlève espaces et tirets
      if (!/^[67]\d{8}$/.test(cleanedNumber)) {
        return alert("Le numéro doit commencer par 6 ou 7 et contenir exactement 9 chiffres.");
      }
      if (cleanedNumber.length !== 9) {
        return alert("Le numéro de téléphone doit contenir exactement 9 chiffres.");
      }
      const fullNumber = prefixe + cleanedNumber;

      // Email format
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
        return alert("Adresse email invalide.");
      }

      // Confirmation email
      if (email !== confirmEmail) {
        return alert("Les emails ne correspondent pas.");
      }

      // Date de naissance cohérente
      const birthYear = new Date(age).getFullYear();
      const currentYear = new Date().getFullYear();
      if (birthYear < 1900 || birthYear > currentYear) {
        return alert(`L'année de naissance doit être comprise entre 1900 et ${currentYear}.`);
      }

      // Consentement photo (obligatoire à remplir, mais peut être oui ou non)
      if (acceptPhoto !== "yes" && acceptPhoto !== "no") {
        return alert("Veuillez répondre à la question concernant la prise de photos.");
      }

      // Conditions d'utilisation
      if (!acceptTerms) {
        return alert("Vous devez accepter l'utilisation de vos données.");
      }

      // Nombre de cours cohérent
      if (disponibilites.length < cours) {
        return alert("Le nombre de disponibilités doit être au moins égal au nombre de cours.");
      }

      // Toutes les dispos ≥ 1h
      for (const dispo of disponibilites) {
        const [startH, startM] = dispo.open.split(":").map(Number);
        const [endH, endM] = dispo.close.split(":").map(Number);
        const duration = (endH * 60 + endM) - (startH * 60 + startM);

        if (duration < 60) {
          return alert(`La disponibilité du ${dispo.jour} est inférieure à 1 heure.`);
        }
      }

      // Données prêtes à l’envoi
      const inscriptionData = {
        name: nom,
        surname: prenom,
        birthday: age,
        courses: parseInt(cours),
        level: 0,
        email: email,
        phone: fullNumber,
        photoConsent: acceptPhoto === "yes",
        disponibilities: disponibilites.map(d => ({
          dayWeek: dayMapping[d.jour],
          open: d.open,
          close: d.close,
        })),
      };

      try {
        showForm.value = false;
        isLoading.value = true;
        await createInscription(inscriptionData);
        isLoading.value = false;
      } catch (error) {
        isLoading.value = false;
        showError.value = true;
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
      showForm,
      showError,
      isLoading,
      generateTimeSlots,
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
  focus: outline-none;
  focus: ring-2;
  focus: ring-blue-500;
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


/* Scrollbar */
::-webkit-scrollbar {
  width: 12px;
}

::-webkit-scrollbar-track {
  background: transparent;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  background: linear-gradient(45deg, #528359, #3a6242);
  border-radius: 10px;
  border: 2px solid transparent;
  background-clip: padding-box;
  transition: background 0.3s ease-in-out, border 0.3s ease-in-out;
}

</style>
