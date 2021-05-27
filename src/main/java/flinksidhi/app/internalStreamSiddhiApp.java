package flinksidhi.app;

import flinksidhi.event.s3.transform.S3AccessLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.util.Collector;

import java.util.Map;

import static flinksidhi.app.connector.Consumers.createInputMessageConsumer;
import static flinksidhi.app.connector.Producer.createMapProducer;

public class internalStreamSiddhiApp {

    private static final String inputTopic = "EVENT_STREAM_INPUT";
    private static final String outputTopic = "EVENT_STREAM_OUTPUT";
    private static final String consumerGroup = "EVENT_STREAM1";
    private static final String kafkaAddress = "localhost:9092";
    private static final String zkAddress = "localhost:2181";

    private static final String S3_ACCESSLOG_FAILED_ATTEMPT_CQL = "from inputStream select json:toObject(s3log) as obj insert into temp;" +
            "from temp select json:getString(obj,'$.s3log.bucket') as accessed_bucket," +
            "json:getString(obj,'$.s3log.requestId') as access_requester," +
            "json:getString(obj,'$.s3log.operation') as attempted_action," +
            "json:getString(obj,'$.s3log.httpStatus') as attempt_response," +
            "json:getString(obj,'$.s3log.errorCode') as attempt_error insert into outputStream";

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
        //DataStream<String> inputS = env.addSource(new S3EventSource());

        //Set Time characteristics default is EventTime and it expects proper watermarking to process the data
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

        SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);
        cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
        cep.registerExtension( "json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);

//        flinkKafkaConsumer.assignTimestampsAndWatermarks(
//                WatermarkStrategy
//                .forBoundedOutOfOrderness(Duration.ofSeconds(20)));

        //Flink kafka stream Producer
        FlinkKafkaProducer<Map<String, Object>> flinkKafkaProducer =
                createMapProducer(env,outputTopic, kafkaAddress);


        //Get Input DataStream from Kafka for S3 Access Logs
        DataStream<String> inputStream = getInputDataStream(env);
        cep.registerStream("inputStream", inputStream, "s3log");

        inputStream.print();
        //json needs extension jars to present during runtime.
        DataStream<Map<String,Object>> output = cep.from("inputStream")
                .cql(S3_ACCESSLOG_FAILED_ATTEMPT_CQL)
                .returnAsMap("outputStream");

       //Add Data stream sink -- flink producer
        output.addSink(flinkKafkaProducer);
        output.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class lineSplitter implements FlatMapFunction<String, String> {
        @Override
        public void flatMap(String sentence, Collector<String> out) throws Exception {
            for (String line: sentence.split("\n")) {
                out.collect(line);
            }
        }
    }

    private static DataStream<String> getInputDataStream(StreamExecutionEnvironment env){
        //Flink kafka stream consumer
        FlinkKafkaConsumer<String> flinkKafkaConsumer =
                createInputMessageConsumer(inputTopic, kafkaAddress,zkAddress, consumerGroup);

        DataStream<String> inputS = env.addSource(flinkKafkaConsumer);


        DataStream<String> S3LogMsg = inputS.flatMap(new lineSplitter());

        DataStream<String> jsonS3LogMsgs =  inputS.map(s3LogMsg -> {
            return  S3AccessLog.toJson(s3LogMsg);
        });

        return jsonS3LogMsgs;
    }
}

