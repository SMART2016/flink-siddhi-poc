package com.stackidentity.rae.app.control;

import com.stackidentity.rae.app.control.consumer.KafkaRuleConsumer;
import com.stackidentity.rae.app.control.model.RuleControlEvent;
import com.stackidentity.rae.app.control.transform.ControlEventTransformer;
import com.stackidentity.rae.app.serde.*;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

//Spring
@Component
@Scope("singleton")
public class ControlStream {

    //They will be going to configurations

    private static final String consumerName = "ruleConsumer";


    public DataStream<ControlEvent> getControlStream(final StreamExecutionEnvironment env, Properties properties, final String topic, final String streamName, int parallalism)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        FlinkKafkaConsumer<RuleControlEvent> rulesConsumer =
                KafkaRuleConsumer.getControlStream(topic, properties, new ControlEventSerDeSchema());

        DataStream<RuleControlEvent> ruleControlStream = env.addSource(rulesConsumer, streamName).uid(consumerName).name(consumerName).setParallelism(parallalism);

        //1.We can use transformers to transform the rules in case the rules are parameterized
        //2. We can apply filters to filter rules out which is not apt for the specific application ,
        //          if the control input topic is same for all rules
        //return ruleStream.flatMap(new RuleControlEventTransformer()).uid("rule-transformer").name("rule-transformer");

        return ruleControlStream.flatMap(new ControlEventTransformer()).uid("rule-transformer").name("rule-transformer");
        //return ruleStream;
    }

    public DataStream<RuleControlEvent> getRuleControlStream(final StreamExecutionEnvironment env, Properties properties, final String topic, final String streamName, int parallalism)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        FlinkKafkaConsumer<RuleControlEvent> rulesConsumer =
                KafkaRuleConsumer.getControlStream(topic, properties, new ControlEventSerDeSchema());

        DataStream<RuleControlEvent> ruleControlStream = env.addSource(rulesConsumer, streamName).uid(consumerName).name(consumerName).setParallelism(parallalism);

        //1.We can use transformers to transform the rules in case the rules are parameterized
        //2. We can apply filters to filter rules out which is not apt for the specific application ,
        //          if the control input topic is same for all rules
        //return ruleStream.flatMap(new RuleControlEventTransformer()).uid("rule-transformer").name("rule-transformer");

        return ruleControlStream;
    }
}
