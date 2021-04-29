<template>
  <div id="playdate">
    <playdate-details-large/>
  </div>
</template>

<script>
import PlaydateDetailsLarge from "../components/PlaydateDetailsLarge.vue";


export default {
  name: "joinPlaydate",
  components: {
    PlaydateDetailsLarge,
  },
  created(){

  },
  methods: {
   
  }
}
</script>

<style scoped>
#playdate{
  width: 70vw;
  margin-left: auto;
  margin-right: auto;
}

</style>