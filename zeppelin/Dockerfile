FROM debian:stretch

RUN apt-get update && apt-get install -y wget git \
    openjdk-8-jdk openjdk-8-jre-headless \
    libfontconfig r-base-dev r-cran-evaluate

RUN wget http://www.eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz \
    && tar -zxf apache-maven-3.3.9-bin.tar.gz -C /usr/local/ \
    && ln -s /usr/local/apache-maven-3.3.9/bin/mvn /usr/local/bin/mvn

RUN git clone https://github.com/apache/zeppelin.git
RUN cd /zeppelin \
    && ./dev/change_scala_version.sh 2.11 \
    && mvn clean package -Pspark-2.1 -Phadoop-2.6 -Pscala-2.11 -DskipTests

WORKDIR /zeppelin

CMD ["/zeppelin/bin/zeppelin.sh"]
