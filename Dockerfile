# Build stage
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:25-jre
WORKDIR /app

RUN mkdir -p /data

COPY --from=build /app/target/turbometa-*.jar app.jar

ENV TURBOMETA_DB_PATH=/data/turbometa.db
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
