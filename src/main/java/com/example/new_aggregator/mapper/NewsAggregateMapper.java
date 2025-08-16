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

@Mapper(componentModel = "spring")
public interface NewsAggregateMapper
{
    @Mapping(target = "articles", source = "gNewsArticles")
    NewsResponseDto toNewsResponseDto(GNewsResponse gNewsResponse);
    @Mapping(target = "sourceDto", source = "gNewsArticle.gNewsSource")
    ArticleDto toArticleDto(GNewsArticle gNewsArticle);
    SourceDto toSourceDto(GNewsSource gNewsSource);
    
    @Mapping(target = "articles", source = "newsApiArticles")
    NewsResponseDto toNewsResponseDto(NewsApiResponse newsApiResponse);
    @Mapping(target = "sourceDto", source = "newsApiArticle.newsApiSource")
    ArticleDto toArticleDto(NewsApiArticle newsApiArticle);
    SourceDto toSourceDto(NewsApiSource newsApiSource);

}
