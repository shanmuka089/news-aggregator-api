package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface GNewsHeadLinesClient
{

    /**
     * Fetches headlines from GNews API based on the provided query.
     *
     * @param category QueryDto containing the search parameters for headlines.
     * @return ResponseDto containing GNewsResponse with the headlines data.
     */
    ResponseDto<GNewsResponse> fetchHeadlinesByQuery(QueryDto category);
    
}
