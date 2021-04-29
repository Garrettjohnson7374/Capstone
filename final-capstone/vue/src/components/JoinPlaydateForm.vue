<template>
  <div>
    <div
      v-show="successMsg"
      class="alert alert-success playdate-joined-message"
      role="alert"
    >
      {{ successMsg }}
    </div>

    <div
      v-show="errorMsg"
      class="alert alert-danger playdate-error-message"
      role="alert"
    >
      {{ errorMsg }}
    </div>
    <div v-show="displayForm">
      <form class="form-user" id="form" v-on:submit.prevent="addPetToPlaydate">
        <p>Which pet would you like to join the playdate?</p>
        <div id="buttons">
          <label
            v-for="pet in $store.state.pets"
            v-bind:key="pet.petId"
            for="pet-name"
            class="label-pet-name"
          >
            <input
              class="input-pet-name"
              type="radio"
              id="pet-name"
              v-on:change="findPetByName"
              v-bind:value="pet.name"
              v-model="petName"
            />{{ pet.name }}</label
          >
        </div>
      </form>
      <button id="button-add-pet" class="btn btn-primary" type="submit">
          Join Playdate
        </button>
    </div>
  </div>
</template>

<script>
import playdateService from "@/services/PlaydateService";
import petService from "@/services/PetService";

export default {
  name: "JoinPlaydateForm",
  props: ["playdate"],
  created() {
    this.retrievePetList();
  },
  data() {
    return {
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
      displayForm: true,
      errorMsg: "",
      successMsg: "",
    };
  },
  methods: {
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
    findPetByName() {
      this.pet = this.$store.state.pets.find(
        (pet) => pet.name === this.petName
      );
    },
    addPetToPlaydate() {
      if (this.playdate.pet.name == this.pet.name) {
        this.errorMsg =
        this.playdate.pet.name + " is already hosting this playdate.";
        // this.showForm = false;
        // this.toggleDisplay();
      } else if (
        this.playdate.attendeeList.some(
          (attendee) => attendee.name == this.pet.name
        )
      ) {
        this.errorMsg = this.pet.name + " is already attending this playdate.";
        this.showForm = false;
        this.toggleDisplay();
      } else {
        playdateService
          .joinPlaydate(this.playdate.playdateId, this.pet.petId)
          .then((response) => {
            if (response.status === 201) {
              this.errorMsg = "";
              this.successMsg = "Playdate joined, yay!";
            }
            this.displayForm = false;

            this.pet = {};
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Playdate could not be joined. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Playdate could not be joined. Server could not be reached"
              );
            } else {
              alert(
                "Playdate could not be joined. Request could not be created."
              );
            }
          });
      }
    },
  },
};
</script>

<style>
.label-pet-name,
.input-pet-name {
  display: inline-block;
  margin: 0.5rem;
}
#form{
  display: flex;
  flex-direction: column;
  align-items: center;
}
#form p {
  text-align: center;
  margin-bottom: 0px
}

#form label {
  margin-left: auto;
  margin-right: auto;
}

button {
  width: 180px;
}

#form {
  margin-bottom: 30px;
}
</style>