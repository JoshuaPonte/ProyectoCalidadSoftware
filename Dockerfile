# Paso 1: Compilar la aplicacion usando Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .

# Modificar el pom.xml en caliente dentro de Docker para cambiar la version a 21
RUN sed -i 's/<java.version>26<\/java.version>/<java.version>21<\/java.version>/g' pom.xml || true
RUN sed -i 's/<maven.compiler.source>26<\/maven.compiler.source>/<maven.compiler.source>21<\/maven.compiler.source>/g' pom.xml || true
RUN sed -i 's/<maven.compiler.target>26<\/maven.compiler.target>/<maven.compiler.target>21<\/maven.compiler.target>/g' pom.xml || true

COPY src ./src
RUN mvn clean package -DskipTests

# Paso 2: Crear la imagen ligera de ejecucion en Java 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/massmanager-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
