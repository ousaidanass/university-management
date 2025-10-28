FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY target/address-service-0.0.1-SNAPSHOT.jar address-service.jar
EXPOSE 8080

CMD ["java", "-jar", "address-service.jar"]
