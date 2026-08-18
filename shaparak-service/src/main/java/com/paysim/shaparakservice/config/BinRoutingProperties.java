package com.paysim.shaparakservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "shaparak.routing")
public class BinRoutingProperties {

    private Map<String, String> binMap;
    private String defaultQueue;
}
