package com.example.new_aggregator.config;

import com.example.new_aggregator.config.errorHandling.DownStreamErrorHandler;
import com.example.new_aggregator.config.interceptor.ApiKeyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class ApplicationConfig
{

    @Autowired
    private ApiKeyInterceptor apiKeyInterceptor;
    
    @Autowired
    private DownStreamErrorHandler downStreamErrorHandler;
    
    @Bean(name= "gNewsClient")
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder)
    {
        return templateBuilder
                .interceptors(List.of(apiKeyInterceptor))
                .errorHandler(downStreamErrorHandler)
                .build();
    }
    
    
}
