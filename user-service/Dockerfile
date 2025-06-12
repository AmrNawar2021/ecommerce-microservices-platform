# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim

# Add metadata
LABEL maintainer="Amr Fawzi"

# Set working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port (لو حابب تشغل الـ service على بورت معين)
EXPOSE 8090

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
