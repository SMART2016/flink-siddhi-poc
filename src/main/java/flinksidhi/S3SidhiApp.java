package flinksidhi;

import flinksidhi.app.internalStreamSiddhiApp;
import org.apache.flink.util.UserCodeClassLoader;
//import flinksidhi.app.kafkaStreamApp;

public class S3SidhiApp {
    public static void main(String[] args) {
        try {
            Class.forName("io.siddhi.extension.execution.json.function.ToJSONObjectFunctionExtension");
            Class.forName("io.siddhi.extension.execution.json.function.GetStringJSONFunctionExtension");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        internalStreamSiddhiApp.start();
        //kafkaStreamApp.start();
    }
}

