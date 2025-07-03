FROM maven:3.8.5-openjdk-11 AS builder

WORKDIR /app/
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11
COPY --from=builder /app/target/eventos-0.0.1-SNAPSHOT.war /app.war

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.war"]




