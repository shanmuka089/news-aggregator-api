package com.example.new_aggregator.client;

import com.example.new_aggregator.client.impl.GNewsClientImpl;
import com.example.new_aggregator.config.ClientConfig;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GNewsClientTest
{

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClientConfig clientConfig;
    
    @InjectMocks
    private GNewsClientImpl gNewsClient;
    
    @Test
    void testFetchNewsByPreference() {

        ClientConfig.GsClientConfig gsClientConfig = new ClientConfig.GsClientConfig();
        gsClientConfig.setGsClientBaseUrl("http://localhost:8080");
        gsClientConfig.setGsClientSearchPath("/everything");
        
        when(clientConfig.getGsClient()).thenReturn(gsClientConfig);

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
                eq(GNewsResponse.class))).thenReturn(ResponseEntity.ok(new GNewsResponse()));
        
        ResponseDto<GNewsResponse> response = gNewsClient.fetchNewsByPreference(query);
        
        assert response.getStatus() == ResponseStatus.SUCCESS;
        assert response.getData() != null;
    }

}
