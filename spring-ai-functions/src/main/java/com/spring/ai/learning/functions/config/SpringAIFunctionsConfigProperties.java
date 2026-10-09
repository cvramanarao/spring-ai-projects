package com.spring.ai.learning.functions.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "saf.aiapp")
@Getter
@Setter
public class SpringAIFunctionsConfigProperties {

    private String geoCoordinatesApiUrl;
    private String weatherApiUrl;
}
