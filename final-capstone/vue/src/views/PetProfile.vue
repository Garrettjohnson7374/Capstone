<template>
  <div>
    <pet v-bind:id="$route.params.id"></pet>
  </div>
</template>
<script>

import Pet from "@/components/Pet";

export default {
  name: "petProfile",

  components: {
    Pet,
  },
computed: {

}
};
</script>