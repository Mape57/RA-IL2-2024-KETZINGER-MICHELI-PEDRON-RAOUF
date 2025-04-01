# Étape 1 : build Java + jlink
FROM maven:3.9.9-eclipse-temurin-21 AS builder

ARG EMAIL
ARG PASSWORD
ARG TRAINER_EMAIL
ARG BASE_URL
RUN echo "-> URL: $BASE_URL"

WORKDIR /usr/src/project
COPY . .

# Compilation sans exécuter les tests
RUN mvn clean package -DskipTests

# Extraction du contenu du JAR pour analyse
RUN jar xf target/*.jar

# Détection des modules nécessaires pour jlink
RUN jdeps --ignore-missing-deps -q \
    --recursive \
    --multi-release 21 \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    target/*.jar > deps.info

# Construction d’un runtime Java allégé
RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /javaruntime

# Étape 2 : image finale légère et sécurisée
FROM debian:12.10-slim

ENV JAVA_HOME=/usr/java/jdk21
ENV PATH=$JAVA_HOME/bin:$PATH

COPY --from=builder /javaruntime $JAVA_HOME
COPY --from=builder /usr/src/project/target/*.jar /project/app.jar

WORKDIR /project

# Ajout d’un utilisateur non-root
ARG USERNAME=appuser
ARG USER_UID=1000
ARG USER_GID=$USER_UID
RUN groupadd --gid $USER_GID $USERNAME && \
    useradd --uid $USER_UID --gid $USER_GID -m $USERNAME
USER $USERNAME

# Port par défaut exposé (optionnel)
EXPOSE 8080

RUN echo "Test ENV: $TRAINER_EMAIL"

ENTRYPOINT ["java", "-jar", "app.jar"]