FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

ARG REDISHOST
ARG REDISPORT
ARG REDISUSER
ARG REDISPASSWORD
ARG CRYPTOLANG
ARG CRYPTOKEY

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
