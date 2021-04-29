<template>
  <form class="form-registerPet" v-on:submit.prevent="sendForm">
      <label for="name" class="sr-only">Pet Name</label>
      <input
        type="text"
        id="name"
        class="form-control"
        placeholder="Name"
        v-model="pet.name"
        required
        autofocus
      />

      <select
        name="species"
        id="species"
        v-model="pet.species"
        v-on:change="toggleOtherSpecies"
      >
        <option value="" default disabled>Species</option>
        <option value="Dog">Dog</option>
        <option value="Cat">Cat</option>
        <option value="Rabbit">Rabbit</option>
        <option value="Hedgehog">Hedgehog</option>
        <option value="other">Other (please describe)</option>
      </select>

      <input
        type="text"
        id="otherSpecies"
        class="form-control d-none"
        placeholder="Other Species Type"
        v-model="pet.species"
        required
      />

      <label for="breed" class="sr-only">breed</label>
      <input
        type="text"
        id="breed"
        class="form-control"
        placeholder="Breed"
        v-model="pet.breed"
      />
      <label for="weight" class="sr-only">weight</label>
      <input
        type="number"
        id="weight"
        min="0"
        class="form-control"
        placeholder="Weight in pounds"
        v-model="pet.weight"
        required
      />
      <label for="birthYear" class="sr-only">BirthYear</label>
      <input
        type="number"
        id="birthYear"
        class="form-control"
        placeholder="Birth Year"
        min="1970"
        max="2021"
        value=""
        v-model="pet.birthYear"
        required
      />

      <div class="personality">
        <p>Energy:</p>
        <label for="energetic">
          <input
            type="radio"
            id="Energetic"
            name="energetic_relaxed"
            v-model="pet.energeticRelaxed"
            value="energetic"
          />
          Energetic</label
        >

        <label for="relaxed">
          <input
            type="radio"
            id="Relaxed"
            name="energetic_relaxed"
            v-model="pet.energeticRelaxed"
            value="relaxed"
          />
          Relaxed</label
        >
      </div>

      <div class="personality">
        <p>Timidity:</p>
        <label for="shy">
          <input
            type="radio"
            id="Shy"
            name="shy_friendly"
            v-model="pet.shyFriendly"
            value="shy"
          />
          Shy
        </label>
        <label for="friendly">
          <input
            type="radio"
            id="Friendly"
            name="shy_friendly"
            v-model="pet.shyFriendly"
            value="friendly"
          />
          Friendly
        </label>
      </div>

      <div class="personality">
        <p>Curiosity:</p>
        <label for="apathetic">
          <input
            type="radio"
            id="Apathetic"
            name="apathetic_curious"
            v-model="pet.apatheticCurious"
            value="apathetic"
          />
          Apathetic</label
        >

        <label for="curious">
          <input
            type="radio"
            id="Curious"
            name="apathetic_curious"
            v-model="pet.apatheticCurious"
            value="curious"
          />
          Curious</label
        >
      </div>

      <label for="bio" class="sr-only">Bio</label>
      <input
        type="text"
        id="bio"
        class="form-control"
        placeholder="Bio"
        v-model="pet.bio"
      />

      <label for="pic" class="sr-only">Pic</label>
      <input
        type="url"
        id="pic"
        class="form-control"
        placeholder="Image URL of Pet"
        v-model="pet.pic"
      />
    <button class="btn btn-lg btn-primary form-submit" type="submit">
      {{ button }} Pet
    </button>
  </form>
</template>

<script>
import petService from "../services/PetService";
export default {
  name: "petForm",
  props: ["button"],
  data() {
    return {
      pet: {
        name: "",
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
    };
  },
  created() {
    this.completeFormForPet();
  },

  methods: {
    sendForm() {
      if (this.button === "Register") {
        petService
          .registerPet(this.pet)
          .then((response) => {
            if (response.status === 201) {
              const petId = response.data;
              this.$router.push(`/pets/${petId}`);
            }
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Pet could not be registered. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert("Pet could not be registered. Server could not be reached");
            } else {
              alert(
                "Pet could not be registered. Request could not be created."
              );
            }
          });
      } else if (this.button === "Update") {
        const petId = this.$route.params.id;
        petService
          .updatePet(petId, this.pet)
          .then((response) => {
            if (response.status === 200) {
              this.$router.push(`/pets/${petId}`);
            }
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Pet could not be updated. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert("Pet could not be updated. Server could not be reached");
            } else {
              alert("Pet could not be updated. Request could not be created.");
            }
          });
      }
    },
    toggleOtherSpecies() {
      let otherSpeciesElement = document.getElementById("otherSpecies");
      if (this.pet.species === "other") {
        this.pet.species = "";
        otherSpeciesElement.classList.remove("d-none");
      } else {
        otherSpeciesElement.classList.add("d-none");
      }
    },
    completeFormForPet() {
      if (this.button === "Update") {
        petService.getPet(this.$route.params.id).then((response) => {
          this.pet = response.data;
          this.checkUser();
        });
      }
    },
    checkUser() {
      if (this.$store.state.user.id != this.pet.userId) {
        this.$router.push("/profile");
      }
    },
  },
};
</script>

<style scoped>
.personality {
  display: block;
}
.personality label {
  display: inline-block;
  margin: 0.5rem;
}

.personality p {
  display: inline-block;
  margin: 0.5rem;
  font-family: "Raleway", sans-serif;
  font-weight: 600;
}

.species {
  height: 100%;
  width: 307px;
}

#species {
  width: 350px;
  height: 35px;
  border: 1px solid hsla(210, 6%, 67%, 0.5);
  border-radius: 3%;
  margin-bottom: 5px;
}


</style>