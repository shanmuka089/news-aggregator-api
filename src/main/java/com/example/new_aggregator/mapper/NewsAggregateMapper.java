package com.example.new_aggregator.mapper;


import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsArticle;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsSource;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiArticle;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiResponse;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiSource;
import com.example.new_aggregator.models.dto.ArticleDto;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.SourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewsAggregateMapper
{

    NewsAggregateMapper INSTANCE = Mappers.getMapper(NewsAggregateMapper.class);
    
    NewsResponseDto toNewsResponseDto(GNewsResponse gNewsResponse);
    ArticleDto toArticleDto(GNewsArticle gNewsArticle);
    SourceDto toSourceDto(GNewsSource gNewsSource);
    
    NewsResponseDto toNewsResponseDto(NewsApiResponse newsApiResponse);
    ArticleDto toArticleDto(NewsApiArticle newsApiArticle);
    SourceDto toSourceDto(NewsApiSource newsApiSource);

}
