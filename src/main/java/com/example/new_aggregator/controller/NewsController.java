package com.example.new_aggregator.controller;


import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.NewsAggregatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
        
        log.info("Fetching latest news articles from various sources");
        ResponseDto<NewsResponseDto> responseDto = newsAggregatorService.retrieveNewsFromVariousSources();
        return ResponseEntity.ok(responseDto);
        
    }
}
