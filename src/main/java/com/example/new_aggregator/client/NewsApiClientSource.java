package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponse;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface NewsApiClientSource
{
    ResponseDto<SourcesResponse> fetchSources(QueryDto queryDto);
}
