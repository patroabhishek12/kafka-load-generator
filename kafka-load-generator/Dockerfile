# ---- Build stage ----
FROM gradle:8.4-jdk21-alpine AS build

WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/kafka-load-generator-0.0.1-SNAPSHOT.jar app.jar

# Environment for Spring Boot, if you want to override anything
ENV JAVA_OPTS=""

# Expose no HTTP port (this is a worker process)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
