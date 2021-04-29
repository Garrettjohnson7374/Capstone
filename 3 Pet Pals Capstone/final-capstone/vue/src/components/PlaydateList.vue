<template>
  <div id="list">
    <h1>Playdates</h1>
    <div v-show="$store.state.token != ''">
      <button class="btn btn-primary" v-on:click="toggleDisplayType">
        {{ displayType === "List" ? "View Map" : "Back to List" }}
      </button>
    </div>

    <div id="filters" v-show="displayTypeIsList">
      <div id="species-group">
        <select
          name="species"
          id="species"
          v-model="filter.pet.species"
          v-on:change="toggleOtherSpecies"
        >
          <option value="" default selected disabled>Species</option>
          <option value="">View All</option>
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
          v-model="filter.pet.species"
          required
        />
      </div>

      <div id="energy-group">
        <select name="energy" id="energy" v-model="filter.pet.energeticRelaxed">
          <option value="" default selected disabled>Energy</option>
          <option value="">View All</option>
          <option value="Energetic">Energetic</option>
          <option value="Relaxed">Relaxed</option>
        </select>
      </div>

      <div id="timidity-group">
        <select name="timidity" id="timidity" v-model="filter.pet.shyFriendly">
          <option value="" default selected disabled>Timidity</option>
          <option value="">View All</option>
          <option value="Shy">Shy</option>
          <option value="Friendly">Friendly</option>
        </select>
      </div>

      <div id="curiosity-group">
        <select
          name="curiosity"
          id="curiosity"
          v-model="filter.pet.apatheticCurious"
        >
          <option value="" default selected disabled>Curiosity</option>
          <option value="">View All</option>
          <option value="Apathetic">Apathetic</option>
          <option value="Curious">Curious</option>
        </select>
      </div>

      <div id="distance-group" v-show="$store.state.token != ''">
        <select name="distance" id="distance" v-model="filter.distanceFromUser">
          <option value="" default selected disabled>Max Distance</option>
          <option value="">Any Distance</option>
          <option value="5">5 miles</option>
          <option value="10">10 miles</option>
          <option value="15">15 miles</option>
          <option value="20">20 miles</option>
          <option value="25">25 miles</option>
          <option value="50">50 miles</option>
        </select>
      </div>
      <div id="clear-filters">
        <button id="clear-filter" class="btn btn-primary" v-on:click="clearFilter">
          Clear Filters
        </button>
      </div>
    </div>

    <p v-show="filteredList.length == 0" id="no-results">No results found</p>

    <div id="list-view" v-if="displayTypeIsList">
      <div v-for="playdate in filteredList" v-bind:key="playdate.playdateId">
        <playdate-details v-bind:playdate="playdate" />
      </div>
    </div>
    <TravelMap
      v-else
      class="travel-map"
      v-bind:playdateList="filteredList"
      v-bind:markers="markers"
      v-show="filteredList.length != 0"
    />
  </div>
</template>
<script>
import playdateService from "@/services/PlaydateService";
import userService from "@/services/UserService";
import petService from "@/services/PetService";

import PlaydateDetails from "@/components/PlaydateDetails.vue";
import TravelMap from "@/components/TravelMap";

