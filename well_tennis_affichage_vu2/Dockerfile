# Étape 1 : Build de l'app
FROM node:18 AS build

WORKDIR /app

COPY . .

RUN npm install && npm run build

# Étape 2 : Serveur pour le frontend
FROM node:18

WORKDIR /app

# Copier seulement le dossier de build
COPY --from=build /app/dist ./dist
COPY --from=build /app/package.json ./
COPY --from=build /app/package-lock.json ./

RUN npm install -g serve

EXPOSE 3000

CMD ["serve", "-s", "dist", "-l", "3000"]
