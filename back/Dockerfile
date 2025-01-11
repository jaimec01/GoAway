# Usa una imagen oficial de Java con soporte para Java 17
FROM amazoncorretto:17-alpine

# Copia el archivo JAR a la imagen
COPY target/GoAway-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 (asegúrate que sea el puerto de tu aplicación Spring Boot)
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
