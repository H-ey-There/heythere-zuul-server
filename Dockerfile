FROM openjdk:14-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} zuul-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/zuul-0.0.1-SNAPSHOT.jar"]