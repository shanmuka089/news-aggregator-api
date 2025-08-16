package com.example.new_aggregator.client.impl;

import com.example.new_aggregator.client.GNewsHeadLinesClient;
import com.example.new_aggregator.models.domain.NewsResponse;
import com.example.new_aggregator.models.dto.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public class GNewsHeadlinesClientImpl implements GNewsHeadLinesClient
{

    @Override
    public ResponseDto<NewsResponse> fetchHeadlinesByQuery(String query)
    {

        return null;
    }

    @Override
    public ResponseDto<NewsResponse> fetchHeadlinesByQueryAndLanguage(String query, String language)
    {

        return null;
    }

    @Override
    public ResponseDto<NewsResponse> fetchHeadlinesByQueryAndLanguageAndCountry(String query, String language, String country)
    {

        return null;
    }
}
