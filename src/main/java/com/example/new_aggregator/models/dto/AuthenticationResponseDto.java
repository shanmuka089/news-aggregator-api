package com.example.new_aggregator.models.dto;

import com.example.new_aggregator.utils.AuthenticationStatus;
import com.example.new_aggregator.utils.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponseDto<T>
{
    private ResponseStatus status;
    private AuthenticationStatus currentStep;
    private AuthenticationStatus nextStep;
    private T data;
}
