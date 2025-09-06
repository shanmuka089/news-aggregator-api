package com.example.new_aggregator.controller;

import com.example.new_aggregator.models.domain.newsApiClient.SourcesResponseDto;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.TopHeadLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${spring.application.base-path}")
public class NewsHeadlinesController
{
    @Autowired
    private TopHeadLinesService topHeadLinesService;
    
    /**
     * Fetches the latest news headlines from all available sources.
     * This endpoint aggregates headlines from multiple sources and returns them in a unified format.
     * @return ResponseEntity containing ResponseDto with NewsResponseDto.
     */
    @GetMapping("/news-headlines")
    public ResponseEntity<ResponseDto<NewsResponseDto>> getNewsHeadlines()
    { 
        ResponseDto<NewsResponseDto> responseDto = topHeadLinesService.fetchHeadLinesFromAllSources();
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Fetches news headlines based on user-defined queries.
     * This endpoint allows users to specify parameters such as category, language, country, topic, and source to filter news headlines.
     * @param category The category of news to fetch (e.g., sports, politics).
     * @param language The language of the news articles (optional).
     * @param country The country of the news articles (optional).
     * @param topic The specific topic to search for in the news articles (optional).
     * @param source The source of the news articles (optional).
     * @return ResponseEntity containing ResponseDto with NewsResponseDto.
     */
    @GetMapping("/news-headlines/{category}")
    public ResponseEntity<ResponseDto<NewsResponseDto>> fetchHeadlinesByQuery(@PathVariable("category") String category,
                                                                              @RequestParam(value = "language", required = false) String language,
                                                                              @RequestParam(value = "country", required = false) String country,
                                                                              @RequestParam(value = "topic", required = false) String topic,
                                                                              @RequestParam(value = "source", required = false) String source) {

        QueryDto queryDto = QueryDto.builder()
                .category(category)
                .language(language)
                .country(country)
                .query(topic)
                .source(source)
                .build();

        ResponseDto<NewsResponseDto> responseDto = topHeadLinesService.fetchTopHeadlines(queryDto);
        return ResponseEntity.ok(responseDto);

    }
    
    @GetMapping("/news-headlines/sources")
    public ResponseEntity<ResponseDto<SourcesResponseDto>> fetchHeadLinesBySources() {
        
        ResponseDto<SourcesResponseDto> responseDto = topHeadLinesService.fetchHeadlinesFromPreferredSources();
        
        return ResponseEntity.ok(responseDto);
    }
}
