package com.example.new_aggregator.service;

import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface TopHeadLinesService
{
    /**
     * Fetches headlines from all available sources.
     *
     * @return ResponseDto containing NewsResponseDto with the headlines data.
     */
    ResponseDto<NewsResponseDto> fetchHeadLinesFromAllSources();
    
    /**
     * Fetches top headlines based on the provided query.
     *
     * @param queryDto QueryDto containing the search parameters.
     * @return ResponseDto containing NewsResponseDto with the top headlines data.
     */
    ResponseDto<NewsResponseDto> fetchTopHeadlines(QueryDto queryDto);
    
    /**
     * Fetches headlines from preferred sources based on user preferences.
     *
     * @return ResponseDto containing SourcesResponseDto with the preferred sources data.
     */
    ResponseDto<SourcesResponseDto> fetchHeadlinesFromPreferredSources();
}
