FROM gradle:7.4.0-jdk11-alpine
MAINTAINER pdpano

RUN mkdir -p /usr/scr/app
WORKDIR /usr/scr/app
COPY . /usr/scr/app

# RUN gradle build
# VOLUME ["/kotlin-data"]

# EXPOSE 8082

ENTRYPOINT ["java","-jar","build/libs/imc-0.0.1-SNAPSHOT.jar"]
