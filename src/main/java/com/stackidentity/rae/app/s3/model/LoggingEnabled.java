package com.stackidentity.rae.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoggingEnabled {
    @JsonProperty("TargetBucket")
    public String getTargetBucket() {
        return this.targetBucket;
    }

    public void setTargetBucket(String targetBucket) {
        this.targetBucket = targetBucket;
    }

    String targetBucket;

    @JsonProperty("TargetPrefix")
    public String getTargetPrefix() {
        return this.targetPrefix;
    }

    public void setTargetPrefix(String targetPrefix) {
        this.targetPrefix = targetPrefix;
    }

    String targetPrefix;
}
