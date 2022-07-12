FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} gateway-service.jar
ENTRYPOINT ["java","-jar","gateway-service.jar"]