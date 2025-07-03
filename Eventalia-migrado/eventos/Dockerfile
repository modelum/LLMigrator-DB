# Etapa de construcción
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre

COPY --from=builder /app/target/*.war /app.war

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.war"]





