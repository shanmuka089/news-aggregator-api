package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.annotations.ApiResponseDto;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.TopHeadLinesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${spring.application.base-path}")
public class NewsHeadlinesController
{
    @Autowired
    private TopHeadLinesService topHeadLinesService;
    
    @GetMapping("/news-headlines")
    public ResponseEntity<ResponseDto<NewsResponseDto>> getNewsHeadlines()
    { 
        ResponseDto<NewsResponseDto> responseDto = topHeadLinesService.getTopHeadLines();
        return ResponseEntity.ok(responseDto);
    }
}
