<template>
  <div class="info-container relative">
    <button class="close-button absolute top-2 right-2" @click="$emit('close')">✕</button>
    <h2 class="title">Informations Complètes du Joueur</h2>
    <div class="info-card">
      <div><strong>Nom :</strong> {{ player.name }}</div>
      <div><strong>Prénom :</strong> {{ player.surname }}</div>
      <div><strong>Email :</strong> {{ player.email }}</div>
      <div><strong>Niveau :</strong> {{ player.level }}</div>
      <div><strong>Nombre de cours par semaine :</strong> {{ player.weeklySessions }}</div>
      <div><strong>Date de naissance :</strong> {{ player.birthday }}</div>
      <div><strong>Âge :</strong> {{ computeAge(player.birthday) }} ans</div>
      <div><strong>Disponibilité :</strong> {{ player.availability }}</div>
    </div>
  </div>
</template>


<script>
import usePlayers from "../../useJs/usePlayers.js";

export default {
  name: "PlayerInfoView",
  props: {
    player: {
      type: Object,
      required: true,
    },
  },
  setup() {
    const { computeAge } = usePlayers();

    return {
      computeAge, // Utilisé dans le template
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
</style>
