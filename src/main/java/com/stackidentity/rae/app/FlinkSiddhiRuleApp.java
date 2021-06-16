package com.stackidentity.rae.app;


import com.stackidentity.rae.app.config.JobConfig;
import com.stackidentity.rae.app.config.JobConfigurator;
import com.stackidentity.rae.app.connector.Consumers;
import com.stackidentity.rae.app.connector.Producer;
import com.stackidentity.rae.app.control.ControlStream;
import com.stackidentity.rae.app.s3.S3FailedAttemptSiddhiApp;
import com.stackidentity.rae.app.s3.transform.S3AccessLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.util.Collector;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;


public class FlinkSiddhiRuleApp {

    public static void start(ConfigurableApplicationContext ctx) throws Exception{
        //Initialize Job Configuration through spring context
        JobConfig jobConfig = ctx.getBean(JobConfig.class);

        // initialize Job configurator
        JobConfigurator configurator = new JobConfigurator(jobConfig,ctx);

        //Initialize flink execution environment
        StreamExecutionEnvironment env = configurator.getConfiguredFlinkEnvironment();

        //Get Input DataStream from Kafka for S3 Access Logs
        //TODO: Split the input stream and rule stream to resource specific streams based on resource types.
        DataStream<String> inputStream = getInputDataStream(env).filter(r -> r != null && r.trim() != "" && r.contains("s3log"));
        DataStream<ControlEvent> ruleStream = getRuleStream(env).filter(r -> r != null );



        //Create Siddhi Env and register siddhi extensions
        SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);
        cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
        cep.registerExtension( "json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);


        //json needs extension jars to present during runtime.
        //Operator for identifying Failed Attempts to S3 by a user
        //TODO: Register streams specific to resource types
        cep.registerStream("inputStream", inputStream, "s3log");

        //TODO: Apply CEP based on resource types and there specific rule streams
        DataStream<Map<String,Object>> failedAttempts = cep.from("inputStream").
                cql(ruleStream).
                returnAsMap("outputStream");

        //Add Data stream sink for failed attempts-- flink producer
        //Flink kafka stream Producer
        FlinkKafkaProducer<Map<String, Object>> flinkKafkaProducer =
                Producer.createMapProducer(env,outputTopicFailedAttempt, kafkaAddress);
        failedAttempts.addSink(flinkKafkaProducer);
        failedAttempts.print();

        try {
            env.execute("TestSiddhi");
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
        String inputTopic = "EVENT_STREAM_INPUT";
        String consumerGrp = "";
        //Flink kafka stream consumer
        FlinkKafkaConsumer<String> flinkKafkaConsumer =
                Consumers.createInputMessageConsumer(inputTopic, kafkaAddress,zkAddress, consumerGrp);

        //String containing newline separated lines.
        DataStream<String> inputS = env.addSource(flinkKafkaConsumer);

        //Created stream from each line in the input stream
        DataStream<String> S3LogMsg = inputS.flatMap(new S3FailedAttemptSiddhiApp.lineSplitter());

        //Converted each line to JSON
        DataStream<String> jsonS3LogMsgs =  inputS.map(s3LogMsg -> {
            return  S3AccessLog.toJson(s3LogMsg);
        });

        return jsonS3LogMsgs;
    }



    private static DataStream<ControlEvent> getRuleStream(StreamExecutionEnvironment env) throws Exception {
        //json needs extension jars to present during runtime.
        //Operator for identifying Failed Attempts to S3 by a user
        //NOTE: inputstream for event stream and outputstream for evaluated event stream should be configurable
        //and it should match with the rule in the control stream.
        ControlStream s = new ControlStream();
        DataStream<ControlEvent> ruleControlStream = s.getControlStream(env);
        return ruleControlStream;
    }

}


