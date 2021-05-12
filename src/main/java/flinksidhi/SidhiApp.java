package flinksidhi;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.siddhi.SiddhiCEP;

import java.util.Map;

public class SidhiApp {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Event> input = env.addSource(new RandomEventSource(5));
       // Output is a map containg a single event in key:value pairs
        //Example: {id=3, name=test_event, price=0.1693391942422522, timestamp=1620831023065}
        DataStream<Map<String,Object>> output = SiddhiCEP
                .define("inputStream", input, "id", "name", "price", "timestamp")
                .cql("from inputStream select timestamp, id, name, price insert into  outputStream")
                .returnAsMap("outputStream");


        String resultPath = "./output";
        output.writeAsText(resultPath, FileSystem.WriteMode.OVERWRITE);
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
