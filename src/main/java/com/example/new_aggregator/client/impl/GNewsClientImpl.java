package com.example.new_aggregator.client.impl;

import com.example.new_aggregator.client.GNewsClient;
import com.example.new_aggregator.client.util.ClientUtils;
import com.example.new_aggregator.config.ClientConfig;
import com.example.new_aggregator.models.domain.NewsResponse;
import com.example.new_aggregator.models.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
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
    
    public ResponseDto<NewsResponse> fetchNewsByQuery(String query){

        String baseUrl = clientConfig.getGsClient().getGsClientSearchPath();

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("q", "Apple OR Microsoft")
                .queryParam("lang", "en")
                .build(false)
                .toUriString();
        
        ResponseEntity<NewsResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                NewsResponse.class
        );
        
        return ClientUtils.getResponseData(responseEntity);
    }

    @Override
    public ResponseDto<NewsResponse> fetchNewsByQueryAndLanguage(String query, String language)
    {

        return null;
    }

    @Override
    public ResponseDto<NewsResponse> fetchNewsByQueryAndLanguageAndCountry(String query, String language, String country)
    {

        return null;
    }
    
}
