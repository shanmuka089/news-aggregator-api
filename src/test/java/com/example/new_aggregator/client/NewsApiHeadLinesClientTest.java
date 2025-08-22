package com.example.new_aggregator.client;

import com.example.new_aggregator.client.impl.NewsApiClientImpl;
import com.example.new_aggregator.client.impl.NewsApiHeadLinesClientImpl;
import com.example.new_aggregator.config.ClientConfig;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiResponse;
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
public class NewsApiHeadLinesClientTest
{

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClientConfig clientConfig;

    @InjectMocks
    private NewsApiHeadLinesClientImpl newsApiClient;

    @Test
    void testFetchNewsByPreference() {

        ClientConfig.NewsApiClientConfig newsApiClientConfig = new ClientConfig.NewsApiClientConfig();
        newsApiClientConfig.setNewsApiBaseUrl("http://localhost:8080");
        newsApiClientConfig.setNewsApiTopHeadlinesPath("/everything");

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
                eq(NewsApiResponse.class))).thenReturn(ResponseEntity.ok(new NewsApiResponse()));

        ResponseDto<NewsApiResponse> response = newsApiClient.fetchHeadlinesByQuery(query);

        assert response.getStatus() == ResponseStatus.SUCCESS;
        assert response.getData() != null;
    }

}
