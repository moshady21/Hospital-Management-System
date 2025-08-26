# ---- Build Stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom and resolve dependencies first (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the app
RUN mvn clean package -DskipTests

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app

# Copy only the built jar from build stage
COPY --from=build /app/target/Hospital-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Spring Boot default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-jar", "app.jar"]
