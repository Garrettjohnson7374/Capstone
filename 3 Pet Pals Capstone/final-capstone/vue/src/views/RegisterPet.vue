<template>
  <div id="register" class="text-center">
    <div class="outline">
      <h1 class="form-header">Register Your Pet</h1>
      <pet-form v-bind:button="text"></pet-form>
    </div>
  </div>
</template>

<script>
import petForm from "@/components/PetForm";

export default {
  name: "registerPet",
  components: {
    petForm,
  },
  data() {
    return {
      text: "Register",
    };
  },
  created() {
    this.clearPet();
  },
  methods: {
    clearPet() {
      this.$store.commit("CLEAR_CURRENT_PET");
    },
  },
};
</script>

<style scoped>
#register {
  background-color: #fafafa;
}
</style>
