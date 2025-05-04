import React, { useState } from 'react'; // Import useState
import ChatPage from './pages/ChatPage';
import Sidebar from './components/SideBar/SideBar';
import './App.css';
import { sendMessageToApi } from './services/chatService'; // Import the API service

function App() {
  const [messages, setMessages] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const handleSendMessage = async (inputText) => {
    if (!inputText.trim()) return;
    const userMessage = { sender: 'user', text: inputText };
    setMessages(prevMessages => [...prevMessages, userMessage]);
    setIsLoading(true);

    try {
      const apiResponse = await sendMessageToApi(inputText);
      const botMessage = {
        sender: 'bot',
        text: apiResponse.botResponse || "Resposta da API",
        intent: apiResponse.intent,
        context: apiResponse.context
      };
      setMessages(prevMessages => [...prevMessages, botMessage]);
    } catch (error) {
      console.error("Erro ao enviar mensagem para API:", error);
      const errorMessage = { sender: 'bot', text: "Desculpe, ocorreu um erro ao processar sua solicitação." };
      setMessages(prevMessages => [...prevMessages, errorMessage]);
    } finally {
      setIsLoading(false);
    }
  };

  const handleNewChat = () => {
    setMessages([]);
    setIsLoading(false); 
  };

  return (
    <div className="app-container">
      <Sidebar onNewChat={handleNewChat} />
      <div className="main-content">
        <ChatPage
          messages={messages}
          isLoading={isLoading}
          onSendMessage={handleSendMessage}
        />
      </div>
    </div>
  );
}

export default App;