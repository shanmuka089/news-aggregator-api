package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface GNewsClient
{
    
    /**
     * Fetches news from GNews API based on the provided query.
     *
     * @param query QueryDto containing the search parameters.
     * @return ResponseDto containing GNewsResponse with the news data.
     */
    ResponseDto<GNewsResponse> fetchNewsByPreference(QueryDto query);
}
