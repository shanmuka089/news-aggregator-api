package com.example.new_aggregator.client.impl;

import com.example.new_aggregator.client.NewsApiClientSource;
import com.example.new_aggregator.client.util.ClientUtils;
import com.example.new_aggregator.config.ClientConfig;
import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
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
public class NewsApiClientSourceImpl implements NewsApiClientSource
{

    @Qualifier("newsApiClient")
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientConfig clientConfig;

    @Override
    public ResponseDto<SourcesResponseDto> fetchNewsBySources(QueryDto queryDto)
    {
        String baseUrl = clientConfig.getNewsApiClient().getNewsApiBaseUrl();
        String gsClientSearchPath = clientConfig.getNewsApiClient().getNewsApiSourcesPath();

        String url = ClientUtils.buildUrl(baseUrl, gsClientSearchPath, queryDto, true);

        ResponseEntity<SourcesResponseDto> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                SourcesResponseDto.class
        );

        return ClientUtils.getResponseData(responseEntity);
    }

}
