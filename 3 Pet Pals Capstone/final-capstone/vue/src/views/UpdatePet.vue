<template>
  <div id="update" class="text-center">
    <div class="outline">
      <h1 class="form-header">Update Your Pet</h1>
      <pet-form v-bind:button="text"></pet-form>
    </div>
  </div>
</template>

<script>
import petForm from "@/components/PetForm";

export default {
  name: "updatePet",
  components: {
    petForm,
  },
  data() {
    return {
      text: "Update",
    };
  },
};
</script>

<style scoped>

</style>