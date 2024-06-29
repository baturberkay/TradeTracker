FROM openjdk:21
LABEL authors="baturdumanay"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TradeTracker.jar
ENTRYPOINT ["java", "-jar", "TradeTracker.jar"]