package com.stackidentity.rae.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessBlock {
    @JsonProperty("PublicAccessBlockConfiguration")
    public PublicAccessBlockConfiguration getPublicAccessBlockConfiguration() {
        return this.publicAccessBlockConfiguration;
    }

    public void setPublicAccessBlockConfiguration(PublicAccessBlockConfiguration publicAccessBlockConfiguration) {
        this.publicAccessBlockConfiguration = publicAccessBlockConfiguration;
    }

    PublicAccessBlockConfiguration publicAccessBlockConfiguration;
}
