package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.errorHandling.ErrorResolver;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.NewsAggregatorService;
import com.example.new_aggregator.utils.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = NewsController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class NewsControllerTest
{
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private NewsAggregatorService newsAggregatorService;

    @MockitoBean
    private ErrorResolver errorResolver;
    
    @Test
    void fetchNews()
    {
        when(newsAggregatorService.retrieveNewsFromVariousSources())
            .thenReturn(new ResponseDto<>(ResponseStatus.SUCCESS, new NewsResponseDto()));

        try {
            mockMvc.perform(get("/news-aggregator/api/v1/news"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                    .andExpect(jsonPath("$.data").isNotEmpty());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testFetchNewsByQuery() {
        
        when(newsAggregatorService.retriveNewsByQueryBasedOnUserInput(any(QueryDto.class)))
            .thenReturn(new ResponseDto<>(ResponseStatus.SUCCESS, new NewsResponseDto()));
        
        try {
            mockMvc.perform(get("/news-aggregator/api/v1/news/sports")
                    .param("language", "en")
                    .param("country", "us")
                    .param("topic", "football")
                    .param("source", "bbc"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(ResponseStatus.SUCCESS.name()))
                    .andExpect(jsonPath("$.data").isNotEmpty());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    

}
