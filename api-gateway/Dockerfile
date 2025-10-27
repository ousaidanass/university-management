FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 9090

CMD ["java", "-jar", "api-gateway.jar"]
