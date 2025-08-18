package com.example.new_aggregator.client.impl;

import com.example.new_aggregator.client.GNewsHeadLinesClient;
import com.example.new_aggregator.client.util.ClientUtils;
import com.example.new_aggregator.config.ClientConfig;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GNewsHeadlinesClientImpl implements GNewsHeadLinesClient
{
    @Autowired
    @Qualifier("gNewsClient")
    private RestTemplate restTemplate;

    @Autowired
    private ClientConfig clientConfig;

    @Override
    public ResponseDto<GNewsResponse> fetchHeadlinesByQuery(QueryDto query)
    {
        String baseUrl = clientConfig.getGsClient().getGsClientBaseUrl();
        String gsClientSearchPath = clientConfig.getGsClient().getGsClientTopHeadlinesPath();

        String url = ClientUtils.buildUrl(baseUrl, gsClientSearchPath, query, true);
        
        ResponseEntity<GNewsResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                GNewsResponse.class
        );

        return ClientUtils.getResponseData(responseEntity);
    }
}
