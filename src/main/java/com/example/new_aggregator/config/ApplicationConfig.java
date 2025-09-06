package com.example.new_aggregator.config;

import com.example.new_aggregator.config.errorHandling.ClientErrorResponseHandler;
import com.example.new_aggregator.config.interceptor.ApiKeyQueryParamInterceptor;
import com.example.new_aggregator.config.interceptor.AuthorizationHeaderInterceptor;
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
    private ApiKeyQueryParamInterceptor apiKeyQueryParamInterceptor;
    
    @Autowired
    private ClientErrorResponseHandler clientErrorResponseHandler;
    
    @Autowired
    private AuthorizationHeaderInterceptor authorizationHeaderInterceptor;
    
    
    @Bean(name= "gNewsClient")
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder)
    {
        return templateBuilder
                .interceptors(List.of(apiKeyQueryParamInterceptor))
                .errorHandler(clientErrorResponseHandler)
                .build();
    }
    
    @Bean(name = "newsApiClient")
    public RestTemplate restTemplateWithAuthorizationHeader(RestTemplateBuilder templateBuilder)
    {

        return templateBuilder
                .interceptors(List.of(authorizationHeaderInterceptor))
                .errorHandler(clientErrorResponseHandler)
                .build();
    }
    
    
}
