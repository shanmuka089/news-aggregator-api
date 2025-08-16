package com.example.new_aggregator.client.impl;

import com.example.new_aggregator.client.GNewsClient;
import com.example.new_aggregator.config.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GNewsClientImpl implements GNewsClient
{
    @Autowired
    @Qualifier("gNewsClient")
    private RestTemplate restTemplate;
    
    @Autowired
    private ClientConfig clientConfig;
    
    public void test(){

        String baseUrl = clientConfig.getGsClient().getGsClientSearchPath();

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("q", "Apple OR Microsoft")
                .queryParam("lang", "en")
                .build(false)
                .toUriString();
        
        System.out.println("GS News Client invoked path: " + clientConfig.getGsClient().getGsClientSearchPath());
        String response = restTemplate.getForObject(url, String.class);
    }
}
