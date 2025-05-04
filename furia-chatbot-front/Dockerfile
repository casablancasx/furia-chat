FROM node:20-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install --legacy-peer-deps

RUN npm i -g serve

COPY . .

RUN npm run build

EXPOSE 8003

CMD ["serve", "-s", "dist", "-l", "8003"]