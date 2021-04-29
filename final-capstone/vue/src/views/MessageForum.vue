<template>
  <div>
    <message-list />
  </div>
</template>

<script>
import messageList from "../components/MessageList.vue";
import userService from "@/services/UserService";
import messageService from "@/services/MessageService";


export default {
  name: "messageforum",
  components: {
    messageList,
  },
  created() {
    this.retrieveAccountInfo;
    this.retrieveMessages;
  },
  methods: {
    retrieveAccountInfo() {
      userService
        .getProfile(this.$store.state.user.id)
        .then((response) => {
          if (response.data.firstName === null) {
            this.$router.push("/profile/create");
          } else {
            this.$store.commit("SET_ACCOUNT_INFO", response.data);
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
    },
    retrieveMessages() {
      messageService
        .getListOfMessages()
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          if (error.response) {
            alert(
              "Messages could not be found. Response was " +
                error.response.statusText
            );
          } else if (error.request) {
            alert("Messages could not be found. Server could not be reached");
          } else {
            alert("Messages could not be found. Request could not be created.");
          }
        });
    },
  },
};
</script>