FROM ubuntu:22.04

RUN apt-get -y update && \
    apt-get upgrade -y && \
    apt-get install -y  wget openjdk-11-jdk maven vim apt-utils locales unzip && \
    apt-get clean

RUN mkdir -p /tmp/dostres
WORKDIR /tmp/dostres

RUN wget https://github.com/kermitt2/grobid/archive/0.7.2.zip
RUN unzip 0.7.2.zip

WORKDIR /tmp/dostres/grobid-0.7.2/

RUN ./gradlew clean install

RUN mkdir /var/maven/ && chmod -R 777 /var/maven && mkdir /tmp/dostres/java/ && chmod -R 777 /tmp/dostres/java && mkdir /tmp/dostres/java/lib/ && chmod -R 777 /tmp/dostres/java/lib
ENV MAVEN_CONFIG /var/maven/.m2

ADD pom.xml /tmp/dostres/java
ADD lib/* /tmp/dostres/java/lib/

RUN cd /tmp/dostres/java && mvn -B -e -C -T 1C -Duser.home=/var/maven org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline 

#RUN locale-gen de_DE.UTF-8
#
#ENV LANG de_DE.UTF-8  
#ENV LANGUAGE de_DE:de  
#ENV LC_ALL de_DE.UTF-8

COPY . /tmp/dostres/java/

RUN chmod -R 777 /var/maven
EXPOSE 8092

WORKDIR /tmp/dostres/java
RUN mvn -Duser.home=/var/maven clean install -DskipTests

ADD src/main/resources/application_server.properties /tmp/dostres/java/src/main/resources/application.properties

#ADD output_annotated.xml /tmp/dostres/java/src/main/resources

RUN chmod -R 777 /tmp/dostres/java

#CMD mvn -Duser.home=/var/maven -Drun.jvmArguments="-Xmx6144m" spring-boot:run
CMD mvn -Duser.home=/var/maven spring-boot:run
#CMD ["/bin/bash"]
