package flinksidhi;

import flinksidhi.app.s3.SiddhiTestApp;

public class S3SidhiApp {
    public static void main(String[] args) {
        try {
            SiddhiTestApp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //S3FailedAttemptSiddhiApp.start();
        //S3ActualAccessSiddhiApp.start();
    }
}

