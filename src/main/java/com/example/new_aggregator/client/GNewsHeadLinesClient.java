package com.example.new_aggregator.client;

import com.example.new_aggregator.models.domain.NewsResponse;
import com.example.new_aggregator.models.dto.ResponseDto;

public interface GNewsHeadLinesClient
{

    ResponseDto<NewsResponse> fetchHeadlinesByQuery(String query);
    ResponseDto<NewsResponse> fetchHeadlinesByQueryAndLanguage(String query, String language);
    ResponseDto<NewsResponse> fetchHeadlinesByQueryAndLanguageAndCountry(String query, String language, String country);

}
