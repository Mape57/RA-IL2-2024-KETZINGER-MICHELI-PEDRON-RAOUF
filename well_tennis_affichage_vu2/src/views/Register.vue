<template>
  <div class="bg-[#fefdf8] min-h-screen flex justify-center items-center">
    <div class="flex flex-col lg:flex-row shadow-xl border border-gray-300 rounded-lg overflow-hidden w-full max-w-[105%] lg:max-w-[1140px]">
      <!-- Section Logo -->
      <LogoSection class="flex-shrink-0 h-auto lg:h-auto" />

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

