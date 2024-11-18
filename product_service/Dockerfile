FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=build/libs/product_srtvice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} product_srtvice.jar
ENTRYPOINT ["java","-jar","/product_srtvice.jar"]
