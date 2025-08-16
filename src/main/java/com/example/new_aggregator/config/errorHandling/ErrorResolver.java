package com.example.new_aggregator.config.errorHandling;

import com.example.new_aggregator.models.domain.ErrorMetadata;
import com.example.new_aggregator.models.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ErrorResolver
{
    
    @Autowired
    private ErrorRegistry errorRegistry;
    
    public void resolve(AbstractNewsAggregatorException exception) {
        String code = exception.errorCode.getCode();
        Optional<ErrorMetadata> metaOpt = errorRegistry.getErrorMetadata(code);

        if (metaOpt.isPresent()) {
            ErrorMetadata meta = metaOpt.get();
            exception.code = meta.getErrorCode();    
            exception.httpStatus = meta.getHttpStatus();
            exception.message = meta.getMessage();
        } else {
            exception.code = exception.errorCode.getCode();
            exception.httpStatus = 500;
        }
    }


    public ErrorDto resolve(ErrorCode errorCode, String internalMessage) {
        String code = errorCode.getCode();
        Optional<ErrorMetadata> metaOpt = errorRegistry.getErrorMetadata(code);
        
        ErrorDto errorDto = new ErrorDto();
        
        if (metaOpt.isPresent()) {
            ErrorMetadata meta = metaOpt.get();
            errorDto.setErrorCode(meta.getErrorCode());
            errorDto.setMessage(meta.getMessage());
            errorDto.setErrorMessage(internalMessage);
        } else {
            errorDto.setErrorMessage(internalMessage);
            errorDto.setErrorCode(errorCode.getCode());
            errorDto.setMessage("An unexpected error occurred");
        }
        return errorDto;
    }
    
    public ErrorDto resolve(ErrorCode errorCode) {
        return resolve(errorCode, null);
    }
}
