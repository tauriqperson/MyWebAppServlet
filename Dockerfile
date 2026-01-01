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
COPY --from=build /app/target/MyWebAppServlet.war ./MyWebAppServlet.war
COPY --from=build /app/target/classes ./target/classes
COPY --from=build /app/target/lib ./target/lib
COPY src/main/webapp ./src/main/webapp

# Expose port
EXPOSE 8080

# Start the application
CMD ["java", "-cp", "target/classes:target/lib/*:MyWebAppServlet.war", "Main"]
