package com.example.new_aggregator.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthorizationHeaderInterceptor implements ClientHttpRequestInterceptor
{
    
    @Value("${client.news-api.api-key}")
    private String API_KEY_VALUE;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
    {
        HttpHeaders httpHeaders = request.getHeaders();
        
        if(!httpHeaders.containsKey(HttpHeaders.AUTHORIZATION)) {
            httpHeaders.set(HttpHeaders.AUTHORIZATION, API_KEY_VALUE);
        }
        log.info("Making News API Call Request: URI: {} & Headers: {}", request.getURI(), request.getHeaders());
        return execution.execute(request, body);
    }

}
