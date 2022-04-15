FROM eclipse-temurin:11.0.14_9-jre-alpine as builder
WORKDIR application
COPY build/libs/tripPricer-1.0.0.jar  tripPricer-1.0.0.jar

EXPOSE 9003
ENTRYPOINT ["java", "-jar", "tripPricer-1.0.0.jar"]
