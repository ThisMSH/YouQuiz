FROM postgres

ENV POSTGRES_USER: postgres
ENV POSTGRES_PASSWORD: azer1234
ENV POSTGRES_DB: youquiz

EXPOSE 5431

FROM openjdk:17
MAINTAINER elmahdisaissihassani@gmail.com
COPY target/YouQuiz-0.0.1-SNAPSHOT.jar dockerized-youquiz-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/DockerizedYouQuiz-0.0.1-SNAPSHOT.jar"]
