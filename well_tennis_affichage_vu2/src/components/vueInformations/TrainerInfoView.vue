<template>
  <Teleport to="body">
    <transition name="fade">
      <div class="popup">
        <form @submit.prevent="saveTrainer">
          <div class="popup-header">
            <h2>{{ isEditing ? "Modifier l'Entraîneur" : "Ajouter un Entraîneur" }}</h2>
            <label class="button secondary" title="Sauvegarder l'entraîneur">
              <input type="submit" class="hidden"/>
              <span class="material-symbols-outlined">save</span>
              Sauvegarder
            </label>
            <label class="button secondary red" title="Supprimer l'entraîneur" v-if="isEditing">
              <input type="button" class="hidden" @click="deleteTrainerHandler"/>
              <span class="material-symbols-outlined">delete</span>
              Supprimer
            </label>
            <label class="button secondary red no-text" title="Annuler les modifications">
              <input type="button" class="hidden" @click="$emit('close')"/>
              <span class="material-symbols-outlined">close</span>
            </label>
          </div>

          <div class="popup-content">
            <div class="left">
              <label class="input top-label">
                <span>Nom</span>
                <input type="text" v-model="editableTrainer.name" required/>
              </label>

              <label class="input top-label">
                <span>Prénom</span>
                <input type="text" v-model="editableTrainer.surname" required/>
              </label>

              <label class="input top-label">
                <span>Email</span>
                <input type="email" v-model="editableTrainer.email" required/>
              </label>

              <div class="input-group">
                <span>Préférences de niveau</span>
                <label class="input">
                  <span>de</span>
                  <input type="number" v-model="editableTrainer.infLevel" required min="0" placeholder="minimum"/>
                </label>

                <label class="input">
                  <span>à</span>
                  <input type="number" v-model="editableTrainer.supLevel" required min="0" placeholder="maximum"/>
                </label>
              </div>

              <div class="input-group">
                <span>Préférences d'âge</span>
                <label class="input">
                  <span>de</span>
                  <input type="number" v-model="editableTrainer.infAge" required min="1" placeholder="minimum"/>
                </label>

                <label class="input">
                  <span>à</span>
                  <input type="number" v-model="editableTrainer.supAge" required min="1" placeholder="maximum"/>
                </label>
              </div>

              <div class="input-group">
                <span>Temps de travail par semaine (en minutes)</span>
                <label class="input">
                  <span>de</span>
                  <input type="number" v-model="editableTrainer.infWeeklyMinutes" required min="0"
                         placeholder="minimum"/>
                </label>

                <label class="input">
                  <span>à</span>
                  <input type="number" v-model="editableTrainer.supWeeklyMinutes" required min="0"
                         placeholder="maximum"/>
                </label>
              </div>
              <div class="input-group">
                <label class="checkbox">
                  <input type="checkbox" v-model="editableTrainer.partTime" class="hidden"/>
                  <span class="material-symbols-outlined">
                    {{ editableTrainer.partTime ? 'check_box' : 'check_box_outline_blank' }}
                  </span>
                  <span>Vacataire</span>
                </label>

                <label class="checkbox">
                  <input type="checkbox" v-model="editableTrainer.admin" class="hidden"/>
                  <span class="material-symbols-outlined">
                    {{ editableTrainer.admin ? 'check_box' : 'check_box_outline_blank' }}
                  </span>
                  <span>Administrateur</span>
                </label>
              </div>
            </div>

            <div class="right">
              <span class="section-title">Disponibilités (uniquement en période de 30 minutes)</span>
              <div class="availability-row" v-for="(slot, index) in editableTrainer.disponibilities" :key="index">
                <label class="button secondary red no-text">
                  <input type="button" class="hidden" @click="removeAvailability(index)"/>
                  <span class="material-symbols-outlined">remove</span>
                </label>
                <select v-model="slot.dayWeek" class="input" required>
                  <option value=1>Lundi</option>
                  <option value=2>Mardi</option>
                  <option value=3>Mercredi</option>
                  <option value=4>Jeudi</option>
                  <option value=5>Vendredi</option>
                  <option value=6>Samedi</option>
                  <option value=7>Dimanche</option>
                </select>
                <span class="green-bold">de</span>
                <input type="time" v-model="slot.open" class="input" required/>
                <span class="green-bold">à</span>
                <input type="time" v-model="slot.close" class="input" required/>
              </div>
              <label class="button secondary no-text">
                <input type="button" class="hidden" @click="addAvailability"/>
                <span class="material-symbols-outlined">add</span>
              </label>
            </div>
          </div>
        </form>
      </div>
    </transition>
  </Teleport>
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

    console.log("editableTrainer", editableTrainer);
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


        const TrainerData = {...editableTrainer.value};


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
.popup-content {
  display: flex;

  > * {
    flex: 1;
  }
}

.green-bold {
  color: var(--accent);
  font-weight: bold;
}

.left {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.right {
  padding-left: 2rem;

  .section-title {
    display: block;
    font-weight: bold;
    margin-bottom: 1rem;
  }

  .availability-row {
    display: flex;
    gap: 1rem;
    margin-bottom: 1rem;
    align-items: center;

    select.input,
    input[type="time"].input {
      height: 2rem;
      padding: 0 0.5rem;
      border: none;
      box-shadow: 0 1px 0 var(--accent);
      transition: all 0.2s ease;

      &:focus {
        outline: none;
        box-shadow: 0 3px 0 var(--accent);
      }
    }

    select.input {
      flex: 2;
    }

    input[type="time"].input {
      flex: 1;
    }

  }

  .button {
    height: 2rem;
    aspect-ratio: 1;
  }
}

.input-group {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  column-gap: 1rem;

  > span {
    flex-basis: 100%;
    font-weight: bold;
  }

  > * {
    flex: 1;
  }

  > .input {
    flex: 2;
    display: flex;
    gap: 1rem;

    > span {
      color: var(--accent);
    }

    > input {
      flex: 1;
    }
  }
}

</style>