package com.stackidentity.rae.app.config;

public interface AppConfigConstants {
    String SPRING_APP_NAME = "spring.application.name";

    String KAFKA_ZOOKEEPER_CONNECT = "kafka.zookeeperConnect";
    String KAFKA_BOOTSTRAP_SERVERS = "kafka.bootstrapServers";
    String KAFKA_EVENTS_CONSUMER_NAME = "kafka.events.consumer.name";
    String KAFKA_ALERTS_PRODUCER_NAME = "kafka.alerts.producer.name";
    String KAFKA_EVENTS_SINK_TOPIC = "kafka.events.sink.topic";
    String KAFKA_EVENTS_SINK_SIDDHI_STREAM_NAME = "kafka.events.sink.siddhiStreamName";
    String KAFKA_EVENTS = "kafka.events";
    String KAFKA_RULES = "kafka.rules";

    String FLINK_STREAM_TIME_CHARACTERISTIC = "flink.streamTimeCharacteristic";
    String FLINK_JOB_NAME = "flink.job.name";
    String FLINK_JOB_GROUP_ID = "flink.job.groupId";
    String FLINK_JOB_PARALLELISM = "flink.job.parallelism";

    String FLINK_JOB_CHECKPOINT_INTERVAL_MS     = "flink.job.checkpoint.intervalMillis";
    String FLINK_JOB_CHECKPOINT_ENABLE          = "flink.job.checkpoint.enable";
    String FLINK_JOB_CHECKPOINT_FAILURE_TOLERABLE_NUMBER = "flink.job.checkpoint.failureTolerableNumber";
    String FLINK_JOB_CHECKPOINT_MODE            = "flink.job.checkpoint.mode";
    String FLINK_JOB_CHECKPOINT_PAUSE           = "flink.job.checkpoint.minPauseBetween";

    String FLINK_ENABLE_LOCAL_WEBUI             = "flink.enableLocalWebUI";

}
