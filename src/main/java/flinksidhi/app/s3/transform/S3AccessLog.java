package flinksidhi.app.s3.transform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hiramsoft.commons.jsalparser.JSalParser;
import com.hiramsoft.commons.jsalparser.S3LogEntry;

import java.util.List;

public class S3AccessLog {

    public static String toJson(String logs){
        StringBuilder sb = new StringBuilder("{ \"key\": \"S3_LOG\",\"s3log\": ");
        String jsonS3LogEntry = "";
        List<S3LogEntry> entries = JSalParser.parseS3Log(logs);

        for(int i=0;i<entries.size();i++) {
            S3LogEntry entry = entries.get(i);
            // Creating Object of ObjectMapper define in Jackson API
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new ParameterNamesModule());
            mapper.registerModule(new Jdk8Module());
            mapper.registerModule(new JavaTimeModule());
            try {
                // Converting the Java object into a JSON string
                jsonS3LogEntry = mapper.writeValueAsString(entry);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        return sb.append(jsonS3LogEntry).append("}").toString();
    }
}
