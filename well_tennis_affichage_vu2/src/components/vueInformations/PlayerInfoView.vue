<template>
  <Teleport to="body">
    <transition name="fade">
      <div class="popup">
        <form @submit.prevent="savePlayer">
          <div class="popup-header">
            <h2>{{ isEditing ? "Modification du joueur" : "Ajouter d'un joueur" }}</h2>
            <label class="button secondary" title="Sauvegarder le joueur">
              <input type="submit" class="hidden"/>
              <span class="material-symbols-outlined">save</span>
              Sauvegarder
            </label>
            <label class="button secondary red" title="Supprimer le joueur" v-if="isEditing">
              <input type="button" class="hidden" @click="deletePlayerHandler"/>
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
                <input type="text" v-model="editablePlayer.name" required/>
              </label>

              <label class="input top-label">
                <span>Prénom</span>
                <input type="text" v-model="editablePlayer.surname" required/>
              </label>

              <label class="input top-label">
                <span>Email</span>
                <input type="email" v-model="editablePlayer.email" required/>
              </label>

              <div class="input-group">
                <span>Date de naissance</span>
                <label class="input">
                  <input type="date" v-model="editablePlayer.birthday" required/>
                </label>

                <label class="input">
                  <span>soit</span>
                  <input type="text" :value="computeAge(editablePlayer.birthday) + ' ans'" readonly/>
                </label>
              </div>

              <div class="input-group">
                <span>Temps de travail par semaine (en minutes)</span>
                <label class="input">
                  <span>N°1</span>
                  <input type="text" v-model="editablePlayer.phone" required
                         placeholder="Téléphone 1"/>
                </label>

                <label class="input">
                  <span>N°2</span>
                  <input type="text" v-model="editablePlayer.phone2"
                         placeholder="Téléphone 2"/>
                </label>
              </div>

              <label class="input top-label">
                <span>Niveau (0-30)</span>
                <input type="number" v-model="editablePlayer.level" min="0" max="30" required/>
              </label>

              <label class="input top-label">
                <span>Nombre de cours</span>
                <input type="number" v-model="editablePlayer.courses" min="1" max="3" required/>
              </label>
            </div>

            <div class="right">
              <span class="section-title">Disponibilités (uniquement en période de 30 minutes)</span>
              <div class="availability-row" v-for="(slot, index) in editablePlayer.disponibilities" :key="index">
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
import usePlayers from "../../useJs/usePlayers.js";
import {useSessionsStore} from "../../store/useSessionsStore.js";
import {storeToRefs} from "pinia";

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
    const sessionsStore = useSessionsStore();
    const {sessions} = storeToRefs(sessionsStore);
    const refreshSessions = sessionsStore.refreshSessions;


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
        editablePlayer.value.disponibilities.push({
          dayWeek: "", // Initialise avec une valeur vide
          open: "",
          close: ""
        });
      } catch (error) {
        console.error("Erreur lors de la création de la disponibilité :", error);
        alert("Impossible d'ajouter la disponibilité.");
      }
    };


    const removeAvailability = async (index) => {
      try {
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
          editablePlayer.value.level >= 0 && editablePlayer.value.level <= 30 &&
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




