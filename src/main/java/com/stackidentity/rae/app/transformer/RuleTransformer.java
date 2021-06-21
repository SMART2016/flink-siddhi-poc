package com.stackidentity.rae.app.transformer;

import com.stackidentity.rae.app.control.model.RuleControlEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.streaming.siddhi.control.MetadataControlEvent;
import org.apache.flink.util.Collector;

public class RuleTransformer implements EventTransformer<DataStream<RuleControlEvent>, DataStream<ControlEvent>> {
    @Override
    public DataStream<ControlEvent> transform(DataStream<RuleControlEvent> controlStream) {
        return controlStream.flatMap(new ControlEventTransformer());
             //TODO: Add UID and Name latter
                //.uid("rule-transformer").name("rule-transformer");
    }

    private static final class ControlEventTransformer implements
            FlatMapFunction<RuleControlEvent, ControlEvent> {

        private static final long serialVersionUID = 1L;

        @Override
        public void flatMap(final RuleControlEvent value, Collector<ControlEvent> collector) {


            String ruleId = value.getRuleId();
            String query = value.getRuleDefinition();


            switch (value.getAction()) {
                case "add":
                    collector.collect(MetadataControlEvent.builder().addExecutionPlan(ruleId, query).build());
                    return;
                case "update":
                    collector.collect(MetadataControlEvent.builder().updateExecutionPlan(ruleId, query).build());
                    return;
                default:
                    break;
            }
        }

    }
}
