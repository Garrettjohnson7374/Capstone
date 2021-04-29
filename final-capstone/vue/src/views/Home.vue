<template>
  <div class="home">
    <h1 id="welcome">Welcome</h1>

    <div id="message">
      <p>Do you love your pet?</p>
      <p>Get them some friends!</p>
    </div>

    <router-link
      :to="{ name: 'login' }"
      id="login"
      tag="button"
      class="btn btn-primary"
      >Login</router-link
    >

    <div id="registration">
      <p>Need an account?</p>
      <router-link
        :to="{ name: 'register' }"
        tag="button"
        class="btn btn-primary"
        >Register</router-link
      >
    </div>

    <img id="leftDog" src="@/assets/left_dog.png" alt="white furry dog frolicking">
    <img id="rightDog" src="@/assets/right_dog.png" alt="brown and white dog with tennis ball">
  </div>
</template>

<script>
export default {
  name: "home",
};
</script>

<style scoped>
.home {
  display: grid;
  grid-template-columns: 1fr, 1fr, 1fr;

  gap: 30px;
  grid-template-areas:
    "welcome welcome welcome"
    "leftDog message rightDog"
    "leftDog login rightDog"
    ". registration .";
  align-items: center;
  justify-items: center;
  height: 100vh;
}

#leftDog {
  grid-area: leftDog;
}
#rightDog {
  grid-area: rightDog;
}

#leftDog, #rightDog {
  height: 50vh;
  border-radius: 2%;
}

#welcome {
  grid-area: welcome;
  margin-top: 40px;
  height: 10vh;
  font-size: 4rem;
}
#message {
  grid-area: message;
  font-size: 1.5rem;

}
#login {
  grid-area: login;
  width: 25vw;
  /* margin-bottom: 10px; */
}
#registration {
  grid-area: registration;
  margin-bottom: 50%;
  
  /* margin-bottom: 30px; */
  /* margin-bottom: 60vh; */
}

#registration >button{
  width: 25vw;
  margin-top: 0;
}
#message,
#registration {
  display: flex;
  flex-direction: column;
  align-items: center;
}
p,
button {
  margin: 0.5rem;
}
</style>
