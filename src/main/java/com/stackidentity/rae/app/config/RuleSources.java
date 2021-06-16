package com.stackidentity.rae.app.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = AppConfigConstants.KAFKA_RULES)
@Component
// lombok
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RuleSources {
    private List<Source> sources;
}
