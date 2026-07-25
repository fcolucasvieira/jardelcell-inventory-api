FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

ENV TZ=America/Fortaleza

RUN useradd -r -s /bin/false spring

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

USER spring

ENTRYPOINT [
    "java",
    "-XX:MaxRAMPercentage=75.0",
    "-jar",
    "app.jar"
]