FROM eclipse-temurin:11.0.14_9-jre-alpine as builder
WORKDIR application
COPY build/libs/gpsUtil-1.0.0.jar  gpsUtil-1.0.0.jar

EXPOSE 9002
ENTRYPOINT ["java", "-jar", "gpsUtil-1.0.0.jar"]
