package com.stackidentity.rae.app;


import com.stackidentity.rae.app.config.JobConfig;
import com.stackidentity.rae.app.config.JobConfigurator;
import com.stackidentity.rae.app.control.model.RuleControlEvent;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.graph.StreamGraph;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
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


        //.filter(r -> r != null && r.trim() != "" && r.contains("s3log"));
        //TODO: Need to add filters on mainsource stream for blank records events

        //Initialize siddhi environment with required extensions
        SiddhiCEP cep = configurator.initSiddhiCEPEnv(env);

        //Get input source data streams (All kafka topics for input data)
        Map<String, DataStream<String>> inputDataStreams = configurator.getMainInputSourceStreams();

        //Get Control streams from rule data sources (All kafka topics for rule)
        Map<String, DataStream<RuleControlEvent>> ruleControlStream = configurator.getMainControlSourceStreams();

        //Get combined input data and rule stream based on common key in data stream records and rule stream records.
        Map<String, List<DataStream<?>>> combinedDataAndRuleStream = configurator.getSplittedDataAndControlStream(inputDataStreams, ruleControlStream);

        //TODO: code to generate output streams from the above combined rule and data stream
        List<DataStream<Map<String, Object>>> outStreams = configurator.getTransformedOutputStreams(combinedDataAndRuleStream);

        //Print the out stream to the sink
        for (DataStream<Map<String, Object>> out : outStreams) {
            out.print();
        }


        try {
            env.execute(jobConfig.getJobName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}







