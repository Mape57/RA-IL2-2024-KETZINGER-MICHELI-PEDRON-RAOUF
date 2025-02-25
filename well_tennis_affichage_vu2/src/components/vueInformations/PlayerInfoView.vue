<template>
  <transition name="fade">
    <div class="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center p-6">
      <div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-5xl relative">
        <button class="close-button absolute top-2 right-2" @click="$emit('close')">✕</button>
        <h3 class="text-2xl font-bold text-gray-700 mb-6 text-center">{{ isEditing ? "Modification du joueur" : "Ajouter d'un joueur" }}</h3>

        <form class="grid grid-cols-2 gap-8" @submit.prevent="savePlayer">
          <div class="space-y-4">
            <div>
              <label class="block font-semibold text-gray-700">Nom :</label>
              <input type="text" v-model="editablePlayer.name" class="input-field" required />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Prénom :</label>
              <input type="text" v-model="editablePlayer.surname" class="input-field" required />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Email :</label>
              <input type="email" v-model="editablePlayer.email" class="input-field" required />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Date de naissance :</label>
              <input type="date" v-model="editablePlayer.birthday" class="input-field" required />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Âge sportif :</label>
              <p class="text-gray-600">{{ computeAge(editablePlayer.birthday) }} ans</p>
            </div>

            <div class="grid grid-cols-2 gap-4">
              <div>
                <label class="block font-semibold text-gray-700">Niveau (1-20) :</label>
                <input type="number" v-model="editablePlayer.level" min="1" max="20" class="input-field" required />
              </div>

              <div>
                <label class="block font-semibold text-gray-700">Nombre de cours :</label>
                <input type="number" v-model="editablePlayer.courses" min="1" max="3" class="input-field" required />
              </div>
            </div>
          </div>

          <div class="space-y-4">
            <label class="block font-semibold text-gray-700">Disponibilités :</label>
            <div class="availability-container">
              <div v-for="(slot, index) in editablePlayer.disponibilities" :key="index" class="availability-row">
                <select v-model="slot.dayWeek" class="day-select" required>
                  <option value="1">Lundi</option>
                  <option value="2">Mardi</option>
                  <option value="3">Mercredi</option>
                  <option value="4">Jeudi</option>
                  <option value="5">Vendredi</option>
                  <option value="6">Samedi</option>
                  <option value="7">Dimanche</option>
                </select>
                <input type="time" v-model="slot.open" class="time-input" required />
                <input type="time" v-model="slot.close" class="time-input" required />
                <span class="delete-icon" @click="removeAvailability(index)">🗑️</span>
              </div>
              <button type="button" @click="addAvailability" class="add-row">➕ Ajouter une disponibilité</button>
            </div>
          </div>

          <div class="col-span-2 flex justify-end space-x-4 mt-6">
            <button type="button" @click="deletePlayerHandler" class="del-button">Supprimer</button>
            <button type="submit" class="save-button">Enregistrer</button>
          </div>
        </form>
      </div>
    </div>
  </transition>
</template>

<script>
import {ref, watch} from "vue";
import usePlayers from "../../useJs/usePlayers.js";
import DisponibilityService from "../../services/DisponibilityService.js";
import DisponibilityPlayerService from "../../services/DisponibilityPlayerService.js";

