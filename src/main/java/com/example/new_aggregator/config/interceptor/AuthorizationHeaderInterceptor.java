package com.example.new_aggregator.config.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
        
        return execution.execute(request, body);
    }

}
