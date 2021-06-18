package com.stackidentity.rae.app.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//lombok
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SubStream {
    private String streamName;
    private String type;
    private String transformer;
    private List<String> fields;
}
