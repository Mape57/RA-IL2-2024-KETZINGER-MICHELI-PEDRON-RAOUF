<template>
  <div class="w-full bg-white flex justify-center items-center shadow-md p-4 lg:p-0 lg:w-[760px] h-auto lg:h-[510px]">
    <div class="text-center w-full lg:w-auto">
      <h1 class="text-[#1A4220] text-xl lg:text-2xl font-bold mb-4 lg:mb-6">Well Tennis Club</h1>
      <form class="space-y-3" @submit.prevent="handleSubmit">
        <input
            type="text"
            placeholder="Identifiant ou Adresse e-mail"
            class="w-full lg:w-[380px] h-[40px] border border-gray-300 rounded-lg p-2 shadow-sm focus:outline-none focus:ring-2 focus:ring-[#528359] text-sm"
            v-model="mailoruser"
        />

        <router-link
            type="submit"
            class="w-full lg:w-[380px] h-[40px] bg-[#528359] text-white text-sm rounded-lg shadow-md py-2 hover:bg-[#456c4c] transition mx-auto"
            to="/"
        >
          Envoyer un mail de récupération
        </router-link>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginForm',
  data() {
    return {
      mailoruser: '',
    };
  },
};
</script>
