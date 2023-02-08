FROM openjdk:17-alpine as builder

COPY ./mvnw .
COPY ./pom.xml .
COPY ./.mvn ./.mvn
COPY ./src ./src

RUN ./mvnw clean package

FROM openjdk:17-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder target/springbackend-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8080
ENV PORT $PORT_APP

EXPOSE $PORT

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "springbackend-0.0.1-SNAPSHOT.jar"]


