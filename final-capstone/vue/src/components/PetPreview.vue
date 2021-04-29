<template>
  <div id="preview">
    <div class="card">
    <h3 class="card-title">{{ pet.name }}</h3>
    <img class="pet-pic card-img-top" v-if="pet.pic" v-bind:src="pet.pic" alt="pet profile picture"/>
    <img class="pet-pic card-img-top default-pic" v-else src="@/assets/paws-default-white.png" alt="pet profile picture"/>
    </div>
    </div>
</template>

<script>
export default {
  name: "petPreview",
  props: ["pet"],
};
</script>

<style scoped>
img {
    margin-top: 10px;
    width: 90%;
    max-width: 300px;
    margin-bottom: 25px;
}

.card {
  display: flex;
  align-items: center;
}

h3 {
    margin-top: 10px;
    font-size: 1.4rem;
}

#preview {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 1.5vh;
    background-color: #FAFAFA !important;
}

.card:hover {
  background-color: #c4cad0;
  border-radius: 2%;
  cursor: pointer;
}

/* .default-pic {
  border: 1px solid hsla(240, 1%, 40%, 0.2);
  border-radius: 2%;
} */

</style>