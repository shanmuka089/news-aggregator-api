package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.newsApiClient.NewsApiResponse;
import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface NewsApiClient
{
    /**
     * Fetches news from various sources.
     *
     * @return ResponseDto containing NewsApiResponse with the news data.
     */
    ResponseDto<NewsApiResponse> fetchNewsByPreference(QueryDto queryDto);
}
