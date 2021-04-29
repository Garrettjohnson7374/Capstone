<template>
  <div id="scheduled-playdates-card">
    <div v-show="$route.path != '/profile'">
      <h3>Scheduled Playdates</h3>
    </div>

    <div v-show="playdateList.length == 0">
      <p id="no-playdates">No playdates at the moment</p>
    </div>

    <div
      class="playdate-card"
      v-for="playdate in playdateList"
      v-bind:key="playdate.playdateId"
    >
      <div id="scheduled-playdate-grid">
        <h4>Playdate hosted by {{ playdate.pet.name }}</h4>
        <p id="playdate-date-time">
          <span class="playdate-header"> Date/Time:</span>
          {{ moment(playdate.date).format("dddd, MMMM Do YYYY, h:mm a") }}
        </p>
        <p id="playdate-address">
          <span class="playdate-header">Location:</span> {{ playdate.address }},
          {{ playdate.city }},
          {{ playdate.state }}
          {{ playdate.zip }}
        </p>
        <div id="attendee-list-group">
          <h5 v-show="playdate.attendeeList.length != 0">Attendees:</h5>
          <div
            v-for="attendee in playdate.attendeeList"
            v-bind:key="attendee.petId"
            id="attendee-list-block"
          >
            <p id="attendee-list-names">{{ attendee.name }}</p>
          </div>
        </div>

        <div id="playdate-buttons">
          <div
            v-show="
              isHost(playdate.playdateId) && playdate.attendeeList.length == 0
            "
          >
            <button v-on:click="updatePlaydate(playdate.playdateId)">
              Update Playdate
            </button>
          </div>

          <div
            v-show="
              confirmCancelMsg && playdate.playdateId == playdateIdToCancel
            "
            class="alert alert-danger confirm-cancel-msg"
            role="alert"
          >
            {{ confirmCancelMsg }}
          </div>

          <div
            v-show="
              confirmCancelMsg && playdate.playdateId == playdateIdToCancel
            "
          >
            <form
              class="form-user"
              id="form-cancel-playdate"
              v-on:submit.prevent="cancel(playdate.playdateId)"
            >
              <div id="cancel-playdate-buttons">
                <button v-on:click="keepPlaydate">Keep Playdate</button>
                <button type="submit">Cancel Playdate</button>
              </div>
            </form>
          </div>

          <div v-show="isHost(playdate.playdateId) && !confirmCancelMsg">
            <button v-on:click="confirmCancel(playdate.playdateId)">
              <!-- <button v-on:click="cancel(playdate.playdateId)"> -->
              Cancel Playdate
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import playdateService from "@/services/PlaydateService";
export default {
  name: "scheduledPlaydates",
  props: ["petId"],
  data() {
    return {
      playdateList: [],
      confirmCancelMsg: "",
      playdateIdToCancel: "",
    };
  },
  created() {
    this.getPlaydates();
  },
  methods: {
    getPlaydates() {
      return playdateService
        .getScheduledPlaydates(this.petId)
        .then((response) => {
          this.playdateList = response.data;
        });
    },
    isHost(playdateId) {
      let result = false;
      this.playdateList.forEach((playdate) => {
        if (playdate.playdateId == playdateId) {
          if (this.petId == playdate.pet.petId) {
            result = true;
          }
        }
      });
      return result;
    },
    confirmCancel(playdateId) {
      this.confirmCancelMsg = "Are you sure you want to cancel this playdate?";
      this.playdateIdToCancel = playdateId;
    },
    keepPlaydate() {
      this.confirmCancelMsg = "";
    },
    updatePlaydate(id) {
      this.$router.push(`/playdates/${id}/update`);
    },
    cancel(playdateId) {
      if (this.confirmCancelMsg) {
        return playdateService
          .cancelPlaydate(playdateId)
          .then((response) => {
            if (response.status === 200) {
              location.reload();
            }
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Playdate could not be cancelled. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Playdate could not be cancelled. Server could not be reached"
              );
            } else {
              alert(
                "Playdate could not be cancelled. Request could not be created."
              );
            }
          });
      }
    },
  },
};
</script>

<style scoped>
.playdate-card {
  border: 2px solid hsla(240, 1%, 40%, 0.2);
  border-radius: 1.5%;
  padding: 15px;
  margin-bottom: 10px;
}

#scheduled-playdate-grid {
  display: grid;
  grid-template-columns: 1fr, 1fr;
  grid-template-areas:
    "host host"
    "dt dt"
    "location location"
    "attendees links";
}

.playdate-header {
  font-weight: 500;
}

h4 {
  font-size: 1.1rem;
  font-style: italic;
  grid-area: host;
}

#playdate-date-time {
  grid-area: dt;
}

#playdate-address {
  grid-area: location;
}

#attendee-list-group {
  grid-area: attendees;
}

#playdate-buttons {
  grid-area: links;
}

h5 {
  font-size: 1.1rem;
}

#attendee-list-names::before {
  content: "\2022";
  color: #a7acb1;
  font-weight: bold;
  display: inline-block;
  width: 1em;
}

#attendee-list-names {
  margin-left: 1em;
  margin-bottom: 1px;
}

h3 {
  margin-bottom: 20px;
  font-family: "Rock Salt", cursive;
  color: #949494;
  font-size: 1.4rem;
  text-align: center;
}

button {
  background-color: transparent;
  border: none;
  color: #cd704c;
}

button:hover {
  color: #07475f;
}

#playdate-buttons {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

#scheduled-playdates-card {
  min-width: 24vw;
}

#cancel-playdate-buttons {
  display: flex;
  flex-direction: column;
}

#scheduled-playdate-grid h4 {
  color: #949494;
  margin-bottom: 15px;
}

#no-playdates {
  color: #949494;
  text-align: center;
  border: 2px solid hsla(240, 1%, 40%, 0.2);
  padding: 10px;
}

</style>