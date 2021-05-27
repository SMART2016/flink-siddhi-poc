# we start from a Kafka image as the connector is in Kafka distribution
FROM flink-siddhi-poc_kafka:latest

#Create connector plugins folder in kafka
#RUN mkdir -p /tmp/plugins
#RUN chmod 777 /tmp/plugins
#COPY plugins/camel-aws-s3-kafka-connector/* /tmp/plugins/
#RUN chmod 777 /tmp/plugins/*

#RUN chmod 777 /opt/kafka/lib/*
COPY ./plugins/camel-aws-s3-kafka-connector/* /opt/kafka/libs/
#RUN chmod 777 /opt/kafka/libs/*

# we replace the default connect-standalone.properties so we can properly resolve to our local Kafka docker development
RUN chmod 777 /opt/kafka/config/connect-standalone.properties

COPY CamelAws2s3SourceConnector.properties /opt/kafka/config/
COPY connect-standalone.properties /opt/kafka/config/



# we replace the start command creating a connector instead of starting a kafka broker.
COPY start-kafka.sh /usr/bin/

#ENV CLASSPATH /tmp/plugins/

# permissions
RUN chmod a+x /usr/bin/start-kafka.sh
RUN chmod a+x /opt/kafka/config/CamelAws2s3SourceConnector.properties
#RUN chmod a+x /opt/kafka/lib/*
