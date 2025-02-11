<template>
  <div class="bg-[#fefdf8] min-h-screen flex justify-center items-center">
    <div class="flex flex-col lg:flex-row shadow-xl border border-gray-300 rounded-lg overflow-hidden w-full max-w-[95%] lg:max-w-[1110px]">
      <!-- Logo Section -->
      <LogoSection />
      <!-- Login Form -->
      <LoginForm />
    </div>


  </div>
</template>

<script>
import LogoSection from '../components/login/LogoSection.vue';
import LoginForm from '../components/login/LoginForm.vue';


export default {
  components: {
    LogoSection,
    LoginForm,
  },
};
</script>

<style>
/* Import Tailwind styles */
</style>
