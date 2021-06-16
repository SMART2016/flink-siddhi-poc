package com.stackidentity.rae.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner {
    @JsonProperty("DisplayName")
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    String displayName;

    @JsonProperty("ID")
    public String getID() {
        return this.iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    String iD;
}
