FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eureka-service.jar
ENTRYPOINT ["java","-jar","eureka-service.jar"]