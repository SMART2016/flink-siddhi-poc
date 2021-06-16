package com.stackidentity.rae.app.s3;

import com.stackidentity.rae.app.connector.*;

import com.stackidentity.rae.app.s3.transform.S3AccessLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.util.Collector;

import java.util.Map;



public class S3FailedAttemptSiddhiApp {

    private static final String inputTopic = "EVENT_STREAM_INPUT";
    private static final String outputTopicFailedAttempt = "EVENT_STREAM_OUTPUT";
    private static final String consumerGroup = "EVENT_STREAM1";
    private static final String kafkaAddress = "localhost:9092";
    private static final String zkAddress = "localhost:2181";


    private static final String S3_ACCESSLOG_FAILED_ATTEMPT_CQL ="from inputStream select json:toObject(s3log) as obj insert into temp;" +
    "from temp select json:getString(obj,'$.s3log.requestId') as access_requester,json:getString(obj,'$.s3log.bucket') as accessed_bucket,json:getString(obj,'$.s3log.operation') as attempted_action,"+
    "json:getString(obj,'$.s3log.httpStatus') as attempted_response,json:getString(obj,'$.s3log.errorCode') as attempted_error insert into temp2;"+
    "from temp2#window.time(10 sec) select access_requester,accessed_bucket,attempted_response,attempted_error,count() as attempts group by access_requester," +
            "accessed_bucket having attempted_response == '404' and attempts > 3 insert into outputStream;";



    public static void start(){
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //DataStream<String> inputS = env.addSource(new S3EventSource());

        //Set Time characteristics default is EventTime and it expects proper watermarking to process the data
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

        //Create Siddhi Env and register siddhi extensions
        SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);
        cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
        cep.registerExtension( "json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);

        //Get Input DataStream from Kafka for S3 Access Logs
        DataStream<String> inputStream = getInputDataStream(env,consumerGroup,kafkaAddress);
        cep.registerStream("inputStream", inputStream, "s3log");

        inputStream.print();
        //json needs extension jars to present during runtime.
        //Operator for identifying Failed Attempts to S3 by a user
        DataStream<Map<String,Object>> failedAttempts = cep.from("inputStream")
                .cql(S3_ACCESSLOG_FAILED_ATTEMPT_CQL)
                .returnAsMap("outputStream");

        //Add Data stream sink for failed attempts-- flink producer
        //Flink kafka stream Producer
        FlinkKafkaProducer<Map<String, Object>> flinkKafkaProducer =
                Producer.createMapProducer(env,outputTopicFailedAttempt, kafkaAddress);

        failedAttempts.addSink(flinkKafkaProducer);
        failedAttempts.print();

        //evaluateFailedS3Attempts(cep,env);
        //evaluateActualS3AAccess(cep,env);

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

    private static DataStream<String> getInputDataStream(StreamExecutionEnvironment env,String consumerGrp,String kafkaAddr){
        //Flink kafka stream consumer
        FlinkKafkaConsumer<String> flinkKafkaConsumer =
                Consumers.createInputMessageConsumer(inputTopic, kafkaAddr,zkAddress, consumerGrp);

        //String containing newline separated lines.
        DataStream<String> inputS = env.addSource(flinkKafkaConsumer);

        //Created stream from each line in the input stream
        DataStream<String> S3LogMsg = inputS.flatMap(new lineSplitter());

        //Converted each line to JSON
        DataStream<String> jsonS3LogMsgs =  inputS.map(s3LogMsg -> {
            return  S3AccessLog.toJson(s3LogMsg);
        });

        return jsonS3LogMsgs;
    }
}

