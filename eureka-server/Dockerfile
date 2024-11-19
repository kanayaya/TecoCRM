FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=build/libs/eureka-server-0.0.1-plain.jar
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]
