package flinksidhi.control;

import flinksidhi.control.consumer.KafkaRuleConsumer;
import flinksidhi.control.event.model.RuleControlEvent;
import flinksidhi.control.event.serde.ControlEventSerDeSchema;
import flinksidhi.control.transform.ControlEventTransformer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.siddhi.control.ControlEvent;

import java.lang.reflect.InvocationTargetException;

public class ControlStream {

    //They will be going to configurations
    private static final String ruleTopic = "S3_RULE_STREAM_INPUT";
    private static final String streamName = "s3ruleStream";
    private static final String consumerName = "s3ruleConsumer";
    private static final int parallalism = 1;



    public static DataStream<ControlEvent> getControlStream(final StreamExecutionEnvironment env)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        FlinkKafkaConsumer<RuleControlEvent> rulesConsumer =
                KafkaRuleConsumer.getControlStream(ruleTopic, new ControlEventSerDeSchema());

        DataStream<RuleControlEvent> ruleControlStream = env.addSource(rulesConsumer,streamName).uid(consumerName).name(consumerName).setParallelism(parallalism);

        //1.We can use transformers to transform the rules in case the rules are parameterized
        //2. We can apply filters to filter rules out which is not apt for the specific application ,
        //          if the control input topic is same for all rules
        //return ruleStream.flatMap(new RuleControlEventTransformer()).uid("rule-transformer").name("rule-transformer");

        return ruleControlStream.flatMap(new ControlEventTransformer()).uid("rule-transformer").name("rule-transformer");
        //return ruleStream;
    }
}
