FROM buildpack-deps:buster-scm
VOLUME /tmp

COPY target/app.jar app.jar
EXPOSE 8083

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]