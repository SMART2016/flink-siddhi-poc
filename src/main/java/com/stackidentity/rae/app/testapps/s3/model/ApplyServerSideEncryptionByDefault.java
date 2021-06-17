package com.stackidentity.rae.app.testapps.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplyServerSideEncryptionByDefault {
    @JsonProperty("SSEAlgorithm")
    public String getSSEAlgorithm() {
        return this.sSEAlgorithm;
    }

    public void setSSEAlgorithm(String sSEAlgorithm) {
        this.sSEAlgorithm = sSEAlgorithm;
    }

    String sSEAlgorithm;

    @JsonProperty("KMSMasterKeyID")
    public String getKMSMasterKeyID() {
        return this.kMSMasterKeyID;
    }

    public void setKMSMasterKeyID(String kMSMasterKeyID) {
        this.kMSMasterKeyID = kMSMasterKeyID;
    }

    String kMSMasterKeyID;
}
