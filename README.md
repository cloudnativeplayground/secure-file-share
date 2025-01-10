Here’s a detailed `README.md` file for your **Secure File Sharing System** project with all the necessary sections you mentioned:

---

# Secure File Sharing System

## Overview

**Secure File Sharing System** is an open-source project designed to demonstrate how to securely share files using **Spring Security**, **Docker**, and **Kubernetes**. The project focuses on providing a secure way to upload, share, and manage files, using advanced security measures such as file encryption, access control, and audit logs.

## Features

- **File Encryption**: Ensures that files are encrypted before storage.
- **Access Control**: Only authorized users can upload, download, or view files.
- **Audit Logs**: Tracks file access, uploads, downloads, and changes for security and compliance purposes.

## Tech Stack

- **Spring Boot** for building the RESTful backend.
- **Spring Security** for securing file access and authentication.
- **Docker** for containerizing the application.
- **Kubernetes** for potential deployment (optional, not implemented here).
- **H2 Database** for in-memory database (can be replaced with MySQL/PostgreSQL for production).

## Project Setup

### Prerequisites

- **Docker**: Ensure Docker is installed on your machine.
- **Maven**: Ensure Maven is installed to build the project.

### 1. Clone the Repository

```bash
git clone https://github.com/cloudnativeplayground/secure-file-share.git
cd secure-file-share
```

### 2. Build the Project

You can build the project using Maven.

```bash
./mvnw clean install
```

This will download dependencies and package the application into a JAR file.

### 3. Running the Application with Docker

To run the application using Docker, follow these steps:

1. **Build Docker Image**:

   ```bash
   docker-compose build
   ```

2. **Run Docker Containers**:

   ```bash
   docker-compose up
   ```

This will start the Spring Boot application and the H2 database container. The application will be available at `http://localhost:8080`.

### 4. Stop the Containers

To stop the running containers, use:

```bash
docker-compose down
```

## Configuration Details

### application.properties

This project comes with a preconfigured `application.properties` file that sets up essential application parameters.

```properties
# Server Configuration
server.port=8080

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
spring.servlet.multipart.enabled=true

# DataSource Configuration (H2 database, can be replaced with MySQL/PostgreSQL)
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.datasource.platform=h2
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Hibernate JPA Configuration (automatic schema generation)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Spring Security Configuration
spring.security.user.name=admin
spring.security.user.password=admin123
spring.security.user.roles=ADMIN

# Logging Configuration
logging.level.org.springframework.web=INFO
logging.level.com.securefileshare=DEBUG
logging.file.name=secure-file-share.log
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# H2 Console (optional for debugging in development)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Swagger Configuration
springfox.documentation.swagger.v2.path=/api-docs
springfox.documentation.swagger-ui.base-url=/swagger-ui 
```

### Swagger API Documentation

Once the application is running, you can access the **Swagger UI** at:

```
http://localhost:8080/swagger-ui/index.html
```

This interface will allow you to explore the REST APIs exposed by the system and try out API calls like uploading and downloading files.

## Maven Commands

- **Build the project**:

  ```bash
  ./mvnw clean install
  ```

- **Run the application locally** (without Docker):

  ```bash
  ./mvnw spring-boot:run
  ```

- **Run tests**:

  ```bash
  ./mvnw test
  ```

- **Build the Docker image using Maven**:

  If you prefer building the Docker image directly from Maven, you can use:

  ```bash
  ./mvnw clean package docker:build
  ```

## Docker Details

### Dockerfile

This project is containerized using Docker. The `Dockerfile` is used to create a Docker image for the Spring Boot application.

```Dockerfile
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

# Expose the port your app will run on
EXPOSE 8080

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "/app/secure-file-share.jar"]
```

### Docker Compose

The `docker-compose.yml` file is used to spin up both the Spring Boot application and the H2 database in separate containers.

```yaml
version: "3.8"

services:
  # Spring Boot application service
  secure-file-share:
    build: .
    container_name: secure-file-share
    ports:
      - "8080:8080"  # Map port 8080 of the container to the host machine's port 8080
    environment:
      SPRING_DATASOURCE_URL: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
      SPRING_DATASOURCE_USERNAME: sa
      SPRING_DATASOURCE_PASSWORD: password
      SPRING_H2_CONSOLE_ENABLED: "true"
      SPRING_H2_CONSOLE_PATH: /h2-console
      SPRING_SECURITY_USER_NAME: admin
      SPRING_SECURITY_USER_PASSWORD: admin123
      SPRING_SECURITY_USER_ROLES: ADMIN
    volumes:
      - .:/app  # Bind mount the current directory to /app in the container
    networks:
      - secure-file-network

  # H2 database service (in-memory database, handled by Spring Boot)
  h2-db:
    image: h2database/h2:1.4.200
    container_name: h2-db
    ports:
      - "8082:8082"  # Expose H2 console port
    environment:
      - SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    networks:
      - secure-file-network

networks:
  secure-file-network:
    driver: bridge
```

## Maintainer

- **Name**: Aditya Pratap Bhuyan
- **LinkedIn**: [Aditya Pratap Bhuyan](https://linkedin.com/in/adityabhuyan)
- **Email**: aditya.sunjava@gmail.com

## License

This project is licensed under the **GNU General Public License v3.0**. See the [LICENSE](https://www.gnu.org/licenses/gpl-3.0.html) for more details.

---

### Notes:
- You can replace the **H2 Database** with **MySQL/PostgreSQL** in production by updating the `application.properties` file.
- **Kubernetes** support can be added later if you decide to deploy it in a Kubernetes environment.


