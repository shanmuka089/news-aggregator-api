package com.example.new_aggregator.models.dto;

import com.example.new_aggregator.utils.ResponseStatus;

public class ResponseDto<T>
{
    private ResponseStatus status;
    private T data;

    public ResponseDto(ResponseStatus status, T data) {
        this.status = status;
        this.data = data;
    }
    
    public ResponseStatus getStatus() {
        return status;
    }
    public T getData() {
        return data;
    }
    
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(ResponseStatus.SUCCESS, data);
    }
}
