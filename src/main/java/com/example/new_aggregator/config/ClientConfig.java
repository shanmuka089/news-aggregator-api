package com.example.new_aggregator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientConfig
{
    private GsClientConfig gsClient = new GsClientConfig();
    
    @Setter
    @Getter
    public static class GsClientConfig {
        public String gsClientSearchPath;
    }
}
