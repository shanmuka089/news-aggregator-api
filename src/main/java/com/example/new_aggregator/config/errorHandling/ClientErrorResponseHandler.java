package com.example.new_aggregator.config.errorHandling;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;


@Component
public class ClientErrorResponseHandler extends DefaultResponseErrorHandler
{
    private final ObjectMapper mapper = new ObjectMapper();
    

    @Override
    public void handleError(ClientHttpResponse response) throws IOException
    {
        HttpStatusCode status = response.getStatusCode();
        switch(status.value()) {
            case 401:
                throw new NewsAggregatorException(NewsAggregatorErrorCode.INVALID_API_KEY);
            case 400:
                throw new NewsAggregatorException(NewsAggregatorErrorCode.BAD_REQUEST);
            case 403:
                throw new NewsAggregatorException(NewsAggregatorErrorCode.FORBIDDEN);
            case 429:
                throw new NewsAggregatorException(NewsAggregatorErrorCode.RATE_LIMIT_EXCEEDED);
            case 404:
                throw new NewsAggregatorException(NewsAggregatorErrorCode.NOT_FOUND);
            case 503:
                throw new NewsAggregatorException(NewsAggregatorErrorCode.SERVICE_UNAVAILABLE);
            case 500:
                throw new NewsAggregatorException(NewsAggregatorErrorCode.INTERNAL_SERVER_ERROR);
            default:
                if (status.value() != 200) {
                    throw new NewsAggregatorException(NewsAggregatorErrorCode.SERVER_ERROR);
            }
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
