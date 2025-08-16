package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.NewsResponse;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface GNewsClient
{
    
    ResponseDto<NewsResponse> fetchNewsByQuery(String query);
    ResponseDto<NewsResponse> fetchNewsByQueryAndLanguage(String query, String language);
    ResponseDto<NewsResponse> fetchNewsByQueryAndLanguageAndCountry(String query, String language, String country);
}
