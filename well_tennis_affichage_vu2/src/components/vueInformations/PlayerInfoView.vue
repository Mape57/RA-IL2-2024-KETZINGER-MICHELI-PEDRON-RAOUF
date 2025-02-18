<template>
  <div class="info-container relative">
    <button class="close-button absolute top-2 right-2" @click="$emit('close')">✕</button>
    <h2 class="title">Informations Complètes du Joueur</h2>

    <form class="info-card" @submit.prevent="savePlayer">
      <div>
        <label><strong>Nom :</strong></label>
        <input type="text" v-model="editablePlayer.name" required/>
      </div>

      <div>
        <label><strong>Prénom :</strong></label>
        <input type="text" v-model="editablePlayer.surname" required/>
      </div>

      <div>
        <label><strong>Email :</strong></label>
        <input type="email" v-model="editablePlayer.email" required/>
      </div>

      <div>
        <label><strong>Niveau (1-20) :</strong></label>
        <input type="text" v-model="editablePlayer.level" min="1" max="20" required/>
      </div>

      <div>
        <label><strong>Nombre de cours par semaine (1-3) :</strong></label>
        <input type="number" v-model="editablePlayer.courses" min="1" max="3" required/>
      </div>

      <div>
        <label><strong>Date de naissance :</strong></label>
        <input type="date" v-model="editablePlayer.birthday" required/>
      </div>

      <div>
        <label><strong>Âge sportif :</strong></label>
        <span>{{ computeAge(editablePlayer.birthday) }} ans</span>
      </div>

      <!-- Section Disponibilités -->
      <div class="availability-container">
        <div class="availability-header">
          <span>Jour</span>
          <span>Début</span>
          <span>Fin</span>
          <span></span>
        </div>

        <!-- Liste des disponibilités -->
        <div

            v-for="(slot, index) in editablePlayer.disponibilities"
            :key="slot.id"
            class="availability-row"
        >
          <!-- Sélecteur de jour -->
          <select v-model="slot.dayWeek" class="day-select" required>
            <option value="1">Lundi</option>
            <option value="2">Mardi</option>
            <option value="3">Mercredi</option>
            <option value="4">Jeudi</option>
            <option value="5">Vendredi</option>
            <option value="6">Samedi</option>
            <option value="Dimanche">Dimanche</option>
          </select>

          <input
              type="time"
              v-model="slot.open"
              class="time-input"
              required
              step="1800"
          />
          <input
              type="time"
              v-model="slot.close"
              class="time-input"
              required
              step="1800"
          />
          <span class="delete-icon" @click="removeAvailability(index)">🗑️</span>

          <!-- Affichage de l'erreur -->
          <div
              v-if="slot.error" class="error">{{ slot.error }}
          </div>

        </div>


        <!-- Bouton Ajouter nouvelle disponibilité -->
        <div class="add-row" @click="addAvailability">
          <span class="add-icon">➕</span>
          <span>Nouvelle disponibilité</span>
        </div>
      </div>

      <!-- Bouton Enregistrer cloture -->
      <button class="save-button" type="submit">Enregistrer</button>

      <!-- Bouton de supression du player -->
      <button class="del-button" @click.prevent="deletePlayerHandler">Supprimer</button>
<!--      <span class="material-symbols-outlined small-icon cursor-pointer" title="Supprimer">delete</span>
c'est le symbole delete
-->

    </form>
  </div>
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




        const playerData = { ...editablePlayer.value };

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
.info-container {
  padding: 2rem;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: auto;
}

.title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: #333;
}

.availability-container {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.availability-header {
  display: flex;
  justify-content: space-between;
  font-weight: bold;
  color: #556b2f;
}

.availability-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  border-bottom: 1px solid #ddd;
  padding: 0.5rem 0;
}

.dayWeek {
  flex: 1;
  color: #2f4f4f;
  font-weight: 500;
}

.time-input {
  flex: 1;
  border: none;
  background-color: #f7f7f7;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
}

.delete-icon {
  color: #d9534f;
  cursor: pointer;
  font-size: 1.25rem;
}

.add-row {
  display: flex;
  align-items: center;
  color: #556b2f;
  font-weight: 500;
  cursor: pointer;
}

.add-icon {
  font-size: 1.25rem;
  margin-right: 0.5rem;
}

.save-button {
  margin-top: 1rem;
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.del-button {
  margin-top: 1rem;
  background-color: #c31d1d;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.save-button:hover {
  background-color: #45a049;
}

.title {
  font-size: 1.75rem;
  font-weight: bold;
  margin-bottom: 1rem;
  color: #333;
}

.info-card {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
  font-size: 1rem;
  color: #555;
}

.error {
  color: red;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}


input[type="text"],
input[type="email"],
input[type="number"],
input[type="date"] {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  margin-top: 1rem;
  padding: 0.75rem 1.5rem;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>



