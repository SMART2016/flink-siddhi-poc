package com.stackidentity.rae.app.config;


import com.stackidentity.rae.app.config.util.Stream;
import com.stackidentity.rae.app.connector.Consumers;
import com.stackidentity.rae.app.control.ControlStream;
import com.stackidentity.rae.app.control.model.RuleControlEvent;
import com.stackidentity.rae.app.serde.StringSchemaSerDe;
import com.stackidentity.rae.app.transformer.EventTransformer;
import com.stackidentity.rae.app.transformer.RuleTransformer;
import com.stackidentity.rae.app.transformer.S3AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;


@Slf4j
public class JobConfigurator {
  private final String BOOTSTRAP_SERVERS = "bootstrap.servers";
  private final String ZOOKEEPER_CONNECT = "zookeeper.connect";
  private final String GROUP_ID = "group.id";

  private final JobConfig config;
  private static final Map<String, EventTransformer<?,?>> eventTranformRepo = new HashMap<>();

  public JobConfigurator(final JobConfig config, ConfigurableApplicationContext ctx) {
    super();
    this.config = config;
  }

  public void initTransformers(){
    eventTranformRepo.put("s3.access.log",new S3AccessLog());
    eventTranformRepo.put("rule-transformer",new RuleTransformer());
    eventTranformRepo.put("json-transformer",null);
  }

