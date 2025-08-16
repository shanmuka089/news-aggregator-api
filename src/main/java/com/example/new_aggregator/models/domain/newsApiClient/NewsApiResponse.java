package com.example.new_aggregator.models.domain.newsApiClient;

import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsArticle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NewsApiResponse
{
    private String status;
    private List<NewsApiArticle> newsApiArticles;
}
