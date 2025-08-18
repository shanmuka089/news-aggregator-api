package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.client.GNewsHeadLinesClient;
import com.example.new_aggregator.client.NewsApiClientSource;
import com.example.new_aggregator.client.NewsApiHeadLinesClient;
import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.mapper.NewsAggregateMapper;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiResponse;
import com.example.new_aggregator.models.domain.newsApiClient.Source;
import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.models.entities.SourceEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.TopHeadLinesService;
import com.example.new_aggregator.utils.Constants;
import com.example.new_aggregator.utils.NewsAggregatorUtils;
import com.example.new_aggregator.utils.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TopHeadLinesServiceImpl implements TopHeadLinesService
{
    @Autowired
    private PreferenceRepository preferenceRepository;
    
    @Autowired
    private GNewsHeadLinesClient gNewsHeadLinesClient;
    
    @Autowired
    private NewsApiClientSource newsApiClientSource;
    
    @Autowired
    private NewsApiHeadLinesClient newsApiHeadLinesClient;

    @Override
    public ResponseDto<NewsResponseDto> fetchHeadLinesFromAllSources()
    {
        Long userId = NewsAggregatorUtils.fetchCurrentUser().getUserId();

        PreferenceEntity preference = preferenceRepository.findByUserId(userId).orElseGet(null);

        QueryDto queryDto = NewsAggregatorUtils.buildQuery(preference);

        GNewsResponse newsResponse = gNewsHeadLinesClient.fetchHeadlinesByQuery(queryDto).getData();
        NewsApiResponse newsApiResponse = newsApiHeadLinesClient.fetchHeadlinesByQuery(queryDto).getData();

        NewsResponseDto newsResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsResponse);
        NewsResponseDto newsApiResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsApiResponse);

        NewsResponseDto newsResponses = NewsAggregatorUtils.mergeNewsResponses(newsResponseDto.getArticles(), newsApiResponseDto.getArticles());

        return new ResponseDto<>(ResponseStatus.SUCCESS, newsResponses);
    }

    @Override
    public ResponseDto<NewsResponseDto> fetchTopHeadlines(QueryDto queryDto)
    {
        GNewsResponse newsResponse = gNewsHeadLinesClient.fetchHeadlinesByQuery(queryDto).getData();
        NewsApiResponse newsApiResponse = newsApiHeadLinesClient.fetchHeadlinesByQuery(queryDto).getData();

        NewsResponseDto newsResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsResponse);
        NewsResponseDto newsApiResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsApiResponse);

        NewsResponseDto newsResponses = NewsAggregatorUtils.mergeNewsResponses(newsResponseDto.getArticles(), newsApiResponseDto.getArticles());

        return new ResponseDto<>(ResponseStatus.SUCCESS, newsResponses);
    }

    @Override
    public ResponseDto<SourcesResponseDto> fetchHeadlinesFromPreferredSources()
    {
        
        Long userId = NewsAggregatorUtils.fetchCurrentUser().getUserId();
        
        PreferenceEntity preference = preferenceRepository.findByUserId(userId).orElse(null);
        
        QueryDto queryDto = NewsAggregatorUtils.buildQuery(preference);
        
        ResponseDto<SourcesResponseDto> sourceResponse = newsApiClientSource.fetchNewsBySources(queryDto);

        List<String> preferredSources = preference.getSources().stream().map(SourceEntity ::getName).map(String::toLowerCase).collect(Collectors.toList());
        
        // if the user has no preferred sources, return all sources
        if(!preferredSources.isEmpty()) {
            List<Source> headlinesFromPreferredSources = sourceResponse.getData().getSources().stream()
                    .filter(source -> preferredSources.contains(source.getName().toLowerCase()))
                    .collect(Collectors.toList());
            sourceResponse = new ResponseDto<>(ResponseStatus.SUCCESS, new SourcesResponseDto(headlinesFromPreferredSources));
        }
        
        return sourceResponse;
    }

}
