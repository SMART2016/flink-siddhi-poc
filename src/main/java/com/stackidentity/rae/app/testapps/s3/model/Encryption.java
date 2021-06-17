package com.stackidentity.rae.app.testapps.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Encryption {
    @JsonProperty("ServerSideEncryptionConfiguration")
    public ServerSideEncryptionConfiguration getServerSideEncryptionConfiguration() {
        return this.serverSideEncryptionConfiguration;
    }

    public void setServerSideEncryptionConfiguration(ServerSideEncryptionConfiguration serverSideEncryptionConfiguration) {
        this.serverSideEncryptionConfiguration = serverSideEncryptionConfiguration;
    }

    ServerSideEncryptionConfiguration serverSideEncryptionConfiguration;
}
