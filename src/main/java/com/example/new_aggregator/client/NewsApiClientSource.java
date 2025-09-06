package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface NewsApiClientSource
{
    /**
     * Fetches news from various sources based on the provided query.
     *
     * @param queryDto QueryDto containing the search parameters.
     * @return ResponseDto containing SourcesResponseDto with the news data.
     */
    ResponseDto<SourcesResponseDto> fetchNewsBySources(QueryDto queryDto);

}
