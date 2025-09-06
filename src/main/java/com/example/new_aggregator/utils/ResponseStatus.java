package com.example.new_aggregator.utils;

public enum ResponseStatus
{
    
    SUCCESS("success"),
    PARTIAL("Partial"),
    ERROR("Error");

    private final String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
