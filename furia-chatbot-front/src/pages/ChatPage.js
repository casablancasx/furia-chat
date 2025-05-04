import React, { useEffect, useRef } from 'react'; // Removed useState
import WelcomeScreen from '../components/WelcomeScreen/WelcomeScreen';
import MessageInput from '../components/MessageInput/MessageInput';
import styles from './ChatPage.module.css';

function ChatPage({ messages, isLoading, onSendMessage }) {

  const messagesEndRef = useRef(null);
  const scrollContainerRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth", block: "end" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);


  const handleWelcomeExampleClick = (text) => {
    onSendMessage(text);
  };

  return (
    <div className={styles.pageContainer}>
      <div ref={scrollContainerRef} className={styles.messagesScrollArea}>
        <div className={styles.messagesContent}>
          {messages.length === 0 && !isLoading && <WelcomeScreen onExampleClick={handleWelcomeExampleClick} />}
          {messages.map((msg, index) => (
            <div key={index} className={`${styles.message} ${styles[msg.sender]}`}>
              {msg.sender === 'bot' && msg.intent === 'LAST_NEWS' && Array.isArray(msg.context) ? (
                <>
                  {msg.text}
                  <ul style={{ paddingLeft: '20px', marginTop: '10px', marginBottom: '0' }}>
                    {msg.context.map((item, ctxIndex) => (
                      <li key={ctxIndex} style={{ marginBottom: '5px' }}>
                        <a
                          href={item.source_link}
                          target="_blank"
                          rel="noopener noreferrer"
                          style={{ color: '#007bff', textDecoration: 'underline' }}
                        >
                          {item.title}
                        </a>
                      </li>
                    ))}
                  </ul>
                </>
              ) : (
                msg.text
              )}
            </div>
          ))}
          {isLoading && <div className={`${styles.message} ${styles.loading}`}>Digitando...</div>}
          <div ref={messagesEndRef} style={{ height: '1px' }} />
        </div>
      </div>
      <div className={styles.inputAreaWrapper}>
        {/* Pass the onSendMessage prop and isLoading prop */}
        <MessageInput onSendMessage={onSendMessage} disabled={isLoading} />
      </div>
    </div>
  );
}

export default ChatPage;