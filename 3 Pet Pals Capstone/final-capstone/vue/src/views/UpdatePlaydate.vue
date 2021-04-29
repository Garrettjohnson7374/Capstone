<template>
  <div>
    <div class="outline">
      <h1 class="form-header">Update Playdate</h1>
      <playdate-form v-bind:button="text" v-bind:id="$route.params.id"></playdate-form>
    </div>
  </div>
</template>

<script>
import PlaydateForm from "../components/PlaydateForm.vue";

export default {
  name: "updatePlaydate",
  components: {
    PlaydateForm,
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