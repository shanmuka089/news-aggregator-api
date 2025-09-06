package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.client.GNewsClient;
import com.example.new_aggregator.client.NewsApiClient;
import com.example.new_aggregator.mapper.NewsAggregateMapper;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiResponse;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.NewsAggregatorService;
import com.example.new_aggregator.utils.NewsAggregatorUtils;
import com.example.new_aggregator.utils.ResponseStatus;
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
    public ResponseDto<NewsResponseDto> retrieveNewsFromVariousSources()
    {
        Long userId = NewsAggregatorUtils.fetchCurrentUser().getUserId();
        
        PreferenceEntity preference = preferenceRepository.findByUserId(userId).orElse(null);

        QueryDto queryDto = NewsAggregatorUtils.buildQuery(preference);

        GNewsResponse newsResponse = gNewsClient.fetchNewsByPreference(queryDto).getData();
        NewsApiResponse newsApiResponse = newsApiClient.fetchNewsByPreference(queryDto).getData();

        NewsResponseDto newsResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsResponse);
        NewsResponseDto newsApiResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsApiResponse);

        NewsResponseDto newsResponses = NewsAggregatorUtils.mergeNewsResponses(newsResponseDto.getArticles(), newsApiResponseDto.getArticles());
        
        return new ResponseDto<>(ResponseStatus.SUCCESS, newsResponses);
    }

    @Override
    public ResponseDto<NewsResponseDto> retriveNewsByQueryBasedOnUserInput(QueryDto queryDto)
    {
        UserDto userDto = NewsAggregatorUtils.fetchCurrentUser();
        PreferenceEntity preferenceEntity = preferenceRepository.findByUserId(userDto.getUserId()).orElse(null);

        NewsAggregatorUtils.validateAndBuildQuery(queryDto, preferenceEntity);
        
        GNewsResponse newsResponse = gNewsClient.fetchNewsByPreference(queryDto).getData();
        NewsApiResponse newsApiResponse = newsApiClient.fetchNewsByPreference(queryDto).getData();

        NewsResponseDto newsResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsResponse);
        NewsResponseDto newsApiResponseDto = NewsAggregateMapper.INSTANCE.toNewsResponseDto(newsApiResponse);

        NewsResponseDto newsResponses = NewsAggregatorUtils.mergeNewsResponses(newsResponseDto.getArticles(), newsApiResponseDto.getArticles());

        return new ResponseDto<>(ResponseStatus.SUCCESS, newsResponses);
    }

}
