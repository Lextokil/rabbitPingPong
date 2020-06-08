FROM maven:3.6.3-jdk-11-slim AS build
COPY ./ ./
RUN mvn clean package -pl ping:ping -am -DskipTests -e

FROM openjdk:11.0.3-jre-slim-stretch
MAINTAINER cadu
COPY --from=build /ping/target/ping-0.0.1-SNAPSHOT.jar /ping.jar
ENTRYPOINT ["java", "-jar", "/ping.jar"]
EXPOSE 8090
