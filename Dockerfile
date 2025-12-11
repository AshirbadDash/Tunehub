# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Install Node.js for frontend build (using binary download for speed)
ARG NODE_VERSION=24.11.0
RUN apt-get update && apt-get install -y xz-utils && rm -rf /var/lib/apt/lists/* && \
    curl -fsSL https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-x64.tar.xz | tar -xJ -C /usr/local --strip-components=1

# ===== LAYER 1: Maven Dependencies (cached until pom.xml changes) =====
COPY pom.xml ./
# Create dummy structure to satisfy Maven and download dependencies
RUN mkdir -p src/main/java src/main/resources src/test/java && \
    echo "public class Dummy {}" > src/main/java/Dummy.java && \
    mvn -B dependency:go-offline -DskipTests -Dfrontend-maven-plugin.skip=true || \
    mvn -B dependency:resolve dependency:resolve-plugins -DskipTests || true

# ===== LAYER 2: Frontend Dependencies (cached until package.json changes) =====
COPY src/main/frontend/package*.json ./src/main/frontend/
WORKDIR /app/src/main/frontend
RUN npm ci --prefer-offline --no-audit || npm install --prefer-offline --no-audit

# ===== LAYER 3: Source Code & Build (rebuilds when code changes) =====
WORKDIR /app
# Remove dummy files and copy real source
RUN rm -rf src/
COPY src ./src

# Build frontend assets manually (before Maven)
WORKDIR /app/src/main/frontend
RUN npm run build

# Build the application (skip all frontend plugin executions)
WORKDIR /app
RUN mvn -B -DskipTests -Dfrontend-maven-plugin.skip=true clean package

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
