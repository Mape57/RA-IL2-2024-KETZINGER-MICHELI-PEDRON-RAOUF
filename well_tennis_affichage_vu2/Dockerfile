FROM node:18-slim AS build

RUN apt-get update && apt-get install -y build-essential

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

RUN npm run build

FROM node:18-slim

RUN useradd -m appuser

WORKDIR /app

COPY --from=build /app/dist ./dist
COPY --from=build /app/package*.json ./

RUN npm install --omit=dev

RUN npm install -g serve

USER appuser

EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=10s \
  CMD curl -f http://localhost:3000 || exit 1

CMD ["serve", "-s", "dist", "-l", "3000"]