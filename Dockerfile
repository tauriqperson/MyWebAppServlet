# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built artifacts from build stage
COPY --from=build /app/target/classes ./target/classes
COPY --from=build /app/target/lib ./target/lib
COPY --from=build /app/target/MyWebAppServlet.war ./target/MyWebAppServlet.war
COPY --from=build /app/src/main/webapp ./src/main/webapp

# Expose port (Render uses PORT env variable)
EXPOSE 8080

# Start the application with correct classpath
CMD ["java", "-cp", "target/classes:target/lib/*", "Main"]
