<template>
  <div>
    <div id="list">
      <h1>Messages</h1>

      <p v-show="messageList.length == 0" id="no-results">No results found</p>

      <div id="message-filters">
        <select
          name="petName"
          id="pet-name"
          v-on:click="retrievePetNames"
          v-model="filter.pet.name"
        >
          <option value="" default selected disabled>Pet Hashtag</option>
          <option value="">View All</option>
          <option
            v-for="name in listOfPets"
            v-bind:key="name"
            v-bind:value="name"
          >
            {{ name }}
          </option>
        </select>

        <select
          name="user"
          id="user"
          v-on:click="retrieveUsers"
          v-model="filter.name"
        >
          <option value="" default selected disabled>Username</option>
          <option value="">View All</option>
          <option
            v-for="name in listOfUsers"
            v-bind:key="name"
            v-bind:value="name"
          >
            {{ name }}
          </option>
        </select>
        <div id="clear-filters">
          <button id="clear-filter" class="btn btn-primary" v-on:click="clearFilter">
            Clear Filters
          </button>
        </div>
      </div>

    <p v-show="filteredList.length == 0" id="no-results">No results found</p>

    </div>

    <div id="list-view">
      <div v-for="message in filteredList" v-bind:key="message.messageId">
        <message-detail v-bind:message="message" />
      </div>

      <router-link
        :to="{ name: 'writeMessage' }"
        id="writeMessage"
        tag="button"
        class="btn btn-primary"
        >Write Message</router-link
      >
    </div>
  </div>
</template>

<script>
import messageService from "@/services/MessageService";
import MessageDetail from "./MessageDetail.vue";

export default {
  name: "messageforum",
  data() {
    return {
      listOfPets: [],
      petName: "",
      username: "",
      listOfUsers: [],
      sortedFilteredMessages: [],
      filter: {
        messageId: "",
        userId: "",
        message: "",
        name: "",
        pic: "",
        postedDate: "",
        pet: {
          petId: "",
          name: "",
          pic: "",
          shyFriendly: "",
          energeticRelaxed: "",
          breed: "",
          birthYear: "",
          bio: "",
          apatheticCurious: "",
        },
      },
    };
  },
  created() {
    this.createMessageList();
  },

  components: { MessageDetail },
  computed: {
    displayTypeIsList() {
      return this.displayType === "List";
    },
    messageList() {
      let listOfMessages = this.$store.state.messageList;
      return listOfMessages;
    },
    filteredList() {
      let filteredMessages = this.messageList;
      if (this.filter.pet.name != "") {
        filteredMessages = filteredMessages.filter(
          (message) => message.pet.name == this.filter.pet.name
        );
      }
      if (this.filter.name != "") {
        filteredMessages = filteredMessages.filter(
          (message) => message.name == this.filter.name
        );
      }
      return filteredMessages;
    },
  },
  methods: {
    createMessageList() {
      messageService
        .getListOfMessages()
        .then((response) => {
          console.log(response);
          this.$store.commit("SET_MESSAGE_LIST", response.data);
          this.$store.commit("SORT_MESSAGE_LIST");
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Messages list could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert(
              "Messages list could not be found. Server could not be reached"
            );
          } else {
            alert(
              "Messages list could not be found. Request could not be created."
            );
          }
        });
    },
    retrievePetNames() {
      this.messageList.forEach((message) => {
        if (message.pet.name && !this.listOfPets.includes(message.pet.name)) {
          this.listOfPets.push(message.pet.name);
        }
      });
    },
    retrieveUsers() {
      this.messageList.forEach((message) => {
        if (message.name && !this.listOfUsers.includes(message.name)) {
          this.listOfUsers.push(message.name);
        }
      });
    },
    clearFilter() {
      this.filter = {
        messageId: "",
        userId: "",
        message: "",
        name: "",
        pic: "",
        postedDate: "",
        pet: {
          petId: "",
          name: "",
          pic: "",
          shyFriendly: "",
          energeticRelaxed: "",
          breed: "",
          birthYear: "",
          bio: "",
          apatheticCurious: "",
        },
      }
    },
  },
};
</script>

<style scoped>
#list {
  margin-bottom: 45px;
}

h1 {
  text-align: center;
  margin-top: 45px;
  margin-bottom: 55px;
}

#no-results {
  text-align: center;
  margin-top: 5vh;
}

#list-view {
  display: flex;
  align-items: center;
  flex-direction: column;
}

#writeMessage {
  margin-bottom: 80px;
}

#user,
#pet-name {
  width: 220px;
  height: 35px;
  border: 1px solid hsla(210, 6%, 67%, 0.5);
  border-radius: 3%;
  margin-bottom: 5px;
}

#message-filters {
  display: flex;
  justify-content: center;
}

#clear-filter {
  margin-top: 0px;
}

#user,
#pet-name,
#clear-filter {
  margin-right: 25px;
}
</style>