/******************************************************************************
 * Copyright (c) 2019 Qualys, Inc. All Rights Reserved.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *****************************************************************************/
package com.stackidentity.rae.app.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//Spring
@Component
@Scope("singleton")

// lombok
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JobConfig implements AppConfigConstants {
    
    @Value("${" + SPRING_APP_NAME + "}")
    private String appName;
    
    @Value("${" + FLINK_JOB_NAME + "}")
    private String jobName;
    
    @Value("${" + KAFKA_ZOOKEEPER_CONNECT + "}")
    private String zookeeperServers;
    
    @Value("${" + KAFKA_BOOTSTRAP_SERVERS + "}")
    private String kafkaBootstrapServers;

    @Value("${" + KAFKA_EVENTS_SINK_TOPIC + "}")
    private String kafkaEventsSinkTopic;

    @Value("${" + KAFKA_EVENTS_SINK_SIDDHI_STREAM_NAME + "}")
    private String kafkaEventsSinkSiddhiStreamName;

    @Value("${" + FLINK_JOB_GROUP_ID + "}")
    private String flinkJobGroupId;

    @Value("${" + FLINK_JOB_PARALLELISM + "}")
    private int flinkJobParallelism;

    @Value("${" + FLINK_STREAM_TIME_CHARACTERISTIC + "}")
    private TimeCharacteristic flinkStreamTimeCharacteristic;


    @Autowired
    private EventSources eventSources;

    @Autowired
    private RuleSources ruleSources;

}



