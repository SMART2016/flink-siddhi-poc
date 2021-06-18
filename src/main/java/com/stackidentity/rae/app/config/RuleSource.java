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
public class RuleSource {
    private String topic;
    private String controlStreamName;
    private int parallelism;
    private List<SubStream> subStreams;
    private String mappingSourceDataStream;
}
