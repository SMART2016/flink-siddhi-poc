package com.stackidentity.rae.app.control.consumer;

import com.stackidentity.rae.app.control.model.RuleControlEvent;
import com.stackidentity.rae.app.serde.EventSerDeSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

import static com.stackidentity.rae.app.connector.Consumers.createKafkaConsumerFor;

/**
 * Class to fetch kafka consumer for Rule control stream.
 */
public class KafkaRuleConsumer {

    public static FlinkKafkaConsumer<RuleControlEvent> getControlStream(String topic, Properties properties, final EventSerDeSchema<?> serDeSchema) {
        return createKafkaConsumerFor(topic, properties, serDeSchema);
    }
}