export default {
  name: "PlayerInfoView",
  props: {
    player: {
      type: Object,
      required: true,
    },
  },
  computed: {
    isEditing() {
      return !!this.editablePlayer.id; // Si id est défini, on est en mode édition
    },
  },
  setup(props, {emit}) {
    const {computeAge, updatePlayer, createPlayer, deletePlayer} = usePlayers();

    const editablePlayer = ref({...props.player, disponibilities: [...(props.player.disponibilities || [])]});

    const deletePlayerHandler = async () => {
      const confirmDelete = confirm("Êtes-vous sûr de vouloir supprimer ce joueur ?");
      if (!confirmDelete) return;

      try {
        await deletePlayer(editablePlayer.value.id); // Supprime le joueur via le service
        alert("Le joueur a été supprimé avec succès !");
        emit("delete", editablePlayer.value.id); // Informe le parent de la suppression
      } catch (error) {
        console.error("Erreur lors de la suppression :", error);
        alert("Une erreur est survenue lors de la suppression.");
      }
    };


    watch(
        () => props.player,
        (newVal) => {
          editablePlayer.value = {...newVal, disponibilities: [...(newVal.disponibilities || [])]};
        }
    );



    const addAvailability = async () => {
      try {
        if (!editablePlayer.value.id) {
          alert("Veuillez enregistrer le joueur avant d'ajouter une disponibilité.");
          return;
        }

        const disponibilityData = {
          dayWeek: "",
          open: "",
          close: "",
        };


        const response = await DisponibilityService.createDisponibility(disponibilityData);
        const newDisponibilityId = response.data.id;

        editablePlayer.value.disponibilities.push(response.data);

        await DisponibilityPlayerService.createDisponibilityPlayer({
          idPlayer: editablePlayer.value.id,
          idDisponibility: newDisponibilityId
        });


      } catch (error) {
        console.error("Erreur lors de la création de la disponibilité :", error);
        alert("Impossible d'ajouter la disponibilité.");
      }
    };


    const removeAvailability = async (index) => {
      try {
        const slot = editablePlayer.value.disponibilities[index];

        if (!slot.id) {
          editablePlayer.value.disponibilities.splice(index, 1);
          return;
        }
        await DisponibilityPlayerService.deleteDisponibilityPlayer(editablePlayer.value.id, slot.id);

        await DisponibilityService.deleteDisponibility(slot.id);

        editablePlayer.value.disponibilities.splice(index, 1);
      } catch (error) {
        console.error("Erreur lors de la suppression de la disponibilité :", error);
      }
    };

    const savePlayer = async () => {
      try {

        // 1. Validation des champs obligatoires
        if (!validateForm()) {
          alert("Veuillez remplir correctement tous les champs."); // Alerte si des champs sont manquants
          return;
        }


        // 2. Vérification des créneaux horaires
        const invalidTimes = editablePlayer.value.disponibilities.some((slot) => !isValidTime(slot)); // Vérifie si un créneau est invalide
        if (invalidTimes) {
          alert("Veuillez corriger les erreurs dans les disponibilités avant de continuer.");
          return;
        }


        // 3. Vérification des doublons dans les disponibilités
        if (!validateUniqueDisponibilities()) {
          alert("Des chevauchements existent dans les disponibilités. Veuillez les corriger.");
          return;
        }

        //ici Si on crée un joueur (id non défini) → On force validate: false.
        //Si on met à jour un joueur → On garde la valeur existante. cependant je pense qu'il faudra peut etre l'enlever
        const playerData = {
          ...editablePlayer.value,
          validate: editablePlayer.value.id ? editablePlayer.value.validate : true
        };

        let savedPlayer;
        if (!editablePlayer.value.id) {
          savedPlayer = await createPlayer(playerData);
          alert("Joueur créé avec succès !");
        } else {
          savedPlayer = await updatePlayer(playerData);
          alert("Joueur mis à jour avec succès !");
        }

        emit("save", savedPlayer);
        emit("close");
      } catch (error) {
        console.error("Erreur lors de la sauvegarde :", error.response?.data || error.message);
        alert("Une erreur est survenue.");
      }
    };


    const validateForm = () => {
      const today = new Date();

      // Vérifier la date de naissance
      const birthDate = new Date(editablePlayer.value.birthday);
      if (!editablePlayer.value.birthday || isNaN(birthDate.getTime()) || birthDate >= today) {
        alert("La date de naissance est invalide");
        return false;
      }

      // Vérifier l'âge calculé
      const age = computeAge(editablePlayer.value.birthday);
      if (age < 0 || age > 120) {
        alert("L'âge calculé est invalide.");
        return false;
      }
      return (
          editablePlayer.value.level >= 1 && editablePlayer.value.level <= 20 &&
          editablePlayer.value.courses >= 1 && editablePlayer.value.courses <= 3
      );
    };



    const validateUniqueDisponibilities = () => {
      //Filtrer les créneaux valides (on exclut les créneaux incomplets)
      const sortedDisponibilities = editablePlayer.value.disponibilities
          .filter(slot => slot.day && slot.open && slot.close) // Garde uniquement les créneaux ayant un jour, une heure d'ouverture et une heure de fermeture
          .sort((a, b) =>
              (a.day === b.day ? a.open.localeCompare(b.open) : a.day.localeCompare(b.day))
          ); // Trie par jour, puis par heure d'ouverture

      let hasOverlap = false; // Variable pour détecter les conflits de créneaux horaires

      //Vérifier les conflits entre créneaux du même jour
      for (let i = 0; i < sortedDisponibilities.length - 1; i++) {
        const current = sortedDisponibilities[i];  // Créneau actuel
        const next = sortedDisponibilities[i + 1]; // Créneau suivant

        if (current.day === next.day) { // Comparaison uniquement si les créneaux concernent le même jour
          // Vérifier s'il y a un chevauchement entre deux créneaux
          if (current.close > next.open) {
            current.error = "Ce créneau chevauche un autre."; // Ajout d'une erreur dans l'objet du créneau
            next.error = "Ce créneau chevauche un autre.";
            hasOverlap = true;
          }
          // Vérifier si deux créneaux se suivent sans intervalle
          else if (current.close === next.open) {
            current.error = "Deux créneaux consécutifs sans intervalle ne sont pas autorisés.";
            next.error = "Deux créneaux consécutifs sans intervalle ne sont pas autorisés.";
            hasOverlap = true;
          }
          //Pas d'erreur => Supprimer les erreurs enregistrées
          else {
            current.error = null;
            next.error = null;
          }
        }
      }
      //Mise à jour des disponibilités (évite de les écraser si elles sont toutes invalides)
      if (sortedDisponibilities.length > 0) {
        editablePlayer.value.disponibilities = sortedDisponibilities; // Mise à jour uniquement si la liste n'est pas vide
      }
      //Retourner `false` si des chevauchements ont été détectés
      return !hasOverlap;
    };




    const isTimeValid = (time) => {
      const [hours, minutes] = time.split(":").map(Number);
      return minutes === 0 || minutes === 30;
    };

    const isValidTime = (slot) => {
      if (!isTimeValid(slot.open) || !isTimeValid(slot.close)) {
        slot.error = "Les heures doivent être des heures pleines ou demi-heures.";
        return false;
      }
      if (slot.open >= slot.close) {
        slot.error = "L'heure de début doit être antérieure à l'heure de fin.";
        return false;
      }
      slot.error = null;
      return true;
    };



    return {
      computeAge,
      deletePlayerHandler,
      editablePlayer,
      savePlayer,
      addAvailability,
      removeAvailability,
    };
  },
};
</script>

