import axios from 'axios';

export default {
    createProfile(user){
        return axios.post('/profile', user)
      },

    updateProfile(id, user){
        return axios.put(`/profile/${id}`, user)
    },

    getProfile(id){
        return axios.get(`/profile/${id}`)
      },

    getAllProfiles() {
      return axios.get('/profile-list')
    }

}