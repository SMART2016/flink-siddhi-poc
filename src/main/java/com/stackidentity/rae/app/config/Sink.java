package com.stackidentity.rae.app.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
//lombok
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sink  implements Serializable {
    private String topic;
    private String outputStreamName;
}
