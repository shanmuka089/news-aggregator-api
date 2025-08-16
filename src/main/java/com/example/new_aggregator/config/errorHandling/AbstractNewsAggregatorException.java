package com.example.new_aggregator.config.errorHandling;

public class AbstractNewsAggregatorException extends RuntimeException
{

    public ErrorCode errorCode;
    public String internalMessage;
    public String code;
    public String message;
    public int httpStatus;
    
    
    public AbstractNewsAggregatorException(ErrorCode errorCode, String message)
    {
        super(message);
        this.internalMessage = message;
        this.errorCode = errorCode;
    }

    public AbstractNewsAggregatorException(ErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public String getCode()
    {
        return this.code;
    }
    
    public String getInternalMessage()
    {
        return this.internalMessage;
    }
    
    @Override
    public String getMessage()
    {
        return this.message;
    }
    
}
