package com.example.new_aggregator.config.interceptor;

import com.example.new_aggregator.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Component
public class ApiKeyQueryParamInterceptor implements ClientHttpRequestInterceptor
{

    @Value("${client.g-news.api-key}")
    private String API_KEY_VALUE;
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
    {
        URI requestUri = request.getURI();
        
        if(requestUri.getQuery() != null && requestUri.getQuery().contains(Constants.API_KEY)) {
            return execution.execute(request, body);
        }
        
        URI newRequestUri = UriComponentsBuilder.fromUri(requestUri)
                .queryParam(Constants.API_KEY, API_KEY_VALUE)
                .build(false)
                .toUri();
        
        HttpRequest newRequest = new HttpRequestWrapper(request) {
            @Override
            public URI getURI() {
                return newRequestUri;
            }
        };
        log.info("Adding API Key to request: {}", newRequestUri);
        return execution.execute(newRequest, body);
    }

}
