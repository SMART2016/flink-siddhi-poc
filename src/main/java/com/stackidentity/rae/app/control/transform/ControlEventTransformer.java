package com.stackidentity.rae.app.control.transform;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stackidentity.rae.app.control.model.RuleControlEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.streaming.siddhi.control.MetadataControlEvent;
import org.apache.flink.util.Collector;


public final class ControlEventTransformer implements
        FlatMapFunction<RuleControlEvent, ControlEvent> {

    private static final long serialVersionUID = 1L;
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

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

