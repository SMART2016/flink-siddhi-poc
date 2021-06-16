package com.stackidentity.rae.app.control.consumer;

import com.stackidentity.rae.app.control.event.model.RuleControlEvent;
import com.stackidentity.rae.app.control.event.serde.EventSerDeSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import static com.stackidentity.rae.app.connector.Consumers.createKafkaConsumerFor;

/**
 * Class to fetch kafka consumer for Rule control stream.
 */
public class KafkaRuleConsumer {

    //They will be going to configurations
    private static final String consumerGroup = "RULE_CONTROL_STREAM1";
    private static final String kafkaAddress = "localhost:9092";
    private static final String zkAddress = "localhost:2181";

    public static FlinkKafkaConsumer<RuleControlEvent> getControlStream(String topic, final EventSerDeSchema<?> serDeSchema){
        return createKafkaConsumerFor(topic,kafkaAddress,zkAddress,consumerGroup,serDeSchema);
    }
}
