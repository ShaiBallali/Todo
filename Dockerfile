# Use a slim OpenJDK 17 image as the base
FROM openjdk:17.0.1-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the pre-built JAR file into the image
COPY target/To_Do-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port your application listens on
EXPOSE 9285

# Run the application when the container starts
CMD ["java", "-jar", "app.jar"]