# Use the official Zulu OpenJDK 17 JRE slim image as the base.
FROM azul/zulu-openjdk:17-jre-slim

# Set the working directory in the container.
WORKDIR /app

# Copy the built jar file into the container.
COPY build/libs/receiptprocessor-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on.
EXPOSE 8080

# Run the jar file.
ENTRYPOINT ["java", "-jar", "app.jar"]
