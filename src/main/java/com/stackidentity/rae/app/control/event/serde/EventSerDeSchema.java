package com.stackidentity.rae.app.control.event.serde;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;


public interface EventSerDeSchema<T>
        extends DeserializationSchema<T>, SerializationSchema<T> {
    @Override
    default boolean isEndOfStream(T nextElement) {
        return false;
    }
}
