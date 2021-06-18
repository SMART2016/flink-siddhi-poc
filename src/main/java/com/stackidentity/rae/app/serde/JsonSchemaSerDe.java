package com.stackidentity.rae.app.serde;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonSchemaSerDe implements EventSerDeSchema<JSONObject> {

    public JsonSchemaSerDe() {

    }

    @Override
    public JSONObject deserialize(byte[] message) throws IOException {
        JSONObject json = null;
        try {
            json = new JSONObject(new String(message, StandardCharsets.UTF_8));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public boolean isEndOfStream(JSONObject nextElement) {
        return false;
    }

    @Override
    public byte[] serialize(JSONObject element) {
        return new byte[0];
    }

    @Override
    public TypeInformation<JSONObject> getProducedType() {
        return null;
    }
}
