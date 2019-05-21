FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY target/app.jar app.jar
EXPOSE 8083

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]