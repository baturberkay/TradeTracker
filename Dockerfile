FROM openjdk:21-jdk-slim AS build
WORKDIR /TradeTracker
LABEL authors="baturdumanay"

COPY . .

RUN ./mvnw clean package -DskipTests

FROM openjdk:21
WORKDIR /TradeTracker

COPY --from=build /TradeTracker/target/TradeTracker.jar TradeTracker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "TradeTracker.jar"]