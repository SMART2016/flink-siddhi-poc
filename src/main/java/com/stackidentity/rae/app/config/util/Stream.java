package com.stackidentity.rae.app.config.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackidentity.rae.app.control.model.RuleControlEvent;
import com.stackidentity.rae.app.transformer.EventTransformer;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.siddhi.control.ControlEvent;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains main utility code for splitting streams.
 */
public class Stream {

    private static final String COMPARISON_KEY = "type";

    /**
     * Returns the splitted substream for the Json string main stream.
     * The subtreams names corresponds to the key that will be used to compare with the key in the main stream to create substreams.
     *
     * @param mainStream
     * @param subStreamConf
     * @return map of substream splitted from main stream carrying JSON string records
     * Key: substream name (events.sources.substreams.type in the application.xml)
     */
    public static Map<String, DataStream<String>> splitJsonStringStream(DataStream<String> mainStream, final Map<String, EventTransformer<DataStream<String>, DataStream<String>>> subStreamConf) {
        final Map<String, DataStream<String>> subStreamMap = new HashMap<>();

        final Map<String, OutputTag<String>> subStreamTags = new HashMap<>();
        subStreamConf.forEach((key, transformer) -> {
            OutputTag<String> outputTag = new OutputTag(key,Types.STRING);
            subStreamTags.put(key, outputTag);
        });

        //Splitting main stream into substreams as configured in application.xml using flinks side output operation.
        SingleOutputStreamOperator<String> mainDataStream = mainStream.process(
                new ProcessFunction<String, String>() {

                    @Override
                    public void processElement(
                            String value,
                            Context ctx,
                            Collector<String> out) throws Exception {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(value);
                        if (subStreamConf.containsKey(jsonNode.get(COMPARISON_KEY).asText())) {
                            ctx.output(subStreamTags.get(jsonNode.get(COMPARISON_KEY).asText()), value);
                        }

                    }
                });

        subStreamTags.forEach((subStreamName, sideOutputTag) -> {
            DataStream<String> sideOutputStream = mainDataStream.getSideOutput(sideOutputTag);
            subStreamMap.put(subStreamName, sideOutputStream);
        });


        return subStreamMap;
    }


    /**
     * Returns the splitted substream for the RuleControlEvent control main stream.
     * The subtreams names corresponds to the key that will be used to compare with the key in the main stream to create substreams.
     *
     * @param ruleStream
     * @param subStreamConf
     * @return map of substream splitted from main control stream carrying RuleControlEvent records
     * Key: substream name (rules.sources.substreams.type in the application.xml)
     */
    public static Map<String, DataStream<ControlEvent>> splitRuleStream(DataStream<RuleControlEvent> ruleStream, final Map<String, EventTransformer<DataStream<RuleControlEvent>, DataStream<ControlEvent>>> subStreamConf) {
        final Map<String, DataStream<ControlEvent>> subStreamMap = new HashMap<>();

        final Map<String, OutputTag<RuleControlEvent>> subStreamTags = new HashMap<>();
        subStreamConf.forEach((key, transformer) -> {
            OutputTag<RuleControlEvent> outputTag = new OutputTag(key, Types.POJO(RuleControlEvent.class));
            subStreamTags.put(key, outputTag);
        });

        //Splitting main stream into substreams as configured in application.xml using flinks side output operation.
        SingleOutputStreamOperator<RuleControlEvent> sideStream = ruleStream.process(
                new ProcessFunction<RuleControlEvent, RuleControlEvent>() {

                    @Override
                    public void processElement(
                            RuleControlEvent value,
                            Context ctx,
                            Collector<RuleControlEvent> out) throws Exception {
                        //its an array of strings
                        String[] compareKey = value.getType();
                        //NOTE: Not sure if this will work, where same value is posted to multiple side outputs
                        //This is needed because a single rule can be keyed or mapped or needed to multiple data streams
                        for (String key : compareKey) {
                            if (subStreamConf.containsKey(key)) {
                                ctx.output(subStreamTags.get(key), value);
                            }
                        }

                    }
                });

        subStreamTags.forEach((subStreamName, sideOutputTag) -> {
            EventTransformer<DataStream<RuleControlEvent>, DataStream<ControlEvent>> t = subStreamConf.get(subStreamName);
            DataStream<ControlEvent> sideOutputStream = t.transform(sideStream.getSideOutput(sideOutputTag));
            subStreamMap.put(subStreamName, sideOutputStream);
        });


        return subStreamMap;
    }

    // Standby code for rule stream splitting -------------------
    public static Map<String, DataStream<ControlEvent>> splitRuleStream1(DataStream<RuleControlEvent> ruleStream, final Map<String, EventTransformer<DataStream<RuleControlEvent>, DataStream<ControlEvent>>> subStreamConf) {
        final Map<String, DataStream<ControlEvent>> subStreamMap = new HashMap<>();
        subStreamConf.forEach((key,ruleTransformer) ->{
            DataStream<RuleControlEvent> rce =  filteredStream(ruleStream,key);
            subStreamMap.put(key,ruleTransformer.transform(rce));
        });

        return subStreamMap;
    }
    private static DataStream<RuleControlEvent> filteredStream (DataStream<RuleControlEvent> ruleStream,String splitKey){
        DataStream<RuleControlEvent> r1 = ruleStream.filter(
                rule -> {
                    boolean flag = true;
                    String[] compareKey = rule.getType();

                    for (String key : compareKey) {
                        if(splitKey.equals(key)){
                            flag = true;
                            break;
                        }
                    }
                    return flag;
                }
        );

        return r1;
    }

    //Standby code for ends for rule stream splitting

    /**
     * Splits the structured log main stream into a single substream.
     * <b>NOTE</b>: This was not needed as right now we are creating a single source topic for structured logs
     * Example: only one topic is used to publish S3 access log, and so a substream is not needed but to test
     * Stream splitting this is done this way.
     *
     * @param mainStream
     * @param transformer
     * @param subStreamname
     * @return Map<String, DataStream < String>>
     * Key: substream name
     * value: splitted substream from main log stream
     */
    public static Map<String, DataStream<String>> splitLogStream(DataStream<String> mainStream, final EventTransformer<String, String> transformer, final String subStreamname) {
        final Map<String, DataStream<String>> subStreamMap = new HashMap<>();
        OutputTag<String> outputTag = new OutputTag<>(subStreamname, Types.STRING);
        SingleOutputStreamOperator<String> mainDataStream = mainStream.process(
                new ProcessFunction<String, String>() {

                    @Override
                    public void processElement(
                            String value,
                            Context ctx,
                            Collector<String> out) throws Exception {

                        String jsonLogMsg = transformer.transform(value);
                        //Emit output to the single substream
                        ctx.output(outputTag, jsonLogMsg);
                    }
                });
        DataStream<String> sideOutputStream = mainDataStream.getSideOutput(outputTag);
        subStreamMap.put(subStreamname, sideOutputStream);
        return subStreamMap;
    }

    /**
     * Return substream name based on main stream and substream separated by (_)
     *
     * @param mainStreamName
     * @param subStreamName
     * @return
     */
    public static String getSubStreamName(String mainStreamName, String subStreamName) {
        return subStreamName;
        //return mainStreamName + "_" + subStreamName;
    }

    /**
     * Return the actual substream name from the final stream name
     *
     * @param finalSubStream
     * @return
     */
    public static String getSubStreamNameFrm(String finalSubStream) {
        return finalSubStream.split("_")[1];
    }

}


