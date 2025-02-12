
# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine
# Variables de entorno para la aplicación
ENV APP_HOME=/app
# Crear directorio de la aplicación
RUN mkdir $APP_HOME
WORKDIR $APP_HOME
# Copiar el archivo jar del proyecto al contenedor
COPY target/*.jar app.jar
# Exponer el puerto de la aplicación
EXPOSE 8094
# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]