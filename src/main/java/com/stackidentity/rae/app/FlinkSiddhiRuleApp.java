package com.stackidentity.rae.app;


import com.stackidentity.rae.app.config.JobConfig;
import com.stackidentity.rae.app.config.JobConfigurator;
import com.stackidentity.rae.app.connector.Producer;
import com.stackidentity.rae.app.control.ControlStream;
import com.stackidentity.rae.app.control.model.RuleControlEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.util.Collector;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Map;


public class FlinkSiddhiRuleApp {

    public static void start(ConfigurableApplicationContext ctx) throws Exception {
        //Initialize Job Configuration through spring context
        JobConfig jobConfig = ctx.getBean(JobConfig.class);

        // initialize Job configurator
        JobConfigurator configurator = new JobConfigurator(jobConfig, ctx);

        //Initialize flink execution environment
        StreamExecutionEnvironment env = configurator.getConfiguredFlinkEnvironment();

        //Get Main Input DataStream and Control streams from Kafka as configured in application.xml
        //The key for the Map is the main stream name and the value is a List of Datastreams for Data and Control(Rule)
        Map<String, List<DataStream<?>>> inputAndRuleStreams = configurator.getInputAndRuleDataStreams(env);

        //.filter(r -> r != null && r.trim() != "" && r.contains("s3log"));

        //Initialize siddhi environment with required extensions
        SiddhiCEP cep = configurator.initSiddhiCEPEnv(env);

        //Get input source data streams (All kafka topics for input data)
        Map<String ,DataStream<?>> inputDataStreams = configurator.getMainInputSourceStreams(env);

        //Get Control streams from rule data sources (All kafka topics for rule)
        Map<String ,DataStream<RuleControlEvent>> ruleControlStream = configurator.getMainControlSourceStreams(env);

        //Get combined input data and rule stream based on common key in data stream records and rule stream records.
        Map<String, List<DataStream<?>>> combinedDataAndRuleStream = configurator.getSplittedDataAndControlStream(inputDataStreams,ruleControlStream);

        //TODO: code to generate output streams from the above combined rule and data stream
        //code ....

        //TODO: Split the input stream and rule stream to resource specific streams based on resource types.
        inputAndRuleStreams.forEach((name, lstStreams) -> {
            //json needs extension jars to present during runtime.
            //Operator for identifying Failed Attempts to S3 by a user
            //TODO: Register streams specific to resource types
            System.out.println("Registering Siddhi Stream --> " + name);
            cep.registerStream(name, lstStreams.get(0), "s3log");

            //TODO: Apply CEP based on resource types and there specific rule streams
            //TODO: This will throw error if the type doesn't match at runtime
            DataStream<Map<String, Object>> outputStream = cep.from(name).
                    cql((DataStream<ControlEvent>) lstStreams.get(1)).
                    returnAsMap("outputStream");

            //Add Data stream sink for failed attempts-- flink producer
            //Flink kafka stream Producer
            FlinkKafkaProducer<Map<String, Object>> flinkKafkaProducer =
                    Producer.createMapProducer(env, "json-event-stream-output", "localhost:9092");
            outputStream.addSink(flinkKafkaProducer);
            outputStream.print();
        });



        try {
            env.execute(jobConfig.getJobName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}







