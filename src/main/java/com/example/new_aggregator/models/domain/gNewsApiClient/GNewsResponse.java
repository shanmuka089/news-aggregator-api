package com.example.new_aggregator.models.domain.gNewsApiClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GNewsResponse
{
    private List<GNewsArticle> gNewsArticles;
    
}
