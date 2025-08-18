package com.example.new_aggregator.client.util;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.utils.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class ClientUtils
{
    
    public static <T> ResponseDto<T> getResponseData(ResponseEntity<T> response)
    {
        if(response.getStatusCode().is2xxSuccessful() && response.hasBody())
        {
            ResponseDto<T> dto = new ResponseDto<>(
                            ResponseStatus.SUCCESS,
                            response.getBody()
                        );
            
            return dto;
        }
        else
        {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.INTERNAL_SERVER_ERROR, 
                    "Failed to retrieve data from the response. Status code: " + response.getStatusCode());
        }
    }
    
    public static String buildUrl(String baseUrl, String path, QueryDto queryDto, boolean isCountrySupported)
    {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path(path)
                .queryParam("lang", queryDto.getLanguage())
                .queryParam("q", queryDto.getQuery());
                
                if(isCountrySupported && queryDto.getCountry() != null && !queryDto.getCountry().isEmpty()){
                    uriBuilder.queryParam("country", queryDto.getCountry());
                }
                
                return uriBuilder.build(false)
                .toUriString();
    }
}
