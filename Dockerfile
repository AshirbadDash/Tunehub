# Step 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the jar file (skip tests for faster build)
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:21-jre
WORKDIR /app

# Create logs folder
RUN mkdir -p logs

# Copy the jar file from build step
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 10000

# Run the application
CMD ["java", "-jar", "app.jar"]
