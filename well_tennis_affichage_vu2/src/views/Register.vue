<template>
  <div class="bg-[#fefdf8] flex justify-center items-center p-4 lg:min-h-[100dvh]">
    <div class="flex flex-col lg:flex-row shadow-xl border border-gray-300 rounded-lg overflow-hidden w-full max-w-[1140px] h-[calc(100dvh-2rem)] sm:h-auto">
      <!-- Section Logo -->
      <LogoSection class="flex-shrink-0 h-[200px] sm:h-[300px] md:h-[400px] lg:h-[510px]" />

      <!-- Formulaire d'inscription -->
      <RegisterForm class="flex-grow" />
    </div>
  </div>
</template>

<script>
import LogoSection from "../components/login/LogoSection.vue";
import RegisterForm from "../components/register/RegisterForm.vue";

export default {
  name: "RegisterPage",
  components: {
    LogoSection,
    RegisterForm,
  },
};
</script>

