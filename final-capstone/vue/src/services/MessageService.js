import axios from 'axios';

export default {

  createMessage(message){
    return axios.post('/messages', message)
  },

  getListOfMessages(){
    return axios.get('/messages')
  },
}