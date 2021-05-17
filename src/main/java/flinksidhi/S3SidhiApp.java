package flinksidhi;

import flinksidhi.event.s3.source.S3EventSource;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.siddhi.SiddhiCEP;

import java.util.Map;

public class S3SidhiApp {

    public static final String S3_CQL = "from inputStream select json:toObject(awsS3) as obj insert into temp;" +
            "from temp select json:getString(obj,'$.awsS3.ResourceType') as affected_resource_type," +
            "json:getString(obj,'$.awsS3.Details.Name') as affected_resource_name," +
            "json:getString(obj,'$.awsS3.Encryption.ServerSideEncryptionConfiguration') as encryption," +
            "json:getString(obj,'$.awsS3.Encryption.ServerSideEncryptionConfiguration.Rules[0].ApplyServerSideEncryptionByDefault.SSEAlgorithm') as algorithm insert into temp2; " +
            "from temp2 select  affected_resource_name,affected_resource_type, " +
            "ifThenElse(encryption == ' ','Fail','Pass') as state," +
            "ifThenElse(encryption != ' ' and algorithm == 'aws:kms','None','Critical') as severity insert into outputStream";


    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> inputS = env.addSource(new S3EventSource());
        //json needs extension jars to present during runtime.
        DataStream<Map<String,Object>> output = SiddhiCEP
                .define("inputStream",inputS,"awsS3")
                .cql(S3_CQL)
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
