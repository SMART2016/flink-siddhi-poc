package com.stackidentity.rae.app.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.hiramsoft.commons.jsalparser.JSalParser;
import com.hiramsoft.commons.jsalparser.S3LogEntry;

import java.util.List;

public class S3AccessLogRecord implements EventTransformer<String, String> {


    public String transform(String record) {
        StringBuilder sb = new StringBuilder("{ \"type\": \"s3.access.log\",\"s3log\": ");
        String jsonS3LogEntry = "";
        List<S3LogEntry> entries = JSalParser.parseS3Log(record);

        for (int i = 0; i < entries.size(); i++) {
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
