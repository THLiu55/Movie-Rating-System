FROM openjdk:8-jdk-alpine

# Copy the jar file to the container
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
