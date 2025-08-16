package com.example.new_aggregator.exception;

import com.example.new_aggregator.config.errorHandling.AbstractNewsAggregatorException;
import com.example.new_aggregator.config.errorHandling.ErrorCode;

public class NewsAggregatorException extends AbstractNewsAggregatorException
{

    public NewsAggregatorException(ErrorCode errorCode, String message)
    {
        super(errorCode,message);
    }

    public NewsAggregatorException(ErrorCode errorCode)
    {
        super(errorCode);
    }
}
