package com.example.new_aggregator.models.domain.gNewsApiClient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GNewsArticle
{
    private String id;
    private String title;
    private String author;
    private String description;
    private String content;
    private String url;
    private String image;
    private String publishedAt;
    private GNewsSource gNewsSource;
}
