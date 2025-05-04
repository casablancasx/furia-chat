# FURIA Chatbot - Assistente Inteligente sobre CS2

## Visão Geral

Este projeto implementa um chatbot inteligente focado em fornecer informações sobre o time de Counter-Strike 2 (CS2) da FURIA Esports. O chatbot é capaz de responder perguntas sobre jogadores, partidas (anteriores e futuras), campeonatos, transferências, notícias recentes e informações gerais sobre o time, utilizando dados atualizados obtidos através de web scraping do site HLTV.org e processamento de linguagem natural com um modelo de IA externo.

## Funcionalidades Principais

*   **Consulta de Informações do Time:** Ranking, país, títulos conquistados.
*   **Detalhes sobre Jogadores:** Jogadores atuais (titulares e banco), nacionalidade, status, estatísticas básicas.
*   **Informações do Coach:** Nickname, estatísticas, tempo na equipe.
*   **Histórico de Partidas:** Resultados de jogos anteriores, incluindo adversário, placar, data e evento.
*   **Próximas Partidas:** Informações sobre os próximos jogos agendados.
*   **Transferências:** Mudanças recentes no elenco (entradas, saídas, banco).
*   **Campeonatos:** Próximos campeonatos que a FURIA participará.
*   **Últimas Notícias:** Links para as notícias mais recentes sobre o time de CS2 da FURIA.
*   **Interação em Linguagem Natural:** Compreensão da intenção do usuário e geração de respostas contextuais.

## Arquitetura

O projeto segue uma arquitetura de microsserviços:

1.  **`furia-chatbot-front` (Frontend):** Interface de usuário desenvolvida em React (com Vite) onde o usuário interage com o chatbot.
2.  **`furia-chat-bot-backend` (Backend Principal):** Aplicação Spring Boot (Java) que orquestra a comunicação. Recebe mensagens do frontend, chama o serviço de intenção, busca dados no serviço de scraping, interage com o modelo de IA (OpenRouter) para gerar respostas e retorna o resultado para o frontend.
3.  **`furia-intent-service` (Serviço de Intenção):** Serviço Python responsável por analisar a mensagem do usuário e identificar a sua intenção (ex: perguntar sobre jogadores, próxima partida, etc.).
4.  **`web-scraping-hltv` (Serviço de Scraping):** Serviço Python que realiza a coleta de dados (web scraping) do site HLTV.org e os disponibiliza através de uma API REST para o backend principal.

## Tecnologias Utilizadas

*   **Backend:** Java 21, Spring Boot 3, Spring WebFlux (Reactive), Maven
*   **Frontend:** React, Vite, JavaScript, CSS Modules
*   **Serviço de Intenção:** Python, Poetry, (Framework a ser especificado, ex: FastAPI, Flask)
*   **Serviço de Scraping:** Python, Poetry, Cloudscraper, (Framework a ser especificado, ex: FastAPI, Flask)
*   **IA:** OpenRouter API (utilizando modelos como `deepseek/deepseek-prover-v2`)
*   **Banco de Dados:** Não aplicável (dados obtidos via scraping em tempo real ou cacheado nos serviços)
*   **Containerização:** Docker (Dockerfiles presentes nos diretórios dos serviços), Docker Compose

## Configuração e Instalação

**Pré-requisitos:**
*   Java JDK 17 ou superior
*   Maven
*   Node.js e npm
*   Python 3.10+ e Poetry
*   Docker e Docker Compose (Recomendado para execução)
*   Chave da API OpenRouter (configurada em `furia-chat-bot-backend/src/main/resources/application.properties`)

**Passos:**

