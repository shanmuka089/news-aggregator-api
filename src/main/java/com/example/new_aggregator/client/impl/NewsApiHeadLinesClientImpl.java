package com.example.new_aggregator.client.impl;

import com.example.new_aggregator.client.NewsApiHeadLinesClient;
import com.example.new_aggregator.client.util.ClientUtils;
import com.example.new_aggregator.config.ClientConfig;
import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponse;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class NewsApiHeadLinesClientImpl implements NewsApiHeadLinesClient
{

    @Qualifier("newsApiClient")
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientConfig clientConfig;
    
    @Override
    public ResponseDto<SourcesResponse> fetchHeadlinesByQuery(QueryDto query)
    {
        String baseUrl = clientConfig.getNewsApiClient().getNewsApiBaseUrl();
        String gsClientSearchPath = clientConfig.getNewsApiClient().getNewsApiTopHeadlinesPath();

        String url = ClientUtils.buildUrl(baseUrl, gsClientSearchPath, query);

        ResponseEntity<SourcesResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                SourcesResponse.class
        );

        return ClientUtils.getResponseData(responseEntity);
    }

}
