package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface TopHeadLinesService
{
    ResponseDto<NewsResponseDto> getTopHeadLines();
    ResponseDto<NewsResponseDto> getTopHeadlines(QueryDto queryDto);
}
