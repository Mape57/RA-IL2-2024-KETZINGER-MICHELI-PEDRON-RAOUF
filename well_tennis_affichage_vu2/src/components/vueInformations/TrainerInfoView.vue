<template>
  <transition name="fade">
    <div class="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center p-6">
      <div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-5xl relative">
        <button class="close-button absolute top-2 right-2" @click="$emit('close')">✕</button>
        <h3 class="text-2xl font-bold text-gray-700 mb-6 text-center">{{ isEditing ? "Modifier l'Entraîneur" : "Ajouter un Entraîneur" }}</h3>

        <form class="grid grid-cols-2 gap-8" @submit.prevent="saveTrainer">
          <div class="space-y-4">
            <div>
              <label class="block font-semibold text-gray-700">Nom :</label>
              <input type="text" v-model="editableTrainer.name" class="input-field" required />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Prénom :</label>
              <input type="text" v-model="editableTrainer.surname" class="input-field" required />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Email :</label>
              <input type="email" v-model="editableTrainer.email" class="input-field" required />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Niveau max d'enseignement :</label>
              <input type="number" v-model="editableTrainer.supLevel" class="input-field" required min="1" />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Niveau min d'enseignement :</label>
              <input type="number" v-model="editableTrainer.infLevel" class="input-field" required min="1" />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Âge max encadré :</label>
              <input type="number" v-model="editableTrainer.supAge" class="input-field" required min="1" />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Âge min encadré :</label>
              <input type="number" v-model="editableTrainer.infAge" class="input-field" required min="1" />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Minutes max d'enseignement par semaine :</label>
              <input type="number" v-model="editableTrainer.supWeeklyMinutes" class="input-field" required min="1" />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Minutes min d'enseignement par semaine :</label>
              <input type="number" v-model="editableTrainer.infWeeklyMinutes" class="input-field" required min="1" />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Temps partiel :</label>
              <input type="checkbox" v-model="editableTrainer.partTime" class="checkbox-input" />
            </div>

            <div>
              <label class="block font-semibold text-gray-700">Administrateur :</label>
              <input type="checkbox" v-model="editableTrainer.admin" class="checkbox-input" />
            </div>

          </div>

          <div class="flex flex-col space-y-4 h-full">
            <label class="block font-semibold text-gray-700">Disponibilités :</label>
            <div class="availability-container">
              <div v-for="(slot, index) in editableTrainer.disponibilities" :key="index" class="availability-row">
                <select v-model="slot.dayWeek" class="day-select" required>
                  <option value=1>Lundi</option>
                  <option value=2>Mardi</option>
                  <option value=3>Mercredi</option>
                  <option value=4>Jeudi</option>
                  <option value=5>Vendredi</option>
                  <option value=6>Samedi</option>
                  <option value=7>Dimanche</option>
                </select>
                <input type="time" v-model="slot.open" class="time-input" required />
                <input type="time" v-model="slot.close" class="time-input" required />
                <span class="delete-icon" @click="removeAvailability(index)">🗑️</span>
              </div>
              <button type="button" @click="addAvailability" class="add-row">➕ Ajouter une disponibilité</button>
            </div>
          </div>

          <div class="col-span-2 flex justify-end space-x-4 mt-6">
            <button type="button" @click="deleteTrainerHandler" class="button-danger">Supprimer</button>
            <button type="submit" class="button-primary">Enregistrer</button>
          </div>
        </form>
      </div>
    </div>
  </transition>
</template>

<script>
import {ref, watch} from "vue";
import useTrainers from "../../useJs/useTrainers.js";

