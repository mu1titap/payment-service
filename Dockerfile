
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/payment-0.0.1-SNAPSHOT.jar payment-service.jar

EXPOSE 9900

ENTRYPOINT ["java", "-jar", "payment-service.jar"]

ENV TZ=Asia/Seoul