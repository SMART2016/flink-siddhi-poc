package flinksidhi.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty("LocationConstraint")
    public String getLocationConstraint() {
        return this.locationConstraint;
    }

    public void setLocationConstraint(String locationConstraint) {
        this.locationConstraint = locationConstraint;
    }

    String locationConstraint;
}
