<template>
  <form class="form-user" v-on:submit.prevent="sendPlaydate">
    <div id="dateTimePicker">
      <label for="date-time" class="sr-only">Date and Time</label>
      <input
        type="datetime-local"
        id="date-time"
        name="dateTime"
        class="form-control"
        v-model="playdate.date"
        required
        autofocus
      />
    </div>

    <label for="address" class="sr-only">Address</label>
    <input
      type="text"
      id="address"
      class="form-control"
      placeholder="Street Address"
      v-model="playdate.address"
      required
    />

    <label for="city" class="sr-only">City</label>
    <input
      type="text"
      id="city"
      class="form-control"
      placeholder="City"
      v-model="playdate.city"
      required
    />

    <label for="state" class="sr-only">State</label>
    <input
      type="text"
      id="state"
      class="form-control"
      placeholder="State"
      v-model="playdate.state"
      required
    />

    <label for="zip" class="sr-only">Zip</label>
    <input
      type="text"
      id="zip"
      pattern="[0-9]*"
      class="form-control"
      placeholder="Zip Code"
      v-model="playdate.zip"
      required
    />

    <div id="selectPet">
      <div>
        <select
          name="petName"
          id="petName"
          v-on:click="findPetByName"
          v-model="petName"
          required
        >
          <option value="" default disabled>Pet Name</option>
          <option v-for="pet in $store.state.pets" v-bind:key="pet.petId"
          v-bind:value="pet.name">
            {{ pet.name }}
          </option>
        </select>
      </div>
    </div>

    <button class="btn btn-lg btn-primary form-submit" type="submit">
      Schedule Playdate
    </button>
  </form>
</template>

<script>
import playdateService from "@/services/PlaydateService.js";

export default {
  name: "playdateForm",
  props: ["button", "id"],
  data() {
    return {
      playdate: {
        playdate_id: "",
        pet_id: "",
        date: "",
        address: "",
        city: "",
        state: "",
        zip: "",
        pet: {},
        lat: "",
        lng: "",
        distanceFromUser: "",
      },
      petName: "",
    };
  },
  created() {
    this.completeFormForPlaydate();
  },
  computed: {
    today() {
      let today = new Date();
      let dd = String(today.getDate()).padStart(2, "0");
      let mm = String(today.getMonth() + 1).padStart(2, "0"); //January is 0
      let yyyy = today.getFullYear();

      return mm + "/" + dd + "/" + yyyy;
    },
  },
  methods: {
    sendPlaydate() {
      if (this.button === "Schedule") {
        playdateService
          .createPlaydate(this.playdate)
          .then((response) => {
            if (response.status === 201) {
              this.$router.push("/profile");
            }
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Playdate could not be scheduled. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Playdate could not be scheduled. Server could not be reached"
              );
            } else {
              alert(
                "Playdate could not be scheduled. Request could not be created."
              );
            }
          });
      } else if (this.button === "Update") {
        playdateService
        .updatePlaydate(this.id, this.playdate)
        .then((response) => {
          if (response.status === 200) {
            this.$router.push('/profile');
          }
        })
        .catch((error) => {
            if (error.response) {
              alert(
                "Playdate could not be updated. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert("Playdate could not be updated. Server could not be reached");
            } else {
              alert("Playdate could not be updated. Request could not be created.");
            }
          });
      }
    },
    findPetByName() {
      this.playdate.pet = this.$store.state.pets.find(
        (pet) => pet.name === this.petName
      );
    },
    completeFormForPlaydate() {
      if (this.button === "Update") {
        playdateService.getPlaydate(this.id).then((response) => {
          this.playdate = response.data;
          this.petName = this.playdate.pet.name;
        });
      }
    },
  },
};
</script>

<style>
.form-user {
  margin-top: 5vh;
}

#date-time {
  width: 350px;
  height: 40px;
  margin-bottom: 8px;
}

#dateTimePicker {
  display: flex;
  justify-content: center;
}

#selectPet {
  display: flex;
  justify-content: center;
}

#petName {
  width: 350px;
  height: 40px;
  border: 1px solid hsla(210, 6%, 67%, 0.5);
  border-radius: 3%;
}
</style>