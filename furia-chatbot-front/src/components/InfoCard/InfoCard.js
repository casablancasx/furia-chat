import React from 'react';
import styles from './InfoCard.module.css';

function InfoCard({ title, items, IconComponent, onItemClick }) {

  return (
    <div className={styles.card}>
      <div className={styles.header}>
        {IconComponent && <IconComponent className={styles.icon} />}
        <h3 className={styles.title}>{title}</h3>
      </div>
      <ul className={styles.list}>
        {items.map((item, index) => (
          <li
            key={index}
            className={styles.listItem}
            onClick={() => onItemClick && onItemClick(item)}
          >
            {item}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default InfoCard;