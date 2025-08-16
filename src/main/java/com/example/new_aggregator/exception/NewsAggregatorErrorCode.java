package com.example.new_aggregator.exception;

import com.example.new_aggregator.config.errorHandling.ErrorCode;

public enum NewsAggregatorErrorCode implements ErrorCode
{
    NOT_FOUND("news-api-client-not-found.code"),
    INTERNAL_SERVER_ERROR("news-api-client-internal-server-error.code"),
    SERVER_ERROR("news-api-client-server-error.code"),
    UNAUTHORIZED("news-api-client-unauthorized.code"),
    USER_NOT_FOUND("news-api-client-user-not-found.code"),
    FORBIDDEN("news-api-client-forbidden.code"),
    SERVICE_UNAVAILABLE("news-api-client-service-unavailable.code"),
    RATE_LIMIT_EXCEEDED("news-api-client-rate-limit-exceeded.code"),
    INVALID_API_KEY("news-api-client-invalid-api-key.code"),
    BAD_REQUEST("news-api-client-bad-request.code");
    
    String code;
    NewsAggregatorErrorCode(String code) {
        this.code = code;
    }
    
    @Override
    public String getCode() {
        return this.code;
    }
}
