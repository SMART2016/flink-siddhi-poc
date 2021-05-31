package flinksidhi.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServerSideEncryptionConfiguration {
    @JsonProperty("Rules")
    public List<Rule> getRules() {
        return this.rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    List<Rule> rules;
}
