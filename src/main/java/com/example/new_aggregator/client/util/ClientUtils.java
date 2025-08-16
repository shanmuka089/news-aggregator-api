package com.example.new_aggregator.client.util;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.utils.ResponseStatus;
import org.springframework.http.ResponseEntity;

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
}
