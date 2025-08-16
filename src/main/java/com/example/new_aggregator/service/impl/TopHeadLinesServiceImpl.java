package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.client.GNewsHeadLinesClient;
import com.example.new_aggregator.client.NewsApiHeadLinesClient;
import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.TopHeadLinesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TopHeadLinesServiceImpl implements TopHeadLinesService
{
    @Autowired
    private PreferenceRepository preferenceRepository;
    
    @Autowired
    private GNewsHeadLinesClient gNewsHeadLinesClient;
    
    @Autowired
    private NewsApiHeadLinesClient newsApiHeadLinesClient;

    @Override
    public ResponseDto<NewsResponseDto> getTopHeadLines()
    {
        Long userId = 1L;
        PreferenceEntity preference = preferenceRepository.findByUserId(userId).orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.USER_NOT_FOUND));
        
        QueryDto.builder()
                .language(preference.getLanguage())
                .country(preference.getRegion())
                .country(preference.getRegion())
                .query(null)
                .category(null)
                .source(null)
                .build();
        return null;
    }

    @Override
    public ResponseDto<NewsResponseDto> getTopHeadlines(QueryDto queryDto)
    {

        return null;
    }

}
