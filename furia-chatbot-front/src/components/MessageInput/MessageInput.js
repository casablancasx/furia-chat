import React, { useState, useRef, useEffect } from 'react';
import styles from './MessageInput.module.css';
import sendIconUrl from '../../assets/send_vector.svg'; // Import the SVG URL

function MessageInput({ onSendMessage, disabled }) {
  const [inputText, setInputText] = useState('');
  const textareaRef = useRef(null); 

  useEffect(() => {
    if (!disabled) {
      textareaRef.current?.focus();
    }
  }, [disabled]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!inputText.trim() || disabled) return;
    onSendMessage(inputText);
    setInputText('');
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      handleSubmit(e);
    }
  };

  return (
    <form className={styles.inputForm} onSubmit={handleSubmit}>
      <div className={styles.inputWrapper}>
        <textarea
          ref={textareaRef} 
          className={styles.inputArea}
          value={inputText}
          onChange={(e) => setInputText(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Type a message..."
          rows="1"
          disabled={disabled}
        />
        <button type="submit" className={styles.sendButton} disabled={disabled || !inputText.trim()}>
            <img 
              src={sendIconUrl} 
              alt="Send" 
              style={{ filter: 'invert(0%) sepia(0%) saturate(0%) hue-rotate(0deg) brightness(0%) contrast(100%)' }} 
            /> 
        </button>
      </div>
    </form>
  );
}

export default MessageInput;