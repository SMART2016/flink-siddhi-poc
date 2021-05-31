package flinksidhi.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grantee {
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

    @JsonProperty("Type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    @JsonProperty("URI")
    public String getURI() {
        return this.uRI;
    }

    public void setURI(String uRI) {
        this.uRI = uRI;
    }

    String uRI;
}
