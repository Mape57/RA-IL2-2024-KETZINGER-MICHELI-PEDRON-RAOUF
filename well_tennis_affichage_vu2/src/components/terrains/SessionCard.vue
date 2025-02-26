<template>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <div>
    <div
        v-if="!isMobile"
        class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 shadow-sm mb-4 flex lg:flex-row items-center"
    >
      <div class="text-custom-color font-bold text-lg lg:w-[10%] text-center lg:border-r border-gray-800 lg:pr-4 mb-4 lg:mb-0 flex flex-col justify-center items-center">
        <p>{{ startTime }}</p>
        <p>{{ endTime }}</p>
      </div>

      <div class="w-[25%] h-[20%] lg:pl-2 mb-4 lg:mb-0 flex flex-col justify-center">
        <div class="mb-2">
          <span class="font-bold text-sm">Entraîneur :</span> {{ coach }}
        </div>
        <div class="mb-2">
          <span class="font-bold text-sm">Âge :</span> {{ ageGroup }} ans
        </div>
        <div>
          <span class="font-bold text-sm">Niveau :</span> {{ skillLevel }}
        </div>
      </div>

      <div class="flex-1 pl-2 ml-0">
        <div class="flex mt-1">
          <div class="pr-1">
            <ul class="list-disc list-inside text-sm text-gray-700">
              <li v-for="(player, index) in firstHalfPlayers" :key="index">{{ player }}</li>
            </ul>
          </div>

          <div class="w-1/2 pl-2">
            <ul class="list-disc list-inside text-sm text-gray-700">
              <li v-for="(player, index) in secondHalfPlayers" :key="index">{{ player }}</li>
            </ul>
          </div>
        </div>
      </div>

      <div class="lg:w-[10%] flex justify-end" v-if="userRole === 'ADMIN'">

        <button @click="$emit('delete')" class="delete-button">
          <span class="material-icons delete-icon">delete</span>
          <span class="delete-text">Supprimer</span>
        </button>



      </div>
    </div>

    <div
        v-else
        class="session-card bg-gray-50 border border-gray-300 rounded-lg p-4 shadow-sm mb-4 flex flex-col"
    >
      <div class="flex justify-between items-center border-b border-gray-300 pb-2 mb-2">
        <div class="text-custom-color font-bold text-lg">
          {{ coach }}
        </div>
        <div class="text-custom-color text-sm font-medium" @click="showInfo = !showInfo">
          {{ showInfo ? (ageGroup + ' ans, Niveau: ' + skillLevel) : (startTime + ' - ' + endTime) }}
        </div>
      </div>

      <div class="grid no-gap-grid">
        <span
            v-for="(player, index) in players.slice(0, 8)"
            :key="index"
            class="text-sm text-gray-700"
        >
          • {{ player }}
        </span>
      </div>

      <div class="mt-1 flex justify-end" v-if="userRole === 'ADMIN'">
        <button @click="$emit('delete')" class="delete-button">
          <span class="material-icons delete-icon">delete</span>
          <span class="delete-text">Supprimer</span>
        </button>


      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SessionCard",
  props: {
    startTime: {
      type: String,
      required: true,
    },
    endTime: {
      type: String,
      required: true,
    },
    coach: {
      type: String,
      required: true,
    },
    ageGroup: {
      type: String,
      required: true,
    },
    skillLevel: {
      type: String,
      required: true,
    },
    players: {
      type: Array,
      required: true,
    },
    userRole: String,
  },
  data() {
    return {
      isMobile: false,
      showInfo: false,
    };
  },
  computed: {
    firstHalfPlayers() {
      const half = Math.ceil(this.players.length / 2);
      return this.players.slice(0, half);
    },
    secondHalfPlayers() {
      const half = Math.ceil(this.players.length / 2);
      return this.players.slice(half);
    },
  },
  methods: {
    checkScreenSize() {
      this.isMobile = window.innerWidth <= 1024;
    },
  },
  mounted() {
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
};
</script>

<style scoped>
.delete-button {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  color: #e3342f;
  font-size: 18px;
  font-weight: bold;
  text-decoration: none;
  position: relative;
  transition: color 0.2s ease-in-out;
}

.delete-icon {
  font-size: 20px;
  line-height: 1;
  vertical-align: middle;
  position: relative;
  top: 1px;
}

.delete-text {
  font-size: 18px;
  line-height: 1;
  display: flex;
  align-items: center;
}

.delete-button::after {
  content: "";
  position: absolute;
  left: 5px;
  bottom: -2px;
  width: 96%;
  height: 2px;
  background-color: #e3342f;
  transform: scaleX(0);
  transition: transform 0.2s ease-in-out;
}

.delete-button:hover::after {
  transform: scaleX(1);
}



</style>