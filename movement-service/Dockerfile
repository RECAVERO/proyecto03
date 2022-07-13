FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} movement-service.jar
ENTRYPOINT ["java","-jar","movement-service.jar"]