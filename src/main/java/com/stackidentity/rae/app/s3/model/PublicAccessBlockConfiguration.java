package com.stackidentity.rae.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicAccessBlockConfiguration {
    @JsonProperty("BlockPublicAcls")
    public boolean getBlockPublicAcls() {
        return this.blockPublicAcls;
    }

    public void setBlockPublicAcls(boolean blockPublicAcls) {
        this.blockPublicAcls = blockPublicAcls;
    }

    boolean blockPublicAcls;

    @JsonProperty("IgnorePublicAcls")
    public boolean getIgnorePublicAcls() {
        return this.ignorePublicAcls;
    }

    public void setIgnorePublicAcls(boolean ignorePublicAcls) {
        this.ignorePublicAcls = ignorePublicAcls;
    }

    boolean ignorePublicAcls;

    @JsonProperty("BlockPublicPolicy")
    public boolean getBlockPublicPolicy() {
        return this.blockPublicPolicy;
    }

    public void setBlockPublicPolicy(boolean blockPublicPolicy) {
        this.blockPublicPolicy = blockPublicPolicy;
    }

    boolean blockPublicPolicy;

    @JsonProperty("RestrictPublicBuckets")
    public boolean getRestrictPublicBuckets() {
        return this.restrictPublicBuckets;
    }

    public void setRestrictPublicBuckets(boolean restrictPublicBuckets) {
        this.restrictPublicBuckets = restrictPublicBuckets;
    }

    boolean restrictPublicBuckets;
}
