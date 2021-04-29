<template>
  <div id="register" class="text-center">
    <div class="outline">
      <h1 class="form-header">Create Profile</h1>
      <user-form v-bind:button="text"></user-form>
    </div>
  </div>
</template>

<script>
import userForm from "@/components/UserForm";

export default {
  name: "createProfile",
  components: {
    userForm,
  },
  data() {
    return {
      text: "Create",
    };
  },
};
</script>

<style scoped>

</style>