# Usa una imagen oficial de Java con soporte para Java 17
FROM amazoncorretto:17-alpine

# Establecer opciones de Java para habilitar el depurador remoto
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

# Copia el archivo JAR a la imagen
COPY target/GoAway-0.0.1-SNAPSHOT.jar app.jar

# Expone los puertos de la aplicación y del depurador
EXPOSE 8080 5005

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
