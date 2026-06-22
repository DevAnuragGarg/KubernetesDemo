# Start with an image that already has Java installed.
FROM eclipse-temurin:17-jdk

# Create a Working Directory
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]