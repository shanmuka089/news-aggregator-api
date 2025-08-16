package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface NewsAggregatorService
{
    ResponseDto<NewsResponseDto> getNews();
    ResponseDto<NewsResponseDto> getNewsByQuery(QueryDto query);
}
