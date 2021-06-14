package flinksidhi.app.s3;

import flinksidhi.app.s3.transform.S3AccessLog;
import flinksidhi.connector.Consumers;
import flinksidhi.connector.Producer;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.streaming.siddhi.SiddhiStream;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.util.Collector;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static flinksidhi.control.ControlStream.getControlStream;


public class SiddhiTestApp {



    private static final String kafkaAddress = "localhost:9092";
    private static final String zkAddress = "localhost:2181";
    private static final String outputTopicFailedAttempt = "EVENT_STREAM_OUTPUT";
    //private static final String consumerGroup = "EVENT_STREAM1";


    //The query says:
    //for each event fro inputstream , create a json obect from the event string message and insert the object into temp table.
    //from temp table select json properties of the json object in temp and insert them as individual columns/fields in temp2 table.
    //Create two internal streams from temp2 e2 followed by e1 and  for each event in the temp2 stream and if another event arrives
    //within 30 seconds with a value for e2.attempted_response attribute being equal to e1.attempted_response of the initial event e1,
    //an output is generated and sent to the output stream.
    private static final String SIDDHI_PATTERN_TEST_CQL ="from inputStream select json:toObject(s3log) as obj insert into temp;" +
            "from temp select " +
            "json:getString(obj,'$.s3log.requestId') as access_requester," +
            "json:getString(obj,'$.s3log.httpStatus') as attempted_response," +
            "json:getString(obj,'$.s3log.bucket') as accessed_bucket," +
            "json:getString(obj,'$.s3log.operation') as attempted_action " +
            "insert into temp2;"+
            "from every( e1 = temp2 ) -> e2 = temp2[ e1.access_requester == access_requester and " +
            "(e1.attempted_response == attempted_response) and attempted_response == '404'] " +
            "within 30 sec " +
            "select e1.access_requester , e1.attempted_response, e1.accessed_bucket,e1.attempted_action " +
            "insert into outputStream;";



    public static void start() throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //DataStream<String> inputS = env.addSource(new S3EventSource());

        //Set Time characteristics default is EventTime and it expects proper watermarking to process the data
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        env.setParallelism(1);

        //Create Siddhi Env and register siddhi extensions
        SiddhiCEP cep = SiddhiCEP.getSiddhiEnvironment(env);
        cep.registerExtension("json:toObject", io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension.class);
        cep.registerExtension( "json:getString", io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension.class);

        //Get Input DataStream from Kafka for S3 Access Logs
        DataStream<String> inputStream = getInputDataStream(env).filter(r -> r != null && r.trim() != "" && r.contains("s3log"));
        DataStream<ControlEvent> ruleStream = getRuleStream(env).filter(r -> r != null );

        ruleStream.print();
        //DataStream<String> finalDataStream = inputStream.keyBy(new DataKeySelector()).connect(ruleStream.keyBy(new RuleKeySelector())).flatMap( new DataCollector());


        //json needs extension jars to present during runtime.
        //Operator for identifying Failed Attempts to S3 by a user
        final Map<String,String> queryMap =new HashMap<>();
        cep.registerStream("inputStream", inputStream, "s3log");


        SiddhiStream.SingleSiddhiStream<String> singleSiddhiS = cep.from("inputStream");
        SiddhiStream.ExecutionSiddhiStream ess = singleSiddhiS.cql(ruleStream);
        DataStream<Map<String,Object>> failedAttempts = ess.returnAsMap("outputStream");

        //Add Data stream sink for failed attempts-- flink producer
        //Flink kafka stream Producer
        FlinkKafkaProducer<Map<String, Object>> flinkKafkaProducer =
                Producer.createMapProducer(env,outputTopicFailedAttempt, kafkaAddress);

        failedAttempts.addSink(flinkKafkaProducer);
        failedAttempts.print();

        try {
            env.execute("TestSiddhi");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class lineSplitter implements FlatMapFunction<String, String> {
        @Override
        public void flatMap(String sentence, Collector<String> out) throws Exception {
            for (String line: sentence.split("\n")) {
                out.collect(line);
            }
        }
    }

    private static DataStream<String> getInputDataStream(StreamExecutionEnvironment env){
        String inputTopic = "EVENT_STREAM_INPUT";
        String consumerGrp = "";
        //Flink kafka stream consumer
        FlinkKafkaConsumer<String> flinkKafkaConsumer =
                Consumers.createInputMessageConsumer(inputTopic, kafkaAddress,zkAddress, consumerGrp);

        //String containing newline separated lines.
        DataStream<String> inputS = env.addSource(flinkKafkaConsumer);

        //Created stream from each line in the input stream
        DataStream<String> S3LogMsg = inputS.flatMap(new S3FailedAttemptSiddhiApp.lineSplitter());

        //Converted each line to JSON
        DataStream<String> jsonS3LogMsgs =  inputS.map(s3LogMsg -> {
            return  S3AccessLog.toJson(s3LogMsg);
        });

        return jsonS3LogMsgs;
    }



    private static DataStream<ControlEvent> getRuleStream(StreamExecutionEnvironment env) throws Exception {
        //json needs extension jars to present during runtime.
        //Operator for identifying Failed Attempts to S3 by a user
        //NOTE: inputstream for event stream and outputstream for evaluated event stream should be configurable
        //and it should match with the rule in the control stream.
        DataStream<ControlEvent> ruleControlStream = getControlStream(env);
        return ruleControlStream;
    }

}

/**
 * Example Rule STream record :
 * {
 *     "key": "S3_LOG",
 *     "rule":""
 * }
 */
class RuleKeySelector implements KeySelector<String,String>{
    @Override
    public String getKey(String rule) throws Exception {
        JSONObject ruleJson = new JSONObject(rule);
        return ruleJson.getString("key");
    }
}

/**
 * Example Data STream record :
 * {
 *     "key": "S3_LOG",
 *     "s3log":{ // JSON for S3 log object ....}
 * }
 */
class DataKeySelector implements KeySelector<String,String>{
    @Override
    public String getKey(String data) throws Exception {
        JSONObject dataJson = new JSONObject(data);
        return dataJson.getString("key");
    }
}

class DataCollector extends RichCoFlatMapFunction<String, String, String> {

    private ValueState<String> ruleState = null;

    @Override
    public void open(Configuration config) {
        ValueStateDescriptor<String> descriptor = new ValueStateDescriptor<String>(
                // state name
                "rule",
                // type information of state
                TypeInformation.of(new TypeHint<String>() {
                }));
        ruleState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public void flatMap2(String rule, Collector<String> out) throws Exception {
        JSONObject ruleJson = new JSONObject(rule);
        String ruleStr = ruleJson.getString("rule");
        ruleState.update(ruleStr);
    }

    @Override
    public void flatMap1(String data, Collector<String> out) throws Exception {
        if (ruleState.value() != "") {
            JSONObject dataJson = new JSONObject(data);
            dataJson.append("rule",ruleState.value());
            out.collect(dataJson.toString());
        }
    }
}
