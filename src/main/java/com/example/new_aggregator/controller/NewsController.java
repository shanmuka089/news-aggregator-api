package com.example.new_aggregator.controller;


import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.NewsAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${spring.application.base-path}")
public class NewsController
{

    @Autowired
    private NewsAggregatorService newsAggregatorService;

    /**
     * Fetches the latest news articles from various sources.
     * This endpoint aggregates news from multiple sources and returns them in a unified format.
     * @return
     */
    @GetMapping("/news")
    public ResponseEntity<ResponseDto<NewsResponseDto>> fetchNews() {
        
        ResponseDto<NewsResponseDto> responseDto = newsAggregatorService.retrieveNewsFromVariousSources();
        return ResponseEntity.ok(responseDto);
        
    }
    
    
    /**
     * Fetches news articles based on user-defined queries.
     * This endpoint allows users to specify parameters such as category, language, country, topic, and source to filter news articles.
     * @param category The category of news to fetch (e.g., sports, politics).
     * @param language The language of the news articles (optional).
     * @param country The country of the news articles (optional).
     * @param topic The specific topic to search for in the news articles (optional).
     * @param source The source of the news articles (optional).
     * @return
     */
    @GetMapping("/news/{category}")
    public ResponseEntity<ResponseDto<NewsResponseDto>> fetchNewsByQuery(@PathVariable("category") String category,
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
        
        ResponseDto<NewsResponseDto> responseDto = newsAggregatorService.retriveNewsByQueryBasedOnUserInput(queryDto);
        return ResponseEntity.ok(responseDto);
        
    }
}
