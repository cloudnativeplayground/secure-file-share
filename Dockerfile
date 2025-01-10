# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the local pom.xml and project files to the container's working directory
COPY pom.xml .
COPY src ./src

# Run Maven to build the project
RUN ./mvnw clean install -DskipTests

# Use an official OpenJDK runtime for the final image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage to the final image
COPY --from=builder /app/target/secure-file-share-1.0-SNAPSHOT.jar /app/secure-file-share.jar

# Expose the port your app will run on (same as your server.port in application.properties)
EXPOSE 8080

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "/app/secure-file-share.jar"]

