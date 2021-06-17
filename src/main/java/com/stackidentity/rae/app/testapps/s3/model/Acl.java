package com.stackidentity.rae.app.testapps.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Acl {
    @JsonProperty("Owner")
    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    Owner owner;

    @JsonProperty("Grants")
    public List<Grant> getGrants() {
        return this.grants;
    }

    public void setGrants(List<Grant> grants) {
        this.grants = grants;
    }

    List<Grant> grants;
}
