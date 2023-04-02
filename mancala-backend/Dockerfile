# Base image
FROM openjdk:17-jdk-alpine3.14

# Set the working directory
WORKDIR /app

# Copy files to the container
COPY /target/*.jar ./mancala.jar

# Expose the backend port
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "mancala.jar"]
