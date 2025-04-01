
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Baixa as dependências (cache eficiente)
RUN ./mvnw dependency:go-offline -B


COPY src ./src

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]