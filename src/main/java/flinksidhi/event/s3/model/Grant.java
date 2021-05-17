package flinksidhi.event.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grant {
    @JsonProperty("Grantee")
    public Grantee getGrantee() {
        return this.grantee;
    }

    public void setGrantee(Grantee grantee) {
        this.grantee = grantee;
    }

    Grantee grantee;

    @JsonProperty("Permission")
    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    String permission;
}
