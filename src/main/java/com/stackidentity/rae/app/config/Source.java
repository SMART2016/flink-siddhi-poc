package com.stackidentity.rae.app.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
//lombok
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Source implements Serializable {
  private String topic;
  private String siddhiStreamName;
  private int parallelism;
  private List<SubStream> substreams;
  private Sink sink;
}
