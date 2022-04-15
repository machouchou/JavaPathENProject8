FROM eclipse-temurin:11.0.14_9-jre-alpine as builder
WORKDIR application
COPY build/libs/tourGuide-1.0.0.jar  tourGuide-1.0.0.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tourGuide-1.0.0.jar"]
