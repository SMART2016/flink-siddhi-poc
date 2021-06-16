package com.stackidentity.rae.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

//Root Object of the AWS S3 event Data model
public class Root {
    @JsonProperty("awsS3")
    public AwsS3 getAwsS3() {
        return this.awsS3;
    }

    public void setAwsS3(AwsS3 awsS3) {
        this.awsS3 = awsS3;
    }

    AwsS3 awsS3;
}
