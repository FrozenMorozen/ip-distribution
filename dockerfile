# back
FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/ip-distribution-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} ip-distribution.jar
ENTRYPOINT ["java","-jar","/ip-distribution.jar"]