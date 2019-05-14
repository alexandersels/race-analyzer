package com.racing.analyzer.backend.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "parsing")
@Data
public class ParsingConfig {
    private boolean loggingEnabled;
    private boolean createSnapshots;
}