<style scoped>

.fixed.inset-0 {
  z-index: 1000 !important;
}

/* Pop-up */
.bg-white.p-8.rounded-lg.shadow-xl {
  z-index: 1100 !important;
  max-height: 90vh;
  overflow-y: auto;
}

/* Champs de texte */
.input-field {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  transition: border 0.3s ease;
}

.input-field:focus {
  outline: none;
  border-color: #2F855A;
  box-shadow: 0 0 5px rgba(47, 133, 90, 0.5);
}

/* 📆 Disponibilités */
.availability-container {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  min-height: 300px;
  max-height: 430px;
  overflow-y: auto;
}

.availability-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  background: white;
}

.day-select, .time-input {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

/* Supprimer une disponibilité */
.delete-icon {
  font-size: 20px;
  color: #d9534f;
  cursor: pointer;
}

.delete-icon:hover {
  color: #c82333;
}

/* Ajouter une disponibilité */
.add-row {
  display: flex;
  align-items: center;
  justify-content: center;
  background: #2F855A;
  color: white;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.add-row:hover {
  background: #256f46;
}

button {
  padding: 10px 16px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.del-button {
  background-color: #c82333;
  color: white;
}

.del-button:hover {
  background-color: #a81c28;
}

.save-button {
  background-color: #2F855A;
  color: white;
}

.save-button:hover {
  background-color: #256f46;
}

.close-button {
  position: absolute;
  top: 15px;
  right: 15px;
  background: none;
  border: none;
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
  cursor: pointer;
  transition: color 0.3s ease, transform 0.2s ease;
}

.close-button:hover {
  color: #c82333;
  transform: scale(1.2);
}


@media (max-width: 1024px) {
  .grid-cols-2 {
    grid-template-columns: 1fr;
  }
}
</style>




