package flinksidhi.app;

import flinksidhi.app.stream.SingleSiddhiStream;
import flinksidhi.event.s3.source.S3EventSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.util.Preconditions;

import java.util.Map;

import static flinksidhi.app.connector.Consumers.createInputMessageConsumer;
import static flinksidhi.app.connector.Producer.createMapProducer;

public class internalStreamSiddhiApp {

    private static final String inputTopic = "EVENT_STREAM_INPUT";
    private static final String outputTopic = "EVENT_STREAM_OUTPUT";
    private static final String consumerGroup = "EVENT_STREAM1";
    private static final String kafkaAddress = "kafka:9092";
    private static final String zkAddress = "zookeeper:2181";

    private static final String S3_CQL1 = "from inputStream select awsS3 insert into temp";
    private static final String S3_CQL = "from inputStream select json:toObject(awsS3) as obj insert into temp;" +
            "from temp select json:getString(obj,'$.awsS3.ResourceType') as affected_resource_type," +
            "json:getString(obj,'$.awsS3.Details.Name') as affected_resource_name," +
            "json:getString(obj,'$.awsS3.Encryption.ServerSideEncryptionConfiguration') as encryption," +
            "json:getString(obj,'$.awsS3.Encryption.ServerSideEncryptionConfiguration.Rules[0].ApplyServerSideEncryptionByDefault.SSEAlgorithm') as algorithm insert into temp2; " +
            "from temp2 select  affected_resource_name,affected_resource_type, " +
            "ifThenElse(encryption == ' ','Fail','Pass') as state," +
            "ifThenElse(encryption != ' ' and algorithm == 'aws:kms','None','Critical') as severity insert into outputStream";


    public static void start(){
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> inputS = env.addSource(new S3EventSource());

        //Flink kafka stream consumer
        FlinkKafkaConsumer<String> flinkKafkaConsumer =
                createInputMessageConsumer(inputTopic, kafkaAddress,zkAddress, consumerGroup);
        //Flink kafka stream Producer
        FlinkKafkaProducer<Map<String, Object>> flinkKafkaProducer =
                createMapProducer(env,outputTopic, kafkaAddress);


        //Add Data stream source -- flink consumer
        //DataStream<String> inputS = env.addSource(flinkKafkaConsumer);
        SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);

        cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
        cep.registerExtension( "json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);
        cep.registerStream("inputStream", inputS, "awsS3");


        inputS.print();

        //json needs extension jars to present during runtime.
        DataStream<Map<String,Object>> output = cep.from("inputStream")
                .cql(S3_CQL1)
                .returnAsMap("temp");

       //Add Data stream sink -- flink producer
        output.addSink(flinkKafkaProducer);
        output.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static <T> SingleSiddhiStream<T> from(String streamId, SiddhiCEP env) {
        Preconditions.checkNotNull(streamId, "streamId");
        return new SingleSiddhiStream(streamId, env);
    }
}

