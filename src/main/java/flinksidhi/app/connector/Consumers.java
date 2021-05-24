package flinksidhi.app.connector;


import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.json.JSONObject;

import java.util.Properties;
public class Consumers {
    public static FlinkKafkaConsumer<String> createInputMessageConsumer(String topic, String kafkaAddress, String zookeeprAddr, String kafkaGroup ) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaAddress);
        properties.setProperty("zookeeper.connect", zookeeprAddr);
        properties.setProperty("group.id",kafkaGroup);
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<String>(
                topic,new SimpleStringSchema(),properties);
        return consumer;
    }

    public static FlinkKafkaConsumer<JSONObject> createInputJsonMessageConsumer(String topic, String kafkaAddress, String zookeeprAddr, String kafkaGroup ) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaAddress);
        properties.setProperty("zookeeper.connect", zookeeprAddr);
        properties.setProperty("group.id",kafkaGroup);
        FlinkKafkaConsumer<JSONObject> consumer = new FlinkKafkaConsumer<JSONObject>(
                topic,new JsonSchema(),properties);
        return consumer;
    }
}
