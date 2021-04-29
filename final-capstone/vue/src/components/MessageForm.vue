<template>
  <div id="create-message">
    <form class="message-form" v-on:submit.prevent="postMessage">
      <label for="message" class="sr-only">First Name</label>

      <textarea
        id="message-text-area"
        name="message"
        rows="8"
        cols="60"
        class="form-control"
        placeholder="Type Your Message Here..."
        v-model="message.message"
        required
        autofocus
      ></textarea>

      <p>Are you posting about a pet?</p>
      <label
        v-for="pet in $store.state.pets"
        v-bind:key="pet.petId"
        for="pet-name"
        class="label-pet-name"
      >
        <input
          class="input-pet-name"
          type="radio"
          name="pet-name"
          v-on:change="findPetByName()"
          v-bind:value="pet.name"
          v-model="petName"
        />{{ pet.name }}</label
      >

      <button class="btn btn-lg btn-primary form-submit" type="submit">
        Post Message
      </button>
      <div id="extra-space"></div>
    </form>
  </div>
</template>

<script>
import messageService from "@/services/MessageService";
import petService from "@/services/PetService";
export default {
  name: "userForm",
  props: ["button"],
  data() {
    return {
      message: {
        message: "",
        petId: "",
      },
      pet: {
        petId: "",
        name: "",
        userId: "",
        species: "",
        breed: "",
        weight: "",
        birthYear: "",
        energeticRelaxed: "",
        shyFriendly: "",
        apatheticCurious: "",
        bio: "",
        pic: "",
      },
      petName: "",
    };
  },
  created() {
    this.retrievePetList();
  },

  methods: {
    findPetByName() {
      this.pet = this.$store.state.pets.find(
        (pet) => pet.name === this.petName
      );
    },
    retrievePetList() {
      petService
        .getPetsByUserId()
        .then((response) => {
          this.$store.commit("SET_PETS", response.data);
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Pet list could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert("Pet list could not be found. Server could not be reached");
          } else {
            alert("Pet list could not be found. Request could not be created.");
          }
        });
    },
    postMessage() {
      this.message.petId = this.pet.petId;
      messageService
        .createMessage(this.message)
        .then((response) => {
          if (response.status === 201) {
            this.$router.push("/messageforum");
          }
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Message could not be posted. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert("Message could not be posted. Server could not be reached");
          } else {
            alert("Message could not be posted. Request could not be created.");
          }
        });
    },
  },
};
</script>

<style scoped>
.label-pet-name,
.input-pet-name {
  display: inline-block;
  margin: 0.5rem;
}

#message-text-area {
  margin-top: 20vh;
}
#create-message {
  display: flex;
  justify-content: center;
}
.message-form p {
  margin-top: 10px;
}
.personality p {
  display: inline-block;
  margin: 0.5rem;
  font-family: "Raleway", sans-serif;
  font-weight: 600;
}

textarea {
  border: 2px solid hsla(240, 1%, 40%, 0.2);
}
</style>