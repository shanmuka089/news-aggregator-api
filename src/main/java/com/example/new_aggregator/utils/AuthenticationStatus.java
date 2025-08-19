package com.example.new_aggregator.utils;

public enum AuthenticationStatus
{
    INITIATE_SESSION("SESSION_INIT"),
    AUTHENTICATE("AUTHENTICATE"),
    SEND_OTP("SEND_OTP"),
    VALIDATE_OTP("VALIDATE_OTP"),
    PENDING("PENDING"),
    COMPLETED("COMPLETED");
    String val;
    AuthenticationStatus(String val){
        this.val = val;
    }
}
