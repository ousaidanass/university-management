FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY target/config-server-0.0.1-SNAPSHOT.jar config-server.jar
EXPOSE 8888

CMD ["java", "-jar", "config-server.jar"]
