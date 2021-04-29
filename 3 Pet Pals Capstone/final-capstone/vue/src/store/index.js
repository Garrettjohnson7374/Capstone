import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    pets: [],
    pet: {
      petId: '',
      name: '',
      userId: '',
      species: '',
      breed: '',
      weight: '',
      birthYear: '',
      energeticRelaxed: '',
      shyFriendly: '',
      apatheticCurious: '',
      bio: '',
      pic: '',
    },
    accountInfo: {
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      address: '',
      city: '',
      state: '',
      zip: '',
      bio: '',
      pic: '',
      lat: '',
      lng: '',
    },
    playdateList: [],
    messageList: [],
    users: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_PETS(state, data){
      state.pets = data;
    },
    SET_CURRENT_PET(state, data) {
      state.pet = data;
    },
    CLEAR_CURRENT_PET(state) {
      state.pet = {
        petId: '',
        name: '',
        userId: '',
        species: '',
        breed: '',
        weight: '',
        birthYear: '',
        energeticRelaxed: '',
        shyFriendly: '',
        apatheticCurious: '',
        bio: '',
        pic: '',
      }
    },
    SET_ACCOUNT_INFO(state, data) {
      state.accountInfo = data;
    },
    SET_PLAYDATE_LIST(state, data){
      state.playdateList = data;
    },
    SET_MESSAGE_LIST(state, data) {
      state.messageList = data;
    },
    SET_USERS_LIST(state, data) {
      state.users = data;
    },
    SORT_MESSAGE_LIST(state) {
      state.messageList.sort((a, b) => (a.messageId > b.messageId) ? 1 : -1);
    }
    // ADD_PLAYDATE_DISTANCE(state, data){
    //   const index = state.playdateList.findIndex(element => element.playdateId == data.id)
    //   state.playdateList[index].distance = data.distance;
    // }
  }
})
