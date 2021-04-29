<template>
  <div>
 
    <b-container id="join-playdate-info">
      <b-row align-h="center">
        <b-col cols="11" id="header-row">
          <h3>Playdate with {{ playdate.pet.name }}</h3>
        </b-col>
      </b-row>
  
      <b-row align-v="center" align-h="center" class="form-row">
        <b-col cols="4" id="pet-pic-container"
          ><img
            class="pet-pic"
            v-if="playdate.pet.pic"
            v-bind:src="playdate.pet.pic" />
          <img
            class="pet-pic default-pic"
            v-else
            src="@/assets/paws-default-white.png"
        /></b-col>

        <b-col cols="5" offset-md="1" id="pet-details"
          ><p v-if="playdate.pet.bio">{{ playdate.pet.bio }}</p>
          <p>
            {{ playdate.pet.energeticRelaxed }} |
            {{ playdate.pet.shyFriendly }} |
            {{ playdate.pet.apatheticCurious }}
          </p>
          <p>Species: {{ playdate.pet.species }}</p>
          <p v-if="playdate.pet.breed">Breed: {{ playdate.pet.breed }}</p>
          <p>
            Weight: {{ playdate.pet.weight }} pound{{
              playdate.pet.weight === 1 ? "" : "s"
            }}
          </p>
          <p v-if="age === 0">Less Than 1 Year Old</p>
          <p v-else>Age: {{ age }} year{{ age === 1 ? "" : "s" }}</p>
        </b-col>
      </b-row>
      <b-row align-h="center" class="form-row">
        <b-col cols="9" id="playdate-details">
          <p>
            <span class="bolded">Location: </span>{{ playdate.address }},
            {{ playdate.city }}, {{ playdate.state }} {{ playdate.zip }}
          </p>
          <p>
            <span class="bolded">Date & Time: </span>
            {{
              moment(this.playdate.date).format("dddd, MMMM Do YYYY, h:mm a")
            }}
          </p>
        </b-col>
      </b-row>

      <b-row align-v="center" align-h="center" class="form-row">
        <b-col cols="4" align-self="center" id="owner-details">
          <img
            class="owner-pic"
            v-if="playdate.owner.pic"
            v-bind:src="playdate.owner.pic"
          />
          <img class="owner-pic" v-else src="@/assets/paw-outline-light.png" />
        </b-col>
        <b-col cols="5" offset-md="1">
          <div id="owner-content">
            <h5>Owner</h5>
            <h6>
              {{ playdate.owner.firstName }} {{ playdate.owner.lastName }}
            </h6>
            <p v-if="playdate.owner.bio">{{ playdate.owner.bio }}</p>
          </div>
        </b-col>
      </b-row>
    </b-container>

    <div id="join-playdate" v-show="$store.state.token != ''">
      <join-playdate-form v-bind:playdate="playdate" />
    </div>

    <router-link
      :to="{ name: 'playdates' }"
      id="back-to-list"
      tag="button"
      class="btn btn-primary"
      >Back to Playdates List</router-link
    >
  </div>
</template>

<script>
import playdateService from "@/services/PlaydateService";
import JoinPlaydateForm from "@/components/JoinPlaydateForm.vue";

export default {
  name: "PlaydateDetailsLarge",
  components: {
    JoinPlaydateForm,
  },
  created() {
    this.getPlaydate();
  },
  data() {
    return {
      showForm: false,
      owner: {},
      playdate: {},
    };
  },
  computed: {
    age() {
      return new Date().getFullYear() - this.playdate.pet.birthYear;
    },
  },
  methods: {
    toggleShowForm() {
      if (this.showForm) {
        this.showForm = false;
      } else {
        this.showForm = true;
      }
    },
    getPlaydate() {
      playdateService.getPlaydate(this.$route.params.id).then((response) => {
        this.playdate = response.data;
      });
    },
  },
};
</script>

<style>

.pet-pic {
  max-width: 90%;
  border-radius: 3%;
}

.owner-pic {
  max-width: 50%;
}

#join-playdate-info {
  max-width: 50vw;
  margin-top: 50px;
}

h3 {
  text-align: center;
}

#owner-details {
  display: flex;
  justify-content: center;
}

#pet-pic-container {
  display: flex;
  justify-content: center;
}

.form-row {
  border-bottom: 2px solid hsla(240, 1%, 40%, 0.2);
  border-right: 2px solid hsla(240, 1%, 40%, 0.2);
  border-left: 2px solid hsla(240, 1%, 40%, 0.2);
  margin-bottom: 15px;
  padding: 20px;
}

#header-row {
 border-bottom: 2px solid hsla(240, 1%, 40%, 0.2);
 padding-bottom: 15px;
}

.bolded {
  font-weight: 500;
}

p {
  font-size: 1.1rem;
}

#back-to-list {
  margin-bottom: 80px;
}

</style>