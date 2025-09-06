package com.example.new_aggregator.models.domain.newsApiClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NewsApiSource
{
    private String id;
    private String name;
    private String url;
}
