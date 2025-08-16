package com.example.new_aggregator.models.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ErrorMetadata
{
    private String errorCode;
    private int httpStatus;
    private String message;
}