1.  **Clonar o repositório:**
    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd <DIRETORIO_DO_PROJETO>
    ```

2.  **Configurar Backend (`furia-chat-bot-backend`):**
    *   Navegue até o diretório: `cd furia-chat-bot-backend`
    *   Verifique e atualize a chave da API OpenRouter e a URL base dos outros serviços em `src/main/resources/application.properties` se necessário.
    *   Compile o projeto (necessário para a primeira build do Docker ou execução manual): `mvnw clean install` (ou `mvn clean install`)

3.  **Configurar Frontend (`furia-chatbot-front`):**
    *   Navegue até o diretório: `cd ../furia-chatbot-front`
    *   Instale as dependências (necessário para a primeira build do Docker ou execução manual): `npm install`
    *   Verifique a URL da API do backend em `src/services/chatService.js` se necessário (para execução manual). A configuração do Docker geralmente lida com isso através da rede interna.

4.  **Configurar Serviço de Intenção (`furia-intent-service`):**
    *   Navegue até o diretório: `cd ../furia-intent-service`
    *   Instale as dependências (necessário para a primeira build do Docker ou execução manual): `poetry install`

5.  **Configurar Serviço de Scraping (`web-scraping-hltv`):**
    *   Navegue até o diretório: `cd ../web-scraping-hltv`
    *   Instale as dependências (necessário para a primeira build do Docker ou execução manual): `poetry install`
    *   Configure variáveis de ambiente se houver um arquivo `.env` (verificar necessidade).

## Executando o Projeto

Existem duas maneiras principais de executar o projeto: usando Docker Compose (recomendado para facilidade e consistência) ou iniciando cada serviço manualmente.

**1. Usando Docker Compose (Recomendado)**

**Pré-requisitos:**
*   Docker e Docker Compose instalados.

**Passos:**

1.  **Navegue até a raiz do projeto:**
    ```bash
    cd <DIRETORIO_DO_PROJETO>
    ```
2.  **Construa e inicie os containers:**
    ```bash
    docker-compose up --build -d
    ```
    *   O comando `--build` garante que as imagens Docker sejam construídas (ou reconstruídas se houver alterações).
    *   O comando `-d` executa os containers em segundo plano (detached mode).

3.  **Acessar a Aplicação:**
    *   O frontend estará acessível em `http://localhost:8003`.
    *   A documentação da API do backend (Swagger UI) estará em `http://localhost:8002/swagger-ui.html`.

4.  **Parar os containers:**
    ```bash
    docker-compose down
    ```

**2. Executando Manualmente (Para Desenvolvimento/Debug)**

Siga esta abordagem se precisar executar os serviços individualmente fora de containers. Certifique-se de que todos os pré-requisitos listados na seção "Configuração e Instalação" estão atendidos. Inicie os serviços na ordem especificada abaixo em terminais separados:

1.  **Serviço de Scraping (`web-scraping-hltv`):**
    *   Roda na porta 8000.
    ```bash
    cd web-scraping-hltv
    poetry run uvicorn main:app --host 0.0.0.0 --port 8000 --reload
    ```

2.  **Serviço de Intenção (`furia-intent-service`):**
    *   Roda na porta 8001.
    ```bash
    cd ../furia-intent-service
    poetry run uvicorn main:app --host 0.0.0.0 --port 8001 --reload
    ```

3.  **Backend Principal (`furia-chat-bot-backend`):**
    *   Roda na porta 8002.
    ```bash
    cd ../furia-chat-bot-backend
    ./mvnw spring-boot:run
    # Ou no Windows: mvnw spring-boot:run
    ```

4.  **Frontend (`furia-chatbot-front`):**
    *   Roda na porta 8003 (ou outra porta indicada pelo Vite).
    ```bash
    cd ../furia-chatbot-front
    npm run dev
    ```
    *   Acesse a aplicação no endereço fornecido pelo Vite (geralmente `http://localhost:8003`).

## API Endpoints

*   **Backend Principal (`furia-chat-bot-backend`):**
    *   `POST /api/chat`: Endpoint principal para enviar mensagens do usuário e receber respostas do bot.
    *   Swagger UI: Acesse `http://localhost:8002/swagger-ui.html` (após iniciar o backend) para documentação detalhada da API.
*   **Serviço de Scraping (`web-scraping-hltv`):**
    *   `/team`: Retorna informações gerais do time.
    *   `/matches/upcoming`: Retorna próximas partidas.
    *   `/matches/previous`: Retorna partidas anteriores.
    *   `/transfers`: Retorna informações de transferências.
    *   `/championships/active`: Retorna campeonatos ativos/próximos.
    *   `/news/latest`: Retorna as últimas notícias.
*   **Serviço de Intenção (`furia-intent-service`):**
    *   `POST /intent`: Recebe texto e retorna a intenção detectada.

## Contribuição

Contribuições são bem-vindas! Siga os passos abaixo:
1.  Faça um fork do projeto.
2.  Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3.  Faça commit das suas alterações (`git commit -m 'Adiciona nova feature'`).
4.  Faça push para a branch (`git push origin feature/nova-feature`).
5.  Abra um Pull Request.