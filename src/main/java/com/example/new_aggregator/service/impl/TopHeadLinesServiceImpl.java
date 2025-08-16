package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.client.GNewsHeadLinesClient;
import com.example.new_aggregator.client.NewsApiHeadLinesClient;
import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.mapper.NewsAggregateMapper;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.TopHeadLinesService;
import com.example.new_aggregator.utils.Constants;
import com.example.new_aggregator.utils.ResponseStatus;
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
        /*Long userId = 1L;
        PreferenceEntity preference = preferenceRepository.findByUserId(userId).orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.USER_NOT_FOUND));
        */
        QueryDto queryDto = QueryDto.builder()
                .language("en")
                .country("in")
                .query("Sports OR Cricket")
                .build();
        
        ResponseDto<GNewsResponse> responseDto = gNewsHeadLinesClient.fetchHeadlinesByQuery(queryDto);

        NewsResponseDto newsResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(responseDto.getData());
        
        return new ResponseDto<>(ResponseStatus.SUCCESS, newsResponseDto);
    }

    @Override
    public ResponseDto<NewsResponseDto> getTopHeadlines(QueryDto queryDto)
    {

        return null;
    }

}
