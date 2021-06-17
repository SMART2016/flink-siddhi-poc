package com.stackidentity.rae.app.config;


import com.stackidentity.rae.app.connector.Consumers;
import com.stackidentity.rae.app.control.ControlStream;
import com.stackidentity.rae.app.s3.transform.S3AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.util.Collector;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;


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

  /**
   * Initialize and return Flink execution environment
   * @return StreamExecutionEnvironment
   */
  public StreamExecutionEnvironment getConfiguredFlinkEnvironment() {
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    //Set Time characteristics default is EventTime and it expects proper watermarking to process the data
    env.setStreamTimeCharacteristic(config.getFlinkStreamTimeCharacteristic());
    env.setParallelism(config.getFlinkJobParallelism());
    //ExecutionConfig executionConfig = env.getConfig();
    return env;
  }

  /**
   * Initialize Siddhi CEP env with the required extensions
   * @TODO: Extensions can be made configurable latter.
   * @param env
   * @return SiddhiCEP
   */
  public SiddhiCEP initSiddhiCEPEnv(StreamExecutionEnvironment env){
    SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);
    cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
    cep.registerExtension("json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);
    return cep;
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

  public Map<String,List<DataStream<?>>> getInputAndRuleDataStreams(StreamExecutionEnvironment env){
    Properties kafkaProps = getKafkaConfigProperties();
    Map<String,List<DataStream<?>>> streamMap = new HashMap<>();

    //Populate input Source main Streams
    EventSources eventSources = config.getEventSources();
    List<Source> iSources = eventSources.getSources();


    System.out.println("Event Source "+eventSources);
    for (Source s : iSources){
      List<DataStream<?>> l = new ArrayList<>(2);

      //Flink kafka stream consumer
      FlinkKafkaConsumer<String> flinkKafkaConsumer =
              Consumers.getInputJsonMessageConsumer(s.getTopic(), kafkaProps);

      //String containing newline separated lines.
      DataStream<?> inputS = env.addSource(flinkKafkaConsumer).name(s.getSiddhiStreamName()).setParallelism(config.getFlinkJobParallelism());
      //TODO: TEMP step will be removed
      DataStream<?>  inputStream = getS3LogJsonStream((DataStream<String>) inputS);
      l.add(0,inputStream);
      streamMap.put(s.getSiddhiStreamName(),l);
    }


    //Populate Rule Source main Stream
    RuleSources ruleSources = config.getRuleSources();
    List<RuleSource> rSources = ruleSources.getSources();
    System.out.println("Rule Sources "+rSources);
    for (RuleSource s : rSources){
      //Flink kafka stream consumer
      DataStream<ControlEvent> ruleStream = null;
      try {
        ruleStream = getRuleStream(env, s.getTopic(), s.getControlStreamName(), config.getFlinkJobParallelism());
      }catch (Exception e){
        e.printStackTrace();
      }
      List<DataStream<?>> l = streamMap.get(s.getMappingSourceDataStream());

      l.add(1,ruleStream);
      streamMap.put(s.getMappingSourceDataStream(),l);
    }

    return streamMap;
  }
  private static DataStream<ControlEvent> getRuleStream(StreamExecutionEnvironment env,String ruleTopic,String StreamName,int parallalism) throws Exception {
    //json needs extension jars to present during runtime.
    //Operator for identifying Failed Attempts to S3 by a user
    //NOTE: inputstream for event stream and outputstream for evaluated event stream should be configurable
    //and it should match with the rule in the control stream.
    ControlStream s = new ControlStream();
    DataStream<ControlEvent> ruleControlStream = s.getControlStream(env,ruleTopic,StreamName,parallalism);
    return ruleControlStream;
  }

  private static DataStream<String> getS3LogJsonStream(DataStream<String> inputS){
    //Created stream from each line in the input stream
    DataStream<String> S3LogMsg = inputS.flatMap(new lineSplitter());

    //Converted each line to JSON
    DataStream<String> jsonS3LogMsgs =  inputS.map(s3LogMsg -> {
      return  S3AccessLog.toJson(s3LogMsg);
    });

    return jsonS3LogMsgs;
  }

  public static class lineSplitter implements FlatMapFunction<String, String> {
    @Override
    public void flatMap(String sentence, Collector<String> out) throws Exception {
      for (String line: sentence.split("\n")) {
        out.collect(line);
      }
    }
  }


} // class JobConfigurator ends
