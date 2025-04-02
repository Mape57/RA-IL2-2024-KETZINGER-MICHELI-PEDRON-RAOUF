<template>
  <div class="player-list">
    <h3 class="text-lg font-semibold text-gray-700 mb-4">Inscriptions en attente</h3>

    <div class="player-card" v-for="player in pendingPlayers" :key="player.id" @click="selectPlayer(player)">
      <div class="player-info">
        <span class="material-symbols-outlined text-green-600 text-xl">person</span>
        <div>
          <p class="text-sm font-semibold text-gray-800">{{ player.name }} {{ player.surname }}</p>
          <p class="text-xs text-gray-500">{{ player.email }}</p>
        </div>
      </div>
    </div>

    <!-- Pop-up détails joueur -->
    <transition name="fade">
      <div v-if="selectedPlayer" class="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center p-6">
        <div class="bg-white p-8 rounded-lg shadow-xl w-full max-w-2xl">
          <h3 class="text-2xl font-bold text-gray-700 mb-4">Détails de l'inscription</h3>
          <div class="space-y-4 text-lg">
            <p><strong>Nom :</strong> {{ selectedPlayer.name }} {{ selectedPlayer.surname }}</p>
            <p><strong>Email :</strong> {{ selectedPlayer.email }}</p>
            <p><strong>Date de naissance :</strong> {{ selectedPlayer.birthday }}</p>
            <p><strong>Nombre de cours :</strong> {{ selectedPlayer.courses }}</p>
            <div>
              <p><strong>Niveau :</strong></p>
              <div class="level-container">
                <!-- Slider pour ajuster le niveau -->
                <input
                    v-model="selectedPlayer.level"
                    type="range"
                    min="1"
                    max="30"
                    step="1"
                    class="level-slider"
                />
                <!-- Affichage du niveau sélectionné -->
                <span class="level-display">{{ selectedPlayer.level }}</span>
              </div>
            </div>


            <div>
              <p><strong>Disponibilités :</strong></p>
              <ul class="list-disc ml-6 text-base">
                <li v-for="dispo in selectedPlayer.disponibilities" :key="dispo.id">
                  <strong>{{ getJourLabel(dispo.dayWeek) }}</strong> : {{ dispo.open }} - {{ dispo.close }}
                </li>
              </ul>
            </div>
          </div>
          <div class="mt-6 flex justify-end space-x-4">
            <button @click="selectedPlayer = null" class="bg-gray-300 hover:bg-gray-400 px-5 py-2 rounded-lg text-lg">
              Fermer
            </button>
            <button @click="validateAndClose(selectedPlayer.id)" class="bg-[#528359] hover:bg-[#456c4c] text-white px-5 py-2 rounded-lg text-lg">
              Valider
            </button>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import { ref, nextTick } from "vue";
import usePlayers from "../../useJs/usePlayers.js";

export default {
  props: {
    pendingPlayers: {
      type: Array,
      required: true,
    },
  },
  setup() {
    const { validatePlayer } = usePlayers();
    const selectedPlayer = ref(null);

    const validateAndClose = async (playerId) => {
      try {
        if (!selectedPlayer.value) return;

        const updatedPlayer = {
          level: Number(selectedPlayer.value.level)
        };

        await validatePlayer(playerId, updatedPlayer);
        selectedPlayer.value = null;
        await nextTick();
      } catch (error) {
        console.error("Erreur lors de la validation :", error);
      }
    };

    const selectPlayer = (player) => {
      selectedPlayer.value = player;
    };

    const getJourLabel = (dayWeek) => {
      const jours = {
        1: "Lundi",
        2: "Mardi",
        3: "Mercredi",
        4: "Jeudi",
        5: "Vendredi",
        6: "Samedi",
        7: "Dimanche",
      };
      return jours[dayWeek] || "Jour inconnu";
    };

    return {
      selectedPlayer,
      validateAndClose,
      selectPlayer,
      getJourLabel,
    };
  },
};


</script>

<style scoped>

.fixed.inset-0 {
  z-index: 1000 !important;
}

/* Contenu du pop-up */
.bg-white.p-8.rounded-lg.shadow-xl {
  z-index: 1100 !important;
}

.player-list {
  padding: 1rem;
  border-top: 1px solid #ddd;
}

.player-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #f8f9fa;
  padding: 10px;
  border-radius: 8px;
  margin-bottom: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

.player-card:hover {
  background: #e9ecef;
}

.player-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.modal-body ul {
  list-style-type: none;
  padding: 0;
}

.modal-body li {
  margin-top: 5px;
}

.level-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.level-slider {
  width: 100%;
  -webkit-appearance: none;
  height: 8px;
  border-radius: 5px;
  background: #ddd;
  outline: none;
  transition: background 0.3s;
}

.level-slider::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 18px;
  height: 18px;
  background: #528359;
  border-radius: 50%;
  cursor: pointer;
}

.level-slider::-moz-range-thumb {
  width: 18px;
  height: 18px;
  background: #528359;
  border-radius: 50%;
  cursor: pointer;
}

.level-display {
  font-weight: bold;
  color: #528359;
  font-size: 1.2rem;
}

</style>
