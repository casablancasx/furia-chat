import axios from 'axios';
import { BACKEND } from '../global';


export async function sendMessageToApi(userMessage) {
  try {
    const response = await axios.post(BACKEND + "/api/chat", { userMessage });
    
    console.log("API Response:", response.data); 
    return response.data;

    


  } catch (error) {
    console.error("Error sending message to API:", error);
  }
}