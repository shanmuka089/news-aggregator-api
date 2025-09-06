package com.example.new_aggregator.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticleDto
{

    private String id;
    private String author;
    private String title;
    private String description;
    private String url;
    private String content;
    private String image;
    private String publishedAt;
    private SourceDto source;

}
