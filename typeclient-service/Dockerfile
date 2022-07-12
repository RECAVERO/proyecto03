FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} type-service.jar
ENTRYPOINT ["java","-jar","type-service.jar"]