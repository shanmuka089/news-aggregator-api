package com.example.new_aggregator.controller;

import com.example.new_aggregator.TestUtils;
import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.TopHeadLinesService;
import com.example.new_aggregator.utils.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsHeadlinesControllerTest
{
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private TopHeadLinesService topHeadLinesService;
    
    private static String token;
    
    @BeforeEach
    void setUp() {
        token = TestUtils.generateToken();
    }
    
    @Test
    void testGetNewsHeadlines() {
        
        when(topHeadLinesService.fetchHeadLinesFromAllSources())
            .thenReturn(new ResponseDto<>(ResponseStatus.SUCCESS, new NewsResponseDto()));
        
        try {
            mockMvc.perform(get("/news-headlines").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.data").isNotEmpty());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testFetchHeadlinesByQuery() {
        
        when(topHeadLinesService.fetchTopHeadlines(ArgumentMatchers.any(QueryDto.class)))
            .thenReturn(new ResponseDto<>(ResponseStatus.SUCCESS, new NewsResponseDto()));
        
        try {
            mockMvc.perform(get("/news-headlines/sports")
                    .header("Authorization", "Bearer " + token)
                    .param("language", "en")
                    .param("country", "us")
                    .param("topic", "football")
                    .param("source", "bbc-news"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.data").isNotEmpty());
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    void testFetchHeadLinesBySources() {
        
        when(topHeadLinesService.fetchHeadlinesFromPreferredSources())
            .thenReturn(new ResponseDto<>(ResponseStatus.SUCCESS, new SourcesResponseDto()));
        
        try {
            mockMvc.perform(get("/news-headlines/sources")
                    .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.data").isNotEmpty());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
