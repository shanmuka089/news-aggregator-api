package com.example.new_aggregator.client;

import com.example.new_aggregator.client.impl.GNewsHeadlinesClientImpl;
import com.example.new_aggregator.client.impl.NewsApiClientSourceImpl;
import com.example.new_aggregator.config.ClientConfig;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiSource;
import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.utils.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewsApiClientSourceTest
{

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClientConfig clientConfig;

    @InjectMocks
    private NewsApiClientSourceImpl newsApiClientSource;

    @Test
    void testFetchNewsByPreference() {

        ClientConfig.NewsApiClientConfig newsApiClientConfig = new ClientConfig.NewsApiClientConfig();
        newsApiClientConfig.setNewsApiBaseUrl("http://localhost:8080");
        newsApiClientConfig.setNewsApiSourcesPath("/everything");

        when(clientConfig.getNewsApiClient()).thenReturn(newsApiClientConfig);

        QueryDto query = QueryDto.builder()
                .query("test")
                .category("technology")
                .source("google-news")
                .country("us")
                .language("en")
                .build();

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(SourcesResponseDto.class))).thenReturn(ResponseEntity.ok(new SourcesResponseDto()));

        ResponseDto<SourcesResponseDto> response = newsApiClientSource.fetchNewsBySources(query);

        assert response.getStatus() == ResponseStatus.SUCCESS;
        assert response.getData() != null;
    }
}
