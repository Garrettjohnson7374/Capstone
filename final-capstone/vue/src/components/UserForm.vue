<template>
  <form class="form-user" v-on:submit.prevent="sendForm">
      <label for="first-name" class="sr-only">First Name</label>
      <input
        type="text"
        id="first-name"
        class="form-control"
        placeholder="First Name"
        v-model="user.firstName"
        required
        autofocus
      />

      <label for="last-name" class="sr-only">Last Name</label>
      <input
        type="text"
        id="last-name"
        class="form-control"
        placeholder="Last Name"
        v-model="user.lastName"
        required
      />

      <label for="email" class="sr-only">Email</label>
      <input
        type="email"
        id="email"
        class="form-control"
        placeholder="Email Address"
        v-model="user.email"
        required
      />

      <label for="phone" class="sr-only">Phone</label>
      <input
        type="tel"
        id="phone"
        pattern="\d{3}[\-]\d{3}[\-]\d{4}"
        class="form-control"
        placeholder="Phone Number: 123-456-7891"
        v-model="user.phone"
      />

      <label for="address" class="sr-only">Address</label>
      <input
        type="text"
        id="address"
        class="form-control"
        placeholder="Street Address"
        v-model="user.address"
        required
      />

      <label for="city" class="sr-only">City</label>
      <input
        type="text"
        id="city"
        class="form-control"
        placeholder="City"
        v-model="user.city"
        required
      />

      <label for="state" class="sr-only">State</label>
      <input
        type="text"
        id="state"
        class="form-control"
        placeholder="State"
        v-model="user.state"
        required
      />

      <label for="zip" class="sr-only">Zip</label>
      <input
        type="text"
        id="zip"
        pattern="[0-9]*"
        class="form-control"
        placeholder="Zip Code"
        v-model="user.zip"
        required
      />

      <label for="bio" class="sr-only">Bio</label>
      <input
        type="text"
        id="bio"
        class="form-control"
        placeholder="Bio"
        v-model="user.bio"
      />

      <label for="pic" class="sr-only">Pic</label>
      <input
        type="url"
        id="pic"
        class="form-control"
        placeholder="Image URL of Profile Pic"
        v-model="user.pic"
      />

      <button class="btn btn-lg btn-primary form-submit" type="submit">
        {{ button }} Profile
      </button>
    <div id="extra-space"></div>
  </form>
</template>

<script>
import userService from "../services/UserService";
export default {
  name: "userForm",
  props: ["button"],
  data() {
    return {
      user: {
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        address: "",
        city: "",
        state: "",
        zip: "",
        bio: "",
        pic: "",
      },
    };
  },
  created() {
    this.completeFormForUser();
  },

  methods: {
    sendForm() {
      if (this.button === "Create") {
        userService
          .createProfile(this.user)
          .then((response) => {
            if (response.status === 201) {
              this.$router.push("/profile");
            }
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Profile could not be created. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Profile could not be created. Server could not be reached"
              );
            } else {
              alert(
                "Profile could not be created. Request could not be created."
              );
            }
          });
      } else if (this.button === "Update") {
        userService
          .updateProfile(this.$store.state.user.id, this.user)
          .then((response) => {
            if (response.status === 200) {
              this.$router.push("/profile");
            }
          })
          .catch((error) => {
            if (error.response) {
              alert(
                "Profile could not be updated. Response was " +
                  error.response.statusText
              );
            } else if (error.request) {
              alert(
                "Profile could not be updated. Server could not be reached"
              );
            } else {
              alert(
                "Profile could not be updated. Request could not be created."
              );
            }
          });
      }
    },

    completeFormForUser() {
      if (this.button === "Update") {
        userService.getProfile(this.$store.state.user.id).then((response) => {
          this.user = response.data;
        });
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
</style>