export default {
  name: "TrainerInfoView",
  props: {
    trainer: {
      type: Object,
      required: true,
    },
  },
  computed: {
    isEditing() {
      return !!this.editableTrainer.id; // Si id est défini, on est en mode édition
    },
  },
  setup(props, {emit}) {
    const {updateTrainer, createTrainer, deleteTrainer} = useTrainers();

    const editableTrainer = ref({...props.trainer, disponibilities: [...(props.trainer.disponibilities || [])]});

    const deleteTrainerHandler = async () => {
      const confirmDelete = confirm("Êtes-vous sûr de vouloir supprimer ce coach ?");
      if (!confirmDelete) return;

      try {
        await deleteTrainer(editableTrainer.value.id); // Supprime le Coach via le service
        alert("Le coach a été supprimé avec succès !");
        emit("delete", editableTrainer.value.id); // Informe le parent de la suppression
      } catch (error) {
        console.error("Erreur lors de la suppression :", error);
        alert("Une erreur est survenue lors de la suppression.");
      }
    };


    watch(
        () => props.trainer,
        (newVal) => {
          editableTrainer.value = {...newVal, disponibilities: [...(newVal.disponibilities || [])]};
        }
    );

    const addAvailability = async () => {
      try {
        const disponibilityData = {
          dayWeek: "",
          open: "",
          close: "",
        };
        editableTrainer.value.disponibilities.push(disponibilityData)
      } catch (error) {
        console.error("Erreur lors de l'ajout de disponibilité :", error);
        alert("Une erreur est survenue lors de l'ajout de disponibilité.");
      }
    };

    const removeAvailability = async (index) => {
      try {
        editableTrainer.value.disponibilities.splice(index, 1);
      } catch (error) {
        console.error("Erreur lors de la suppression de la disponibilité :", error);
      }
    };

    const saveTrainer = async () => {
      try {
        // 1. Validation des champs obligatoires
        // if (!validateForm()) {
        //   alert("Veuillez remplir correctement tous les champs."); // Alerte si des champs sont manquants
        //   return;
        // }

        // 2. Vérification des créneaux horaires
        const invalidTimes = editableTrainer.value.disponibilities.some((slot) => !isValidTime(slot)); // Vérifie si un créneau est invalide
        if (invalidTimes) {
          alert("Veuillez corriger les erreurs dans les disponibilités avant de continuer.");
          return;
        }

        // 3. Vérification des doublons dans les disponibilités
        if (!validateUniqueDisponibilities()) {
          alert("Des chevauchements existent dans les disponibilités. Veuillez les corriger.");
          return;
        }


        const TrainerData = { ...editableTrainer.value };


        let savedTrainer;
        if (!editableTrainer.value.id) {
          // Création
          savedTrainer = await createTrainer(TrainerData);
          alert("Coach créé avec succès !");
        } else {
          // Mise à jour
          savedTrainer = await updateTrainer(TrainerData);
          alert("Coach mis à jour avec succès !");
        }
        emit("save", savedTrainer);
        emit("close");
      } catch (error) {
        console.error("Erreur lors de la sauvegarde :", error.response?.data || error.message);
        alert("Une erreur est survenue.");
      }
    };


    // const validateForm = () => {
    //   return (
    //       editableTrainer.value.level >= 1 && editableTrainer.value.level <= 20 &&
    //       editableTrainer.value.courses >= 1 && editableTrainer.value.courses <= 3
    //   );
    // };



    const validateUniqueDisponibilities = () => {
      const sortedDisponibilities = editableTrainer.value.disponibilities
          .filter(slot => slot.day && slot.open && slot.close) // Exclure les créneaux incomplets
          .sort((a, b) => (a.day === b.day ? a.open.localeCompare(b.open) : a.day.localeCompare(b.day))); // Trier par jour et heure de début

      let hasOverlap = false;

      for (let i = 0; i < sortedDisponibilities.length - 1; i++) {
        const current = sortedDisponibilities[i];
        const next = sortedDisponibilities[i + 1];

        if (current.day === next.day) {
          // Vérifier les chevauchements
          if (current.close > next.open) {
            current.error = "Ce créneau chevauche un autre.";
            next.error = "Ce créneau chevauche un autre.";
            hasOverlap = true;
          }
          // Vérifier les créneaux consécutifs sans intervalle
          else if (current.close === next.open) {
            current.error = "Deux créneaux consécutifs sans intervalle ne sont pas autorisés.";
            next.error = "Deux créneaux consécutifs sans intervalle ne sont pas autorisés.";
            hasOverlap = true;
          } else {
            current.error = null; // Pas d'erreur
            next.error = null;
          }
        }
      }

      //Mise à jour des disponibilités (évite de les écraser si elles sont toutes invalides)
      if (sortedDisponibilities.length > 0) {
        editableTrainer.value.disponibilities = sortedDisponibilities; // Mise à jour uniquement si la liste n'est pas vide
      }
      return !hasOverlap; // Retourne false si des chevauchements ou créneaux consécutifs sont détectés
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
      deleteTrainerHandler,
      editableTrainer,
      saveTrainer,
      addAvailability,
      removeAvailability
    };
  },
};
</script>

<style scoped>
/* Overlay du pop-up */
.fixed.inset-0 {
  z-index: 1000 !important;
}

/* Contenu de la boîte modale */
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
  border-color: #528359;
  box-shadow: 0 0 5px rgba(47, 133, 90, 0.5);
}

/* Disponibilités */
.availability-container {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  max-height: 200px;
  overflow-y: auto;
  padding-right: 10px;
}

/* Liste des disponibilités */
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
  background: #528359;
  color: white;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.add-row:hover {
  background: #3e6c46;
}

/* Boutons */
.button-primary {
  background-color: #528359;
  color: white;
  padding: 10px 16px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.button-primary:hover {
  background-color: #3e6c46;
}

.button-danger {
  background-color: #c82333;
  color: white;
  padding: 10px 16px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.button-danger:hover {
  background-color: #a81c28;
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

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.8);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.bg-white.p-8.rounded-lg.shadow-xl {
  z-index: 1100 !important;
  max-height: 90vh;
  overflow-y: auto;
  animation: fadeIn 0.2s ease-out;
}

@media (max-width: 1024px) {
  .grid-cols-2 {
    grid-template-columns: 1fr;
  }
}
</style>