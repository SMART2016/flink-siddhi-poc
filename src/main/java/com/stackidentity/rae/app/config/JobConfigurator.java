package com.stackidentity.rae.app.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;



@Slf4j
public class JobConfigurator {
  private final String BOOTSTRAP_SERVERS = "bootstrap.servers";
  private final String ZOOKEEPER_CONNECT = "zookeeper.connect";
  private final String GROUP_ID = "group.id";

  private final JobConfig config;

  public JobConfigurator(final JobConfig config, ConfigurableApplicationContext ctx) {
    super();
    this.config = config;
  }

  public StreamExecutionEnvironment getConfiguredFlinkEnvironment() {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    //Set Time characteristics default is EventTime and it expects proper watermarking to process the data
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
    env.setParallelism(1);
    //ExecutionConfig executionConfig = env.getConfig();
    return env;
  }
  /**
   * Extracts and returns the Kafka related configuration as a property bag.
   *
   * @return Kafka configuration properties
   */
  public Properties getKafkaConfigProperties() {
    Properties kafkaProperties = new Properties();
    kafkaProperties.setProperty(BOOTSTRAP_SERVERS, config.getKafkaBootstrapServers());
    kafkaProperties.setProperty(ZOOKEEPER_CONNECT, config.getZookeeperServers());
    kafkaProperties.setProperty(GROUP_ID, config.getFlinkJobGroupId());

    return kafkaProperties;
  }

} // class JobConfigurator ends
