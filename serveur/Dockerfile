FROM maven:3.9.4-eclipse-temurin-21 AS builder

COPY pom.xml .
COPY /src ./src

RUN mvn clean install

FROM eclipse-temurin:21-jdk

COPY --from=builder /target/Well-Tennis-Club-1.jar Well-Tennis-Club-1.jar

EXPOSE 8080

CMD ["java", "-jar", "Well-Tennis-Club-1.jar","--spring.profiles.active=integration"]
