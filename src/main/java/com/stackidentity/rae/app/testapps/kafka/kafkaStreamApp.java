package com.stackidentity.rae.app.testapps.kafka;

import com.stackidentity.rae.app.connector.Producer;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;

import java.util.Properties;


public class kafkaStreamApp {

    private static final String inputTopic = "EVENT_STREAM_INPUT";
    private static final String outputTopic = "EVENT_STREAM_OUTPUT";
    private static final String consumerGroup = "EVENT_STREAM1";
    private static final String kafkaAddress = "localhost:9092";
    private static final String zkAddress = "localhost:2181";


    public static void start(){
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //Flink kafka stream consumer
        FlinkKafkaConsumer<String> flinkKafkaConsumer =
                createInputMessageConsumer(inputTopic, kafkaAddress,zkAddress, consumerGroup);

        //Flink kafka stream Producer
        FlinkKafkaProducer<Tuple2> flinkKafkaProducer =
                Producer.createStringProducer(env,outputTopic, kafkaAddress);

        //Add Data stream source -- flink consumer
        DataStream<String> inputMessagesStream = env.addSource(flinkKafkaConsumer);

        SingleOutputStreamOperator messagesStream = inputMessagesStream.flatMap(new Tokenizer())
                // group by the tuple field "0" and sum up tuple field "1"
                .keyBy(0)
                .sum(1);

        //Add Data stream sink -- flink producer
        messagesStream.addSink(flinkKafkaProducer);
        messagesStream.print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FlinkKafkaConsumer<String> createInputMessageConsumer(String topic, String kafkaAddress, String zookeeprAddr, String kafkaGroup ) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaAddress);
        properties.setProperty("zookeeper.connect", zookeeprAddr);
        properties.setProperty("group.id",kafkaGroup);
        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<String>(
                topic,new SimpleStringSchema(),properties);
        return consumer;
    }
    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // normalize and split the line
            String[] tokens = value.toLowerCase().split("\\W+");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
