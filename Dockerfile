FROM maven:3.3.9-jdk-8

MAINTAINER gdanguy gdanguy@e-biz.fr

COPY . /usr/src/app


RUN chmod -R ug+rw /usr/src/app && \
cd /usr/src/app && \
mvn -X clean install package