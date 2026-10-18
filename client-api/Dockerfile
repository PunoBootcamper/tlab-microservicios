# ETAPA 1: Compilación
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

# Copiar configuración de Maven
COPY pom.xml .

# Descargar dependencias y aprovechar la caché de Docker
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
RUN mvn clean package -DskipTests -B


# ETAPA 2: Ejecución
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copiar únicamente el JAR compilado en la primera etapa
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]