export default {
  name: "playdates",
  data() {
    return {
      displayType: "List",
      filter: {
        playdate_id: "",
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
        address: "",
        city: "",
        state: "",
        zip: "",
        date: "",
        lat: "",
        lng: "",
        distanceFromUser: "",
      },
      markers: [],
    };
  },
  created() {
    this.createPlaydatesList();
    this.retrieveAccountInfo();

  },

  components: {
    PlaydateDetails,
    TravelMap,
  },
  computed: {
    displayTypeIsList() {
      return this.displayType === "List";
    },
    filteredList() {
      let filteredPlaydates = this.$store.state.playdateList;
      const isListedOption =
        this.filter.pet.species === "Dog" ||
        this.filter.pet.species === "Cat" ||
        this.filter.pet.species === "Rabbit" ||
        this.filter.pet.species === "Hedgehog";
      if (this.filter.pet.species != "" && !isListedOption) {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) =>
            playdate.pet.species.toLowerCase() ==
            this.filter.pet.species.toLowerCase()
        );
      } else if (this.filter.pet.species != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) => playdate.pet.species == this.filter.pet.species
        );
      }

      if (this.filter.pet.energeticRelaxed != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) =>
            playdate.pet.energeticRelaxed == this.filter.pet.energeticRelaxed
        );
      }

      if (this.filter.pet.shyFriendly != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) => playdate.pet.shyFriendly == this.filter.pet.shyFriendly
        );
      }

      if (this.filter.pet.apatheticCurious != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) =>
            playdate.pet.apatheticCurious == this.filter.pet.apatheticCurious
        );
      }
      if (this.filter.distanceFromUser != "") {
        filteredPlaydates = filteredPlaydates.filter(
          (playdate) =>
            Number(playdate.distanceFromUser.split(" ")[0]) <=
            Number(this.filter.distanceFromUser)
        );
      }
      this.repopulateMarkers(filteredPlaydates);
      return filteredPlaydates;
    },
  },
  methods: {
    toggleDisplayType() {
      if (this.displayType === "List") {
        this.displayType = "Map";
      } else {
        this.displayType = "List";
      }
    },
    toggleOtherSpecies() {
      let otherSpeciesElement = document.getElementById("otherSpecies");
      if (this.filter.pet.species === "other") {
        this.filter.pet.species = "";
        otherSpeciesElement.classList.remove("d-none");
      } else {
        otherSpeciesElement.classList.add("d-none");
      }
    },
    retrieveAccountInfo() {
      if (this.$store.state.token != "") {
        userService
          .getProfile(this.$store.state.user.id)
          .then((response) => {
            if (response.data.firstName === null) {
              this.$router.push("/profile/create");
            } else {
              this.$store.commit("SET_ACCOUNT_INFO", response.data);
              this.retrievePetList();
            }
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Account information could not be found. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Account information could not be found. Server could not be reached"
              );
            } else {
              alert(
                "Account information could not be found. Request could not be created."
              );
            }
          });
      }
    },
    createPlaydatesList() {
      playdateService
        .getListOfPlaydates()
        .then((response) => {
          this.$store.commit("SET_PLAYDATE_LIST", response.data);
          this.populateMarkers();
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Playdates list could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert(
              "Playdates list could not be found. Server could not be reached"
            );
          } else {
            alert(
              "Playdates list could not be found. Request could not be created."
            );
          }
        });
    },
    populateMarkers() {
      this.filteredList.forEach((element) => {
        let marker = {
          id: element.playdateId,
          position: { lat: Number(element.lat), lng: Number(element.lng) },
        };
        this.markers.push(marker);
      });
    },
    repopulateMarkers(list) {
      this.markers = [];
      list.forEach((element) => {
        const marker = {
          id: element.playdateId,
          position: { lat: Number(element.lat), lng: Number(element.lng) },
        };
        this.markers.push(marker);
      });
    },
    clearFilter() {
      this.filter = {
        playdate_id: "",
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
        address: "",
        city: "",
        state: "",
        zip: "",
        date: "",
        lat: "",
        lng: "",
        distanceFromUser: "",
      };
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
  },
};
</script>

<style scoped>
.travel-map {
  height: 80%;
  width: 80%;
  margin: 10px;
  align-self: center;
}
#clear-filter {
  margin-top: 0px;
}

#list {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 70vw;
  justify-content: flex-start;
  margin-left: auto;
  margin-right: auto;
}

h1 {
  text-align: center;
  margin-top: 45px;
  margin-bottom: 30px;
}

#filters {
  display: flex;
  justify-content: space-around;
  margin-bottom: 3vh;
  font-size: 1.1rem;
  margin-top: 3vh;
}


#species,
#energy,
#timidity,
#curiosity,
#distance {
  border: 1px solid hsla(210, 6%, 67%, 0.6);
  border-radius: 3%;
  height: 35px;
  width: 11vw;
  max-width: 250px;
  padding-left: 5px;
}

#otherSpecies {
  margin-top: 2vh;
  width: 18vw;
  max-width: 250px;
}

#no-results {
  text-align: center;
  margin-top: 5vh;
}
</style>