FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/LLMigrator-DB-API-0.0.1-SNAPSHOT.war /app/LLMigrator-DB-API-0.0.1-SNAPSHOT.war

EXPOSE 8080

CMD ["java", "-jar", "LLMigrator-DB-API-0.0.1-SNAPSHOT.war"]