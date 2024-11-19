FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=build/libs/api_gateway-0.0.1.jar
COPY ${JAR_FILE} api_gateway.jar
ENTRYPOINT ["java","-jar","/api_gateway.jar"]
