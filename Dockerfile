# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only pom first so dependency layer can be cached
COPY pom.xml ./

# Download dependencies (offline mode) to speed up rebuilds
RUN mvn -B -DskipTests dependency:go-offline

# Copy source and build the executable jar
COPY src ./src
RUN mvn -B -DskipTests package

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy built jar from build stage (assumes Spring Boot produces an executable jar in target/)
COPY --from=build /app/target/*.jar ./app.jar

# Default PORT used by Render is 10000; keep PORT configurable
ENV PORT=10000
EXPOSE 10000

# Allow extra JVM options via JAVA_OPTS; ensure Spring Boot uses the PORT env var.
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
