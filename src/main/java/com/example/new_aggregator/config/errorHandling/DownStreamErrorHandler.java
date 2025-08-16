package com.example.new_aggregator.config.errorHandling;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;


@Component
public class DownStreamErrorHandler extends DefaultResponseErrorHandler
{
    private final ObjectMapper mapper = new ObjectMapper();
    

    @Override
    public void handleError(ClientHttpResponse response) throws IOException
    {
        HttpStatusCode status = response.getStatusCode();
        
        if(status.value() == HttpStatus.UNAUTHORIZED.value()) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.INVALID_API_KEY);
        } else if(status.value() == HttpStatus.BAD_REQUEST.value()) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.BAD_REQUEST);
        }else if(status.value() != HttpStatus.OK.value()) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.SERVER_ERROR);
        }
        
        String body = "";
        try {
            body = new String(response.getBody().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
            if(body == null || body.isEmpty()) {
                throw new NewsAggregatorException(NewsAggregatorErrorCode.SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.SERVER_ERROR);
        }
    }
    
}
