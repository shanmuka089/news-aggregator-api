package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface NewsAggregatorService
{
    /**
     * Retrieves news from various sources.
     *
     * @return ResponseDto containing NewsResponseDto with the news data.
     */
    ResponseDto<NewsResponseDto> retrieveNewsFromVariousSources();
    
    /**
     * Retrieves news based on user preferences.
     *
     * @return ResponseDto containing NewsResponseDto with the news data.
     */
    ResponseDto<NewsResponseDto> retriveNewsByQueryBasedOnUserInput(QueryDto query);
}
