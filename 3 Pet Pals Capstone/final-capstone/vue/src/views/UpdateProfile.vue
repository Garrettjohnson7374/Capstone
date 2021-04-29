<template>
  <div id="update" class="text-center">
    <div class="outline">
      <h1 class="form-header">Update Account Information</h1>
      <user-form v-bind:button="text"></user-form>
    </div>
  </div>
</template>

<script>
import userForm from "@/components/UserForm";

export default {
  name: "updateProfile",
  components: {
    userForm,
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