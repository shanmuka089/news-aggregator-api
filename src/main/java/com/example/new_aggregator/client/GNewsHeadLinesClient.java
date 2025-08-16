package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface GNewsHeadLinesClient
{

    ResponseDto<GNewsResponse> fetchHeadlinesByQuery(QueryDto category);
    
}
