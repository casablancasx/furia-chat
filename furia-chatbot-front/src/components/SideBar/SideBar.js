import React from 'react';
import { FiPlus, FiMessageSquare, FiTrash2, FiSun, FiUser, FiHelpCircle, FiLogOut } from 'react-icons/fi';
import styles from './SideBar.module.css';

const Sidebar = ({ onNewChat }) => {
  return (
    <div className={styles.sidebar}>
      <button className={styles['new-chat-button']} onClick={onNewChat}>
        <FiPlus />New chat
      </button>
      <div className={styles['chat-list']}>
        <div className={styles['chat-item']}>
          <FiMessageSquare /> Furia AI Chat
        </div>
      </div>
      <div className={styles['sidebar-bottom']}>
        <div className={styles['sidebar-action']}>
          <FiSun /> Light mode
        </div>
        <div className={styles['sidebar-action']}>
          <FiUser /> My account
        </div>
        <div className={styles['sidebar-action']}>
          <FiHelpCircle /> Updates & FAQ
        </div>
        <div className={styles['sidebar-action']}>
          <FiLogOut /> Log out
        </div>
      </div>
    </div>
  );
};

export default Sidebar;