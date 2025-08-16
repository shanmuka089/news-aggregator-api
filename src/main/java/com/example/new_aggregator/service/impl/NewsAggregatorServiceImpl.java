package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.client.GNewsClient;
import com.example.new_aggregator.client.NewsApiClient;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.NewsAggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NewsAggregatorServiceImpl implements NewsAggregatorService
{
    
    @Autowired
    private PreferenceRepository preferenceRepository;
    
    @Autowired
    private GNewsClient gNewsClient;
    
    @Autowired
    private NewsApiClient newsApiClient;
    
    @Override
    public ResponseDto<NewsResponseDto> getNews()
    {

        return null;
    }

    @Override
    public ResponseDto<NewsResponseDto> getNewsByQuery(QueryDto query)
    {

        return null;
    }

}
