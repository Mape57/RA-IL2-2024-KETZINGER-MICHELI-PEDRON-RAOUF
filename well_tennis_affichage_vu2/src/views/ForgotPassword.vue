<template>
  <div class="bg-[#f5f0e8] min-h-screen flex justify-center items-center">
    <div class="flex flex-col lg:flex-row shadow-xl border border-gray-300 rounded-lg overflow-hidden w-full max-w-[95%] lg:max-w-[1110px]">
      <!-- Logo Section -->
      <LogoSection />
      <!-- Login Form -->
      <ForgotPasswordSection />
    </div>

  </div>
</template>

<script>
import LogoSection from '../components/login/LogoSection.vue';
import ForgotPasswordSection from "../components/login/ForgotPasswordSection.vue";


export default {
  components: {
    LogoSection,
    ForgotPasswordSection,
  },
};
</script>

<style>
/* Import Tailwind styles */
</style>
