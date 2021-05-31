package flinksidhi.app.s3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Logging {
    @JsonProperty("LoggingEnabled")
    public LoggingEnabled getLoggingEnabled() {
        return this.loggingEnabled;
    }

    public void setLoggingEnabled(LoggingEnabled loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    LoggingEnabled loggingEnabled;
}
