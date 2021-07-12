package com.stackidentity.rae.app.config;


import com.stackidentity.rae.app.config.util.Stream;
import com.stackidentity.rae.app.connector.Consumers;
import com.stackidentity.rae.app.connector.Producer;
import com.stackidentity.rae.app.control.ControlStream;
import com.stackidentity.rae.app.control.model.RuleControlEvent;
import com.stackidentity.rae.app.extension.EncryptionKeyValidation;
import com.stackidentity.rae.app.serde.StringSchemaSerDe;
import com.stackidentity.rae.app.transformer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.ConfigConstants;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
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
    private StreamExecutionEnvironment env;
    private SiddhiCEP cep;
    private static final Map<String, EventTransformer<?, ?>> eventTranformRepo = new HashMap<>();

    public JobConfigurator(final JobConfig config, ConfigurableApplicationContext ctx) {
        super();
        this.config = config;
    }

    /**
     * Initializes the transformers for Raw events from flink sources.
     */
    public void initTransformers() {
        eventTranformRepo.put("s3-access-log", new S3AccessLog());
        eventTranformRepo.put("s3-access-log-record", new S3AccessLogRecord());
        eventTranformRepo.put("rule-transformer", new RuleTransformer());
        eventTranformRepo.put("json-transformer", null);
        eventTranformRepo.put("rule-event-transformer", new RuleEventTransformer());
    }

    public EventTransformer<?, ?> getTransformer(String transformerName) {
        return eventTranformRepo.get(transformerName);
    }

    /**
     * Initialize and return Flink execution environment
     * Initializes Stream transformers repository
     *
     * @return StreamExecutionEnvironment
     */
    public StreamExecutionEnvironment getConfiguredFlinkEnvironment() {
        StreamExecutionEnvironment env = null;
        if(config.isEnableLocalUI()){
            System.out.println("--- Enabling Web UI Stream Execution Environment ---");
            Configuration config = new Configuration();
            config.setBoolean(ConfigConstants.LOCAL_START_WEBSERVER, true);
            env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config);
        }else{
            System.out.println("--- Enabling Simple Execution Environment ---");
            env = StreamExecutionEnvironment.getExecutionEnvironment();
        }

        this.env = env;
        //Set Time characteristics default is EventTime and it expects proper watermarking to process the data
        env.setStreamTimeCharacteristic(config.getFlinkStreamTimeCharacteristic());
        env.setParallelism(config.getFlinkJobParallelism());
        //ExecutionConfig executionConfig = env.getConfig();
        if (config.isCheckPointEnabled()) {
            configureCheckpointing();
        }
        initTransformers();



        //env.getStreamGraph().setSavepointRestoreSettings(SavepointRestoreSettings.forPath("/Users/dipanjan/work/stackidentity/flink-siddhi-poc/backup/3f7a11e7991af7be9026a54f873cead5"));
        return env;
    }

    /**
     * Checkpoint configuration ...
     */
    private void configureCheckpointing(){

        env.enableCheckpointing(config.getFlinkJobCheckpointInterval());

        env.getCheckpointConfig().setCheckpointingMode(config.getFlinkJobCheckpointMode());
        env.getCheckpointConfig().setTolerableCheckpointFailureNumber(config.getFailureTolerableNumber());

        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(config.getMinPauseBetweenCheckpoint());
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        //env.getCheckpointConfig().setCheckpointTimeout(config.getCheckPointTimeout());
        //env.getCheckpointConfig().setMaxConcurrentCheckpoints(config.getMaxConcurrentCheckpoints());
        env.getCheckpointConfig().setPreferCheckpointForRecovery(true);
        //Statebackend for checkpointing and persisting state
        env.setStateBackend(new FsStateBackend("file:///tmp/checkpoint"));
    }
    /**
     * Initialize Siddhi CEP env with the required extensions
     *
     * @param env
     * @return SiddhiCEP
     * @TODO: Extensions can be made configurable latter.
     */
    public SiddhiCEP initSiddhiCEPEnv(StreamExecutionEnvironment env) {
        SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);
        cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
        cep.registerExtension("json:toString", io.siddhi.extension.execution.json.function.ToJSONStringFunctionExtension.class);
        cep.registerExtension("json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);
        cep.registerExtension("json:setElement", io.siddhi.extension.execution.json.function.SetElementJSONFunctionExtension.class);
        cep.registerExtension("json:tokenizeAsObject", io.siddhi.extension.execution.json.JsonTokenizerAsObjectStreamProcessorFunction.class);
        cep.registerExtension("enc:isEncryptionKeyValid", EncryptionKeyValidation.class);
        cep.registerExtension("json:getBool", io.siddhi.extension.execution.json.function.GetBoolJSONFunctionExtension.class);
        cep.registerExtension("json:getFloat", io.siddhi.extension.execution.json.function.GetFloatJSONFunctionExtension.class);
        cep.registerExtension("json:getInt", io.siddhi.extension.execution.json.function.GetIntJSONFunctionExtension.class);
        cep.registerExtension("json:getObject", io.siddhi.extension.execution.json.function.GetObjectJSONFunctionExtension.class);
        cep.registerExtension("json:isExists", io.siddhi.extension.execution.json.function.IsExistsJSONFunctionExtension.class);


        this.cep = cep;
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
     * Generates the out stream by applying Siddhi CEP transformation on the finalDataAndRuleStream which contains
     * Data stream and mapping rule stream
     *
     * @param finalDataAndRuleStream key: sourcestreamName - substreamtype, value: list containg datastream and corresponding mapping rule stream
     * @return List<DataStream < Map < String, Object>>> list of output data streams , each output data stream corresponds to each main input data source
     */
    public List<DataStream<Map<String, Object>>> getTransformedOutputStreams(Map<String, List<DataStream<?>>> finalDataAndRuleStream) {
        List<DataStream<Map<String, Object>>> outStreamLst = new ArrayList<>();
        List<Source> mainDataSource = getEventMainSourceList();
        Map<String, FlinkKafkaProducer<Map<String, Object>>> sinks = getKafkaSinks();
        for (Source s : mainDataSource) {
            String outStreamName = s.getSink().getOutputStreamName();
            String mainInputSourceStreamName = s.getSiddhiStreamName();
            List<SubStream> subs = s.getSubstreams();
            for (SubStream sub : subs) {
                String dataAndRuleStreamKey = Stream.getSubStreamName(mainInputSourceStreamName, sub.getType());
                List<DataStream<?>> ruleAndDataStream = finalDataAndRuleStream.get(dataAndRuleStreamKey);
                String[] fields = (String[]) sub.getFields().toArray(new String[0]);
                cep.registerStream(dataAndRuleStreamKey, ruleAndDataStream.get(0), fields);
                DataStream<Map<String, Object>> outputStream = cep.from(dataAndRuleStreamKey).
                        cql((DataStream<ControlEvent>) ruleAndDataStream.get(1)).
                        returnAsMap(outStreamName);
                outputStream.addSink(sinks.get(mainInputSourceStreamName));
                outStreamLst.add(outputStream);
            }

        }
        return outStreamLst;
    }

    private List<Source> getEventMainSourceList() {
        //Read config kafka.events.sources
        EventSources eventSources = config.getEventSources();

        //Read config kafka.events.sources.<Source.java>
        List<Source> iSources = eventSources.getSources();

        return iSources;
    }

    /**
     * Returns the kafka sinks with respect to the Input data sources
     *
     * @return Map<String, FlinkKafkaProducer < Map < String, Object>>> : key : main source stream name and value is the sink for the main data source
     */
    private Map<String, FlinkKafkaProducer<Map<String, Object>>> getKafkaSinks() {
        Map<String, FlinkKafkaProducer<Map<String, Object>>> sinks = new HashMap<>();
        List<Source> iSources = getEventMainSourceList();

        for (Source s : iSources) {
            String topic = s.getSink().getTopic();
            //Flink kafka stream Producer
            String kafkaHost = config.getKafkaBootstrapServers();
            FlinkKafkaProducer<Map<String, Object>> flinkKafkaProducer = Producer.createMapProducer(env, topic, kafkaHost);
            sinks.put(s.getSiddhiStreamName(), flinkKafkaProducer);
        }
        return sinks;
    }

    /**
     * Returns a map containing splitted streams of all main data and control streams based on the application.xml configurations
     *
     * @param mainDataStreams  : map of multiple main data streams as configured
     *                         - Each main stream will need to be splitted to substreams based on the matching "type"
     *                         in the event to the corresponding matching substream type.
     *                         - key : main data stream names
     *                         - Value: main input data streams
     * @param mainRuleStreams: map of multiple control/rule streams as configured, each main rule stream is mapped to a
     *                         main data stream with the property "mappingSourceDataStream" in application.xml
     *                         - key : main data stream names
     *                         - Value: control / rule data streams
     * @return Map<String, List < DataStream < ?>>>
     * - Keys : <mainstream name> + _ + <the substream name>
     * - values: List [ data substream   , rule substream] corresponding to the above key.
     * TODO: Add comments for why split the main data and rule source streams ?
     * TODO: check if same thing is possible with Source operators or not ?
     */
    public Map<String, List<DataStream<?>>> getSplittedDataAndControlStream(Map<String, DataStream<String>> mainDataStreams, Map<String, DataStream<RuleControlEvent>> mainRuleStreams) {
        Map<String, List<DataStream<?>>> splittedStreams = new HashMap<>();

        //TODO: Next steps ....
        //Read config kafka.events.sources
        EventSources eventSources = config.getEventSources();

        //Read config kafka.events.sources.<Source.java>
        List<Source> iSources = eventSources.getSources();

        //Populates the final data and control stream map for each source stream by splitting the source stream to substreams.
        for (Source s : iSources) {

            splittedStreams = getSubStreamsForData((DataStream<String>) mainDataStreams.get(s.getSiddhiStreamName()), s, splittedStreams);

        }

        //Populate Rule Source main Stream
        RuleSources ruleSources = config.getRuleSources();
        List<RuleSource> rSources = ruleSources.getSources();

        for (RuleSource r : rSources) {
            splittedStreams = getSubStreamsForRule(mainRuleStreams.get(r.getMappingSourceDataStream()), r, splittedStreams);
        }

        return splittedStreams;
    }

    /**
     * populates the final map for data and control stream based on the source stream type (log,json)
     *
     * @param mainStream
     * @param s configuration for mainstream
     * @param splittedStreams
     * @return
     */
    private Map<String, List<DataStream<?>>> getSubStreamsForData(DataStream<String> mainStream, Source s, final Map<String, List<DataStream<?>>> splittedStreams) {
        List<SubStream> subStreams = s.getSubstreams();
        switch (s.getStreamType()) {
            case "log":
                // The assumption right now is as log streams are structured data and not json so for now there will be only single substream for log data streams.
                SubStream singleSubStream = subStreams.get(0);
                Map<String, DataStream<String>> subStreamMap = Stream.splitLogStream(mainStream, (EventTransformer<String, String>) getTransformer(singleSubStream.getTransformer()), singleSubStream.getType());
                List<DataStream<?>> dataAndControlLst = new ArrayList<>(2);
                String subStreamName = Stream.getSubStreamName(s.getSiddhiStreamName(), singleSubStream.getType());
                dataAndControlLst.add(0, subStreamMap.get(singleSubStream.getType()));
                splittedStreams.put(subStreamName, dataAndControlLst);
                break;
            case "json": //can have 1 or more than one substreams for json data stream.
                Map<String, EventTransformer<DataStream<String>, DataStream<String>>> subStreamConf = new HashMap<>();
                for (SubStream sub : subStreams){
                   //Right now there is no JSON transformation forseen or registered so the value for the substream types will be null
                    subStreamConf.put(sub.getType(),(EventTransformer<DataStream<String>, DataStream<String>>)getTransformer(sub.getTransformer()));
                }
                Map<String, DataStream<String>> jsonDataStreamMap = Stream.splitJsonStringStream(mainStream, subStreamConf);

                jsonDataStreamMap.forEach((subStreamActualName , jsonSubStream) -> {
                    String subStreamFinalName = Stream.getSubStreamName(s.getSiddhiStreamName(), subStreamActualName);
                    List<DataStream<?>> dataAndControlList = new ArrayList<>(2);
                    dataAndControlList.add(0, jsonSubStream);
                    splittedStreams.put(subStreamFinalName, dataAndControlList);
                });
                break;

            default:
                System.out.println("Invalid Source Stream type");
        }
        return splittedStreams;
    }

    /**
     * populates the final map for data and control stream based on the rule stream
     *
     * @param ruleStream
     * @param r
     * @param splittedStreams: it contains the datastream for the mapping rule stream in the list already against the key: <maindatastream>_<substream>
     * @return
     */
    private Map<String, List<DataStream<?>>> getSubStreamsForRule(DataStream<RuleControlEvent> ruleStream, RuleSource r, final Map<String, List<DataStream<?>>> splittedStreams) {
        //TODO: Debugging
        System.out.println("Main Data Stream: ----> ");
        ruleStream.print();
        List<SubStream> subStreams = r.getSubStreams();
        Map<String, EventTransformer<DataStream<RuleControlEvent>, DataStream<ControlEvent>>> subStreamConf = new HashMap<>();
        //Map<String, EventTransformer<RuleControlEvent, ControlEvent>> subStreamConf = new HashMap<>();
        for (SubStream s : subStreams) {
            //EventTransformer<DataStream<RuleControlEvent>,DataStream<ControlEvent>> t = getTransformer(s.getTransformer());
            subStreamConf.put(s.getType(),(EventTransformer<DataStream<RuleControlEvent>, DataStream<ControlEvent>>)getTransformer(s.getTransformer()));
        }

        Map<String, DataStream<ControlEvent>> controlStreamMap = Stream.splitRuleStream(ruleStream, subStreamConf);

        //Iterate over the control streams map
        //get the final stream name <maindatastream>_<rulesubstreamtype>
        //fetch the List<DataStream<?>> to add the corresponding rule stream to the respective data stream
        controlStreamMap.forEach((ruleSubstreamType, ruleSubStream) -> {
            String subStreamName = Stream.getSubStreamName(r.getMappingSourceDataStream(), ruleSubstreamType);
            List<DataStream<?>> lst = splittedStreams.get(subStreamName);
            //TODO: Debugging
            ruleSubStream.print();
            if(lst != null && lst.size() != 0) {
                lst.add(1, ruleSubStream);
            }
        });

        return splittedStreams;
    }


    /**
     * Returns the Main set of input data source streams for all Kafka input stream topics or other sources.
     * NOTE: Right now the main source for input data is kafka
     *
     * @return Map<String, DataStream < ?>> main input datastreams corresponding to the input source
     * - Applies general String deserialization on the incoming data
     * - Key: main stream names
     * - Value: Datastream w.r.t to the mainstream
     */
    public Map<String, DataStream<String>> getMainInputSourceStreams() {
        Map<String, DataStream<String>> mainStreamMap = new HashMap<>();
        List<Source> iSources = getEventMainSourceList();
        System.out.println("Input sources: -> "+iSources);
        for (Source s : iSources) {
            //Flink kafka stream consumer
            FlinkKafkaConsumer<String> flinkKafkaConsumer =
                    Consumers.createKafkaConsumerFor(s.getTopic(), getKafkaConfigProperties(), new StringSchemaSerDe());

            //String containing newline separated lines.
            DataStream<String> inputS = env.addSource(flinkKafkaConsumer).uid(s.getSiddhiStreamName()).name(s.getSiddhiStreamName()).setParallelism(config.getFlinkJobParallelism());
            inputS.filter(r -> {
                boolean flag = true;
                flag = r != null && r.trim() != "";
                return flag;
            });
            mainStreamMap.put(s.getSiddhiStreamName(), inputS);
        }
        return mainStreamMap;
    }


    /**
     * Returns the Main set of control(Rule) source streams for all Kafka input stream topics or other sources.
     * NOTE: Right now the main source for input data is kafka
     *
     * @return Map<String, DataStream < RuleControlEvent>> main input datastreams corresponding to the input source
     * - Applies general String deserialization on the incoming data
     * - key : mappingSourceDataStream from application.xml
     * - Value: The Rule COntrol Event Datastream
     */
    public Map<String, DataStream<RuleControlEvent>> getMainControlSourceStreams() {
        Map<String, DataStream<RuleControlEvent>> mainRuleStreamMap = new HashMap<>();

        //Read config kafka.rules.sources
        RuleSources ruleSources = config.getRuleSources();

        //Read config kafka.rules.sources.<Source.java>
        List<RuleSource> rSources = ruleSources.getSources();

        System.out.println("Rule Sources " + rSources);

        for (RuleSource r : rSources) {
            //Flink kafka control stream consumer
            DataStream<RuleControlEvent> mainRuleStream = null;
            try {
                mainRuleStream = getRuleControlStream(env, r.getTopic(), r.getControlStreamName(), config.getFlinkJobParallelism());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mainRuleStreamMap.put(r.getMappingSourceDataStream(), mainRuleStream);
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
        DataStream<ControlEvent> ruleControlStream = s.getControlStream(env, getKafkaConfigProperties(), ruleTopic, StreamName, parallalism);
        return ruleControlStream;
    }

    private DataStream<RuleControlEvent> getRuleControlStream(StreamExecutionEnvironment env, String ruleTopic, String StreamName, int parallalism) throws Exception {
        //json needs extension jars to present during runtime.
        //Operator for identifying Failed Attempts to S3 by a user
        //NOTE: inputstream for event stream and outputstream for evaluated event stream should be configurable
        //and it should match with the rule in the control stream.
        ControlStream s = new ControlStream();
        DataStream<RuleControlEvent> ruleControlStream = s.getRuleControlStream(env, getKafkaConfigProperties(), ruleTopic, StreamName, parallalism);
        return ruleControlStream;
    }

} // class JobConfigurator ends
