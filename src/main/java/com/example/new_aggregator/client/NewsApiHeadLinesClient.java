package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.newsApiClient.NewsApiResponse;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface NewsApiHeadLinesClient
{
    /**
     * Fetches headlines from News API based on the provided query.
     *
     * @param category QueryDto containing the search parameters for headlines.
     * @return ResponseDto containing NewsApiResponse with the headlines data.
     */
    ResponseDto<NewsApiResponse> fetchHeadlinesByQuery(QueryDto category);
}
