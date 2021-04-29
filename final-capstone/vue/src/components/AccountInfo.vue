<template>
  <div>
    <div id="accountInfo">
      <div class="card">
        <img
          class="card-img-top account-profile-pic"
          v-if="account.pic"
          v-bind:src="account.pic"
          alt="profile picture"
        />
        <img
          class="card-img-top account-profile-pic"
          v-else
          src="@/assets/default-user-pic.jpg"
          alt="profile picture"
        />
        <div class="card-body">
          <h3 class="card-title">
            {{ account.firstName }} {{ account.lastName }}
          </h3>
          <div class="card-text">
            <h4>
              <span v-show="account.bio" class="account-header">Bio: </span
              >{{ account.bio }}
            </h4>
            <p>
              <span class="account-header">Email: </span> {{ account.email }}
            </p>
            <p>
              <span v-show="account.phone" class="account-header">Phone: </span
              >{{ account.phone }}
            </p>
            <div>
              <p>
                <span class="account-header">Address: </span
                >{{ account.address }}
              </p>
              <p>{{ account.city }}, {{ account.state }} {{ account.zip }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <router-link
      :to="{ name: 'updateProfile' }"
      id="updateProfile"
      tag="button"
      class="btn btn-primary"
      >Update Account Info</router-link
    >
  </div>
</template>

<script>
export default {
  name: "accountInfo",
  computed: {
    account() {
      return this.$store.state.accountInfo;
    },
  },
};
</script>

<style scoped>
img {
  width: 75%;
  max-width: 250px;
  min-width: 150px;
  border-radius: 2%;
  margin: 20px 10px 10px 10px;
  padding: 10px;
  border: 1px solid hsla(240, 1%, 40%, 0.2);
}
h4 {
  font-style: italic;
  font-size: 1rem;
}
#accountInfo {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 3vh;
}

.card {
  display: flex;
  align-items: center;
  padding: 20px;
}

h3 {
  font-size: 1.2rem;
  font-weight: 600;
  text-align: center;
  margin-bottom: 25px;
}

.account-header {
  font-weight: 600;
}

.card-text {
  display: flex;
  flex-direction: column;
  align-content: space-between;
}
</style>