  public EventTransformer<?,?> getTransformer(String transformerName){
    return eventTranformRepo.get(transformerName);
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

  /**
   * Returns a map containing list of substream from data stream and substream from main control stream for a common substream name.
   * @param env
   * @return Map<String,List<DataStream<?>>>
   *                   key(String): The Main Data stream name
   *                   Value (List<DataStream>) :
   *                                              - The list containing main Datastream at the 0th index,
   *                                              - Main Control(Rule) stream in the 1st index
   */
  public Map<String,List<DataStream<?>>> getInputAndRuleDataStreams(StreamExecutionEnvironment env){
    Properties kafkaProps = getKafkaConfigProperties();
    Map<String,List<DataStream<?>>> streamMap = new HashMap<>();

    //Populate splitted input Source  SubStreams from main input source(kafka topic) from config
    EventSources eventSources = config.getEventSources();
    List<Source> iSources = eventSources.getSources();


    System.out.println("Event Source "+eventSources);
    for (Source s : iSources){
      List<DataStream<?>> l = new ArrayList<>(2);

      //Flink kafka stream consumer
      FlinkKafkaConsumer<String> flinkKafkaConsumer =
              Consumers.createKafkaConsumerFor(s.getTopic(), kafkaProps,new StringSchemaSerDe());

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

  /**
   * Returns a map containing splitted streams of all main data and control streams based on the application.xml configurations
   * @param mainDataStreams : map of multiple main data streams as configured
   *                          - Each main stream will need to be splitted to substreams based on the matching "type"
   *                            in the event to the corresponding matching substream type.
   *                          - key : main data stream names
   *                          - Value: main input data streams
   * @param mainRuleStreams: map of multiple control/rule streams as configured, each main rule stream is mapped to a
   *                       main data stream with the property "mappingSourceDataStream" in application.xml
   *                       - key : main data stream names
   *                       - Value: control / rule data streams
   * @return Map<String, List<DataStream<?>>>
   *          - Keys : <mainstream name> + - + <the substream name>
   *          - values: List [ data substream   , rule substream] corresponding to the above key.
   * TODO: Add comments for why split the main data and rule source streams ?
   * TODO: check if same thing is possible with Source operators or not ?
   */
  public Map<String, List<DataStream<?>>> getSplittedDataAndControlStream(Map<String ,DataStream<?>> mainDataStreams , Map<String ,DataStream<RuleControlEvent>> mainRuleStreams){
    Map<String, List<DataStream<?>>> splittedStreams = new HashMap<>();

    //The list is of size 2 containing:
    //INDEX 0: substream for the main dataStreams based on the substream configuration for each main stream.
    //INDEX 1: substream for the main rule/control stream based on the substream configuration for each main rule streams.
    List<DataStream<?>> dataAndRuleStream = new ArrayList<>(2);

    //TODO: Next steps ....
    //Read config kafka.events.sources
    EventSources eventSources = config.getEventSources();

    //Read config kafka.events.sources.<Source.java>
    List<Source> iSources = eventSources.getSources();

    //Populates the final data and control stream map for each source stream by splitting the source stream to substreams.
    for (Source s : iSources){

      splittedStreams = getSubStreamsForData((DataStream<String>) mainDataStreams.get(s.getSiddhiStreamName()),s,splittedStreams);

    }

    //Populate Rule Source main Stream
    RuleSources ruleSources = config.getRuleSources();
    List<RuleSource> rSources = ruleSources.getSources();

    for (RuleSource r : rSources){
       splittedStreams = getSubStreamsForRule(mainRuleStreams.get(r.getMappingSourceDataStream()),r,splittedStreams);
    }

    return splittedStreams;
  }

  /**
   * populates the final map for data and control stream based on the source stream type (log,json)
   * @param mainStream
   * @param s
   * @param splittedStreams
   * @return
   */
  private Map<String,List<DataStream<?>>> getSubStreamsForData(DataStream<String> mainStream,Source s,final Map<String,List<DataStream<?>>> splittedStreams){
    List<SubStream> subStreams = s.getSubstreams();
    switch (s.getStreamType()){
      case "log":
            SubStream singleSubStream = subStreams.get(0);
            Map<String, DataStream<String>> subStreamMap  = Stream.splitLogStream(mainStream,(EventTransformer<String,String>)getTransformer(singleSubStream.getTransformer()),singleSubStream.getType());
            List<DataStream<?>> dataAndControlLst = new ArrayList<>(2);
            String subStreamName = Stream.getSubStreamName(s.getSiddhiStreamName(),singleSubStream.getType());
            dataAndControlLst.add(0,subStreamMap.get(singleSubStream.getType()));
            splittedStreams.put(subStreamName,dataAndControlLst);
            break;
      case "json":
            break;
      default:
          System.out.println("Invalid Source Stream type");
    }
    return splittedStreams;
  }

  /**
   * populates the final map for data and control stream based on the rule stream
   * @param ruleStream
   * @param r
   * @param splittedStreams: it contains the datastream for the mapping rule stream in the list already against the key: <maindatastream>-<substream>
   * @return
   */
  private Map<String,List<DataStream<?>>> getSubStreamsForRule(DataStream<RuleControlEvent> ruleStream,RuleSource r,final Map<String,List<DataStream<?>>> splittedStreams){
    List<SubStream> subStreams = r.getSubStreams();
    Map<String,EventTransformer<DataStream<RuleControlEvent>,DataStream<ControlEvent>>> subStreamConf = new HashMap<>();

    for (SubStream s : subStreams){
      //EventTransformer<DataStream<RuleControlEvent>,DataStream<ControlEvent>> t = getTransformer(s.getTransformer());
       subStreamConf.put(s.getType(),(EventTransformer<DataStream<RuleControlEvent>,DataStream<ControlEvent>>)getTransformer(s.getTransformer()) );
    }

    Map<String, DataStream<ControlEvent>> controlStreamMap = Stream.splitRuleStream(ruleStream,subStreamConf);

    //Iterate over the control streams map
    //get the final stream name <maindatastream>-<rulesubstreamtype>
    //fetch the List<DataStream<?>> to add the corresponding rule stream to the respective data stream
    controlStreamMap.forEach((ruleSubstreamType,ruleSubStream) -> {
      String subStreamName = Stream.getSubStreamName(r.getMappingSourceDataStream(),ruleSubstreamType);
      List<DataStream<?>> lst = splittedStreams.get(subStreamName);
      lst.add(1,ruleStream);
    });

    return splittedStreams;
  }


  /**
   * Returns the Main set of input data source streams for all Kafka input stream topics or other sources.
   * NOTE: Right now the main source for input data is kafka
   * @return Map<String ,DataStream<?>> main input datastreams corresponding to the input source
   *          - Applies general String deserialization on the incoming data
   *          - Key: main stream names
   *          - Value: Datastream w.r.t to the mainstream
   */
  public Map<String ,DataStream<?>> getMainInputSourceStreams(StreamExecutionEnvironment env){
    Map<String,DataStream<?>> mainStreamMap = new HashMap<>();

    //Read config kafka.events.sources
    EventSources eventSources = config.getEventSources();

    //Read config kafka.events.sources.<Source.java>
    List<Source> iSources = eventSources.getSources();
    System.out.println("Event Source "+eventSources);

    for (Source s : iSources){
      //Flink kafka stream consumer
      FlinkKafkaConsumer<String> flinkKafkaConsumer =
              Consumers.createKafkaConsumerFor(s.getTopic(), getKafkaConfigProperties(),new StringSchemaSerDe());

      //String containing newline separated lines.
      DataStream<?> inputS = env.addSource(flinkKafkaConsumer).uid(s.getSiddhiStreamName()).name(s.getSiddhiStreamName()).setParallelism(config.getFlinkJobParallelism());
      mainStreamMap.put(s.getSiddhiStreamName(),inputS);
    }
    return mainStreamMap;
  }


  /**
   * Returns the Main set of control(Rule) source streams for all Kafka input stream topics or other sources.
   * NOTE: Right now the main source for input data is kafka
   * @return Map<String ,DataStream<RuleControlEvent>> main input datastreams corresponding to the input source
   *          - Applies general String deserialization on the incoming data
   *          - key : mappingSourceDataStream from application.xml
   *          - Value: The Rule COntrol Event Datastream
   */
  public Map<String ,DataStream<RuleControlEvent>> getMainControlSourceStreams(StreamExecutionEnvironment env){
    Map<String,DataStream<RuleControlEvent>> mainRuleStreamMap = new HashMap<>();

    //Read config kafka.rules.sources
    RuleSources ruleSources = config.getRuleSources();

    //Read config kafka.rules.sources.<Source.java>
    List<RuleSource> rSources = ruleSources.getSources();

    System.out.println("Rule Sources "+rSources);

    for (RuleSource r : rSources){
      //Flink kafka control stream consumer
      DataStream<RuleControlEvent> mainRuleStream = null;
      try {
        mainRuleStream = getRuleControlStream(env, r.getTopic(), r.getControlStreamName(), config.getFlinkJobParallelism());
      }catch (Exception e){
        e.printStackTrace();
      }
      mainRuleStreamMap.put(r.getMappingSourceDataStream(),mainRuleStream);
    }

    //TODO: Use on spliited control stream return ruleControlStream.flatMap(new ControlEventTransformer()).uid("rule-transformer").name("rule-transformer");
    return mainRuleStreamMap;
  }



  private DataStream<ControlEvent> getRuleStream(StreamExecutionEnvironment env, String ruleTopic, String StreamName, int parallalism) throws Exception {
    //json needs extension jars to present during runtime.
    //Operator for identifying Failed Attempts to S3 by a user
    //NOTE: inputstream for event stream and outputstream for evaluated event stream should be configurable
    //and it should match with the rule in the control stream.
    ControlStream s = new ControlStream();
    DataStream<ControlEvent> ruleControlStream = s.getControlStream(env,getKafkaConfigProperties(),ruleTopic,StreamName,parallalism);
    return ruleControlStream;
  }

  private DataStream<RuleControlEvent> getRuleControlStream(StreamExecutionEnvironment env, String ruleTopic, String StreamName, int parallalism) throws Exception {
    //json needs extension jars to present during runtime.
    //Operator for identifying Failed Attempts to S3 by a user
    //NOTE: inputstream for event stream and outputstream for evaluated event stream should be configurable
    //and it should match with the rule in the control stream.
    ControlStream s = new ControlStream();
    DataStream<RuleControlEvent> ruleControlStream = s.getRuleControlStream(env,getKafkaConfigProperties(),ruleTopic,StreamName,parallalism);
    return ruleControlStream;
  }

  private  DataStream<String> getS3LogJsonStream(DataStream<String> inputS){
    //Created stream from each line in the input stream
    //DataStream<String> S3LogMsg = inputS.flatMap(new lineSplitter());

    //Converted each line to JSON
    EventTransformer<DataStream<String>, DataStream<String>> t =  (EventTransformer<DataStream<String>, DataStream<String>>) getTransformer("s3.access.log");
    DataStream<String> jsonS3LogMsgs =  t.transform(inputS);
    return jsonS3LogMsgs;
  }

} // class JobConfigurator ends
