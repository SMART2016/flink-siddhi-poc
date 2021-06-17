package com.stackidentity.rae.app.testapps.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {
    @JsonProperty("ApplyServerSideEncryptionByDefault")
    public ApplyServerSideEncryptionByDefault getApplyServerSideEncryptionByDefault() {
        return this.applyServerSideEncryptionByDefault;
    }

    public void setApplyServerSideEncryptionByDefault(ApplyServerSideEncryptionByDefault applyServerSideEncryptionByDefault) {
        this.applyServerSideEncryptionByDefault = applyServerSideEncryptionByDefault;
    }

    ApplyServerSideEncryptionByDefault applyServerSideEncryptionByDefault;
}
