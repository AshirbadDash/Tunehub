# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# ===== LAYER 1: Maven Dependencies (cached until pom.xml changes) =====
COPY pom.xml ./
# Create dummy structure to satisfy Maven and download dependencies
RUN mkdir -p src/main/java src/main/resources src/test/java && \
    echo "public class Dummy {}" > src/main/java/Dummy.java && \
    mvn -B dependency:go-offline -DskipTests || \
    mvn -B dependency:resolve dependency:resolve-plugins -DskipTests || true

# ===== LAYER 2: Frontend package.json (cached until package.json changes) =====
COPY src/main/frontend/package*.json ./src/main/frontend/

# ===== LAYER 3: Source Code & Build (rebuilds when code changes) =====
# Remove dummy files and copy real source
RUN rm -rf src/
COPY src ./src

# Build the application (frontend-maven-plugin handles Node.js + npm + build)
RUN mvn -B -DskipTests clean package

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Create non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring

# Copy built jar from build stage
COPY --from=build --chown=spring:spring /app/target/*.jar ./app.jar

# Switch to non-root user
USER spring

# Default PORT used by Render
ENV PORT=10000
EXPOSE 10000

# Optimized JVM options for containerized environment
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

# Run the application
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
