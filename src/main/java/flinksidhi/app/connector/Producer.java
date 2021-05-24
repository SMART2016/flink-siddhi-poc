package flinksidhi.app.connector;

import flinksidhi.app.util.ConvertJavaMapToJson;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;
import org.json.JSONObject;

import java.util.Map;

public class Producer {

    public static FlinkKafkaProducer<Tuple2> createStringProducer(StreamExecutionEnvironment env, String topic, String kafkaAddress) {

        return new FlinkKafkaProducer<Tuple2>(kafkaAddress, topic, new AverageSerializer());
    }

    public static FlinkKafkaProducer<Map<String,Object>> createMapProducer(StreamExecutionEnvironment env, String topic, String kafkaAddress) {

        return new FlinkKafkaProducer<Map<String,Object>>(kafkaAddress, topic, new SerializationSchema<Map<String, Object>>() {
            @Override
            public void open(InitializationContext context) throws Exception {

            }

            @Override
            public byte[] serialize(Map<String, Object> stringObjectMap) {
                String json = ConvertJavaMapToJson.convert(stringObjectMap);
                return json.getBytes();
            }
        });
    }
    public static FlinkKafkaProducer<JSONObject> createJsonProducer(StreamExecutionEnvironment env, String topic, String kafkaAddress) {

        return new FlinkKafkaProducer<JSONObject>(kafkaAddress, topic, new SerializationSchema<JSONObject>() {
            @Override
            public void open(InitializationContext context) throws Exception {

            }

            @Override
            public byte[] serialize(JSONObject jsonObject) {
                return jsonObject.toString().getBytes();
            }
        });
    }
}

class AverageSerializer implements KeyedSerializationSchema<Tuple2> {
    @Override
    public byte[] serializeKey(Tuple2 element) {
        return ("\"" + element.getField(0).toString() + "\"").getBytes();
    }

    @Override
    public byte[] serializeValue(Tuple2 element) {
        String value = "{"+ element.getField(0).toString()+": "+ element.getField(1).toString() + "}";
        return value.getBytes();
    }

    @Override
    public String getTargetTopic(Tuple2 element) {
        // use always the default topic
        return null;
    }
}
