FROM maven:3.6.1-jdk-11-slim AS build
COPY ./ ./
RUN mvn clean package -pl pong -am -DskipTests

FROM openjdk:11-jdk-slim
MAINTAINER lestokil
COPY --from=build /pong/target/pong-0.0.1-SNAPSHOT.jar /pong.jar
CMD ["java", "-jar", "/pong.jar"]
EXPOSE 8080
