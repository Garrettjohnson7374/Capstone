import axios from 'axios';

export default {

  registerPet(pet){
    return axios.post('/pets/register', pet)
  },

  // list(){
  //   return axios.get('/pets')
  // },
  
  getPet(id){
    return axios.get(`/pets/${id}`)
  },

  updatePet(id, pet){
    return axios.put(`/pets/${id}`, pet)
  },

  getPetsByUserId() {
    return axios.get('/pets')
  }
}