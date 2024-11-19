FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=build/libs/client_site-0.0.1.jar
COPY ${JAR_FILE} client_site.jar
ENTRYPOINT ["java","-jar","/client_site.jar"]

## Этап сборки
#FROM gradle:8.2-jdk17 AS build
#WORKDIR /app
#COPY . /app
#RUN gradle build --no-daemon
#
## Этап выполнения
#FROM openjdk:17.0.1-jdk-slim
#WORKDIR /app
#COPY --from=build /app/build/libs/*.jar app.jar
#EXPOSE 8081
#ENTRYPOINT ["java", "-jar", "app.jar"]
