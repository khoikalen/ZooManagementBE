FROM openjdk:20-jdk

WORKDIR /app
COPY target/ZooManagementSystem-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
