package flinksidhi.event.s3.source;



import flinksidhi.event.s3.transform.JsonToS3;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

//Just Returns a single instance of the AwsS3 root event
public class S3EventSource implements SourceFunction<String> {

    @Override
    public void run(SourceContext<String> sourceContext)  {
        long timestamp = System.currentTimeMillis();
        sourceContext.collectWithTimestamp(JsonToS3.getInput(), timestamp);
    }

    @Override
    public void cancel() {
        // Do nothing
    }
}
