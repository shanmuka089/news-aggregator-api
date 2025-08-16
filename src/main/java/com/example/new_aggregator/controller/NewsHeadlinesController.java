package com.example.new_aggregator.controller;

import com.example.new_aggregator.client.NewsApiClientSource;
import com.example.new_aggregator.config.ApiDocsConfig;
import com.example.new_aggregator.models.dto.ErrorDto;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.TopHeadLinesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Headlines")
@RequestMapping("${spring.application.base-path}")
public class NewsHeadlinesController
{
    @Autowired
    private TopHeadLinesService topHeadLinesService;

    @ApiDocsConfig
    @GetMapping("/news-headlines")
    public ResponseEntity<ResponseDto<NewsResponseDto>> getNewsHeadlines()
    { 
        ResponseDto<NewsResponseDto> responseDto = topHeadLinesService.getTopHeadLines();
        return ResponseEntity.ok(responseDto);
    }
}
