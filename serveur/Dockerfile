FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /usr/src/project
COPY . .

RUN mvn clean package -DskipTests
RUN jar xf target/*.jar

RUN jdeps --ignore-missing-deps -q \
    --recursive \
    --multi-release 21 \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    target/*.jar > deps.info

RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /javaruntime

FROM debian:12.10-slim

ENV JAVA_HOME=/usr/java/jdk21
ENV PATH=$JAVA_HOME/bin:$PATH

COPY --from=builder /javaruntime $JAVA_HOME
COPY --from=builder /usr/src/project/target/*.jar /project/app.jar

WORKDIR /project

ARG USERNAME=appuser
ARG USER_UID=1000
ARG USER_GID=$USER_UID
RUN groupadd --gid $USER_GID $USERNAME && \
    useradd --uid $USER_UID --gid $USER_GID -m $USERNAME
USER $USERNAME

EXPOSE 8080

# ✅ Activation du profil ici comme dans ton ancien Dockerfile
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=integration"]