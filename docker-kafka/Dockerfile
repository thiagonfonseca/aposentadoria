FROM ubuntu:20.04 as ubuntu

RUN mkdir -p /kafka

WORKDIR /kafka

RUN apt-get update

RUN apt-get install wget -y

RUN wget https://downloads.apache.org/kafka/2.7.0/kafka_2.13-2.7.0.tgz

RUN tar -xvf kafka_2.13-2.7.0.tgz 

RUN mv kafka_2.13-2.7.0 kafka

RUN apt-get install zookeeper -y

RUN echo "delete.topic.enable=true" >> kafka/config/server.properties

RUN echo "advertised.host.name=localhost" >> kafka/config/server.properties

ENTRYPOINT ["/bin/sh","-c","nohup kafka/bin/zookeeper-server-start.sh -daemon kafka/config/zookeeper.properties > kafka/zookeeper.error && nohup kafka/bin/kafka-server-start.sh kafka/config/server.properties > kafka/kafka.log"]