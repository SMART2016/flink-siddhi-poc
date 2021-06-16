package com.stackidentity.rae.app.control.event.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.stackidentity.rae.app.control.event.model.RuleControlEvent;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;

public class ControlEventSerDeSchema implements EventSerDeSchema<RuleControlEvent> {
    private static final long serialVersionUID = 1L;

    private ObjectMapper mapper;

    {
        // create the mapper
        mapper = new ObjectMapper();
        // enable pretty printing
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public TypeInformation<RuleControlEvent> getProducedType() {
        return TypeExtractor.getForClass(RuleControlEvent.class);
    }

    @Override
    public byte[] serialize(final RuleControlEvent element) {
        try {
            return mapper.writeValueAsBytes(element);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public RuleControlEvent deserialize(final byte[] message) {
        try {
            return mapper.readValue(message, RuleControlEvent.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
