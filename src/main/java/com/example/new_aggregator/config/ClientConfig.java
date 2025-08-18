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
    private NewsApiClientConfig newsApiClient = new NewsApiClientConfig();
    
    @Setter
    @Getter
    public static class GsClientConfig {
        private String gsClientBaseUrl;
        private String gsClientSearchPath;
        private String gsClientTopHeadlinesPath;
    }

    @Setter
    @Getter
    public static class NewsApiClientConfig {
        public String newsApiBaseUrl;
        public String newsApiSearchPath;
        public String newsApiTopHeadlinesPath;
        public String newsApiSourcesPath;
    }
    
    
}
