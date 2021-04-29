<template>
  <div id="detail-view" class="card">
    <img
      v-if="message.pic"
      class="profile-pic"
      v-bind:src="message.pic"
      alt="user profile picture"
    />
    <img
      class="default-profile-pic"
      v-else
      src="@/assets/default-user-pic.jpg"
      alt="profile picture"
    />

    <div id="message-container" class="card-body">
      <p class="card-title">
        <span class="message-header">Username: </span>
        {{ message.name }}
      </p>

      <p class="card-text">
        <span class="message-header">Message: </span>{{ message.message }}
      </p>
    </div>

    <div id="message-info">
      <p id="date-posted" v-show="message.postedDate">
        {{ moment(message.postedDate).format("MMMM Do YYYY, h:mm a") }}
      </p>
      <p id="pet-tag" v-show="message.pet.name">#{{ message.pet.name }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "messageDetail",
  props: ["message"],
};
</script>

<style>

#detail-view {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 55vw;
  margin-bottom: 10px;
  border: 2px solid hsla(240, 1%, 40%, 0.2);
}

#detail-view:hover {
  background-color: #fafafa;
}

.message-header {
  font-weight: 600;
}

.default-profile-pic {
  max-width: 100px;
  border-radius: 5%;
  align-self: center;
  padding: 13px;
}

.profile-pic {
  max-width: 100px;
  border-radius: 5%;
  align-self: center;
  padding: 20px;
}

#message-info {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-right: 15px;
  justify-content: space-between;
  margin-top: 5px;
}

#pet-tag {
  color: #cd704c;
}

#date-posted {
  color: hsla(196, 86%, 20%, 0.75);
  font-weight: 600;
}

#message-container {
    flex-basis: 50%;
}

</style>