package flinksidhi.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Map;

public class ConvertJavaMapToJson {

    public static String convert(Map<String, Object> m) {
        String json = "{}";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
                 json = objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
