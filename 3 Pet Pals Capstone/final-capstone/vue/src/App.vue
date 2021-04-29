<template>
  <div id="app">
    <head>
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,300;0,400;0,600;1,400&display=swap"
      rel="stylesheet"
    />
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap" rel="stylesheet">
    </head>
    <header>
      <nav id="nav1">
        <router-link v-bind:to="{name: 'profile'}" v-show="$store.state.token != ''">Home</router-link>
        <router-link v-bind:to="{ name: 'home' }" v-show="$store.state.token == ''">Home</router-link
        >
        <span v-show="$store.state.token != ''">&nbsp;|&nbsp;</span>
        <router-link
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link>

      </nav>
    

        <h1>Pet Pals</h1>

      <nav id="nav2">

        <router-link v-bind:to="{name: 'playdates'}">Playdates</router-link>
        <span v-show="$store.state.token != ''">&nbsp;|&nbsp;</span>
        <router-link v-bind:to="{name: 'messageforum'}" v-show="$store.state.token != ''">Message Forum</router-link>
      </nav>
    </header>
    <router-view />
  </div>
</template>




<style>
body{
  color: #2a2b2a !important;
  background-color: #FAFAFA !important;
}

/* * {
  border-style: dotted;
  border-color: red;
} */

/* * {
  border: 0.01px dotted hsla(0, 0%, 100%, 0.0);
} */

#app {
  background-color: #FAFAFA; 
}
header {
  display: flex !important;
  flex-direction: row;
  align-items: center; 
  justify-content: space-between;
  background-color: #07475f;
  color: #FAFAFA;
}
nav a {
  color: #FAFAFA;
  padding-left: 10px;
  padding-right: 10px;
}

nav a:hover, .router-link-exact-active {
  text-decoration: none;
  color: #f89e7a;
}


#nav1 {
  margin-left: 20px;
}

header >nav, header>h1 {
  flex-basis: 50%;
}
header>h1 {
  text-align: center;
}
header>nav {
  display: flex;
}
#nav2{
  justify-content: flex-end;
  margin-right: 20px;
}

.btn {
  display: block !important;
  margin: auto;
  margin-top: 10px;
  background: #07475f !important;
  border-color: #07475f !important;
}

.btn:hover {
  background: #1e7c9e !important;
  border-color: #1e7c9e !important;
}

h1, h2, h3, h4, h5, h6 {
  font-family: "Raleway", sans-serif;
}

input[type="text"],
input[type="password"],
input[type="number"],
input[type="url"],
input[type="tel"],
input[type="email"] {
  width: 350px;
  margin: auto;
  margin-bottom: 5px;
}

.outline {
  border: 1px solid #c4cad0;
  border-radius: 0.25rem;
  margin: 50px auto 300px auto;
  padding: 30px 25px;
  background-color: #fff;
  box-shadow: 1px 3px 7px 0 rgba(134, 130, 130, 0.4),
    0 1px 2px 0 rgba(56, 56, 56, 0.3);
  width: 40vw;
  max-width: 600px;
}

.outline:hover {
  background-color: #FAFAFA;
}

.form-header {
  margin-top: 15px;
  margin-bottom: 20px;
  font-size: 1.6rem;
  text-align: center;
}

.form-submit {
  margin-top: 20px;
  margin-bottom: 15px;
}

.default-pic {
  border: 1px solid hsla(240, 1%, 40%, 0.2);
  border-radius: 2%;
}

</style>
