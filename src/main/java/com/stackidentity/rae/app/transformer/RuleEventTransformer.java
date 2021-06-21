package com.stackidentity.rae.app.transformer;

import com.stackidentity.rae.app.control.model.RuleControlEvent;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.streaming.siddhi.control.MetadataControlEvent;

public class RuleEventTransformer implements EventTransformer<RuleControlEvent, ControlEvent> {
    @Override
    public ControlEvent transform(RuleControlEvent ruleEvent) {
        return getControlEventFrmRuleEvent(ruleEvent);
    }

    public ControlEvent getControlEventFrmRuleEvent(final RuleControlEvent value) {
        String ruleId = value.getRuleId();
        String query = value.getRuleDefinition();

        ControlEvent c = null;
        switch (value.getAction()) {
            case "add":
                c = MetadataControlEvent.builder().addExecutionPlan(ruleId, query).build();
                return c;
            case "update":
                c = MetadataControlEvent.builder().updateExecutionPlan(ruleId, query).build();
                return c;
            default:
                break;
        }
        return null;
    }
}
