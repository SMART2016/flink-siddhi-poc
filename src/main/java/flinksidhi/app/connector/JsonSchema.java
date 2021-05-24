package flinksidhi.app.connector;

import flinksidhi.event.s3.transform.JsonToS3;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonSchema  implements DeserializationSchema<JSONObject>, SerializationSchema<JSONObject> {

    public JsonSchema(){

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
