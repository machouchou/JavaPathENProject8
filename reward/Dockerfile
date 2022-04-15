FROM eclipse-temurin:11.0.14_9-jre-alpine as builder
WORKDIR application
COPY build/libs/reward-1.0.0.jar  reward-1.0.0.jar

EXPOSE 9001
ENTRYPOINT ["java", "-jar", "reward-1.0.0.jar"]
