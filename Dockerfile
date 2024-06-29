FROM openjdk:21
LABEL authors="xyz"

RUN mvn clean install

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tradeTracker.jar
ENTRYPOINT ["java", "-jar", "tradeTracker.jar"]