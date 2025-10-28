FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY target/student-service-0.0.1-SNAPSHOT.jar student-service.jar
EXPOSE 8080

CMD ["java", "-jar", "student-service.jar"]
