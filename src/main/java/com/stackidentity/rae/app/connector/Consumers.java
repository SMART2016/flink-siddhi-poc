package com.stackidentity.rae.app.connector;


import com.stackidentity.rae.app.serde.EventSerDeSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class Consumers {

    public static FlinkKafkaConsumer createKafkaConsumerFor(String topic, Properties properties, final EventSerDeSchema<?> serDeSchema) {
        FlinkKafkaConsumer consumer = new FlinkKafkaConsumer(topic, serDeSchema, properties);
        return consumer;
    }
}
