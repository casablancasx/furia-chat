import React from 'react';
import InfoCard from '../InfoCard/InfoCard';
import styles from './WelcomeScreen.module.css';
import { FaUsers, FaTrophy, FaInfoCircle } from 'react-icons/fa'; 

const timeExamples = [
  "Quem é o coach atual da FURIA?",
  "Qual a posição da FURIA no ranking mundial?",

];

const jogadoresExamples = [
  "Quem são os jogadores titulares da FURIA?",
  "Quais foram as mudanças recentes no elenco?",
];

const partidasExamples = [
  "Qual será a próxima partida da FURIA?",
  "Qual o próximo campeonato que a FURIA disputará?",
];


function WelcomeScreen({ onExampleClick }) {
  return (
    <div className={styles.welcomeContainer}>
      <img src="https://furiagg.fbitsstatic.net/sf/img/logo-furia.svg?theme=main&amp;v=202503171541" alt="Logo da Furia" width="160" fetchpriority="high"></img>
      <div className={styles.infoGrid}>
        <InfoCard
          items={timeExamples}
          IconComponent={FaInfoCircle} 
          onItemClick={onExampleClick}
        />
        <InfoCard
          items={jogadoresExamples}
          IconComponent={FaUsers} 
          onItemClick={onExampleClick}
        />
        <InfoCard
          items={partidasExamples}
          IconComponent={FaTrophy} 
          onItemClick={onExampleClick}
        />
      </div>
    </div>
  );
}

export default WelcomeScreen;