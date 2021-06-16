package com.stackidentity.rae;

import com.stackidentity.rae.app.FlinkSiddhiRuleApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlinkSidhiService {
    public static void main(String[] args) {

        // initialize spring context
        ConfigurableApplicationContext ctx = SpringApplication.run(FlinkSidhiService.class, args);

        try {
            FlinkSiddhiRuleApp.start(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //S3FailedAttemptSiddhiApp.start();
        //S3ActualAccessSiddhiApp.start();
    }
}

