FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} client-service.jar
ENTRYPOINT ["java","-jar","client-service.jar"]