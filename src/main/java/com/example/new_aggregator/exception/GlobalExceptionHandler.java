package com.example.new_aggregator.exception;

import com.example.new_aggregator.config.errorHandling.ErrorResolver;
import com.example.new_aggregator.models.dto.ErrorDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.utils.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler
{
    
    @Autowired
    private ErrorResolver errorResolver;
    
    @ExceptionHandler(NewsAggregatorException.class)
    public ResponseEntity<ResponseDto<ErrorDto>> handleNewsAggregatorException(NewsAggregatorException exception) {
        errorResolver.resolve(exception);
        
        ErrorDto errorDto = new ErrorDto(
                exception.getCode(),
                exception.getMessage(),
                exception.getInternalMessage()
        );

        log.error("NewsAggregatorException occurred: {}", exception.getMessage());

        ResponseDto<ErrorDto> response = new ResponseDto<>(
                ResponseStatus.SUCCESS,
                errorDto
        );
        
        return ResponseEntity.status(exception.httpStatus).body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<ErrorDto>> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = "Validation failed: " + exception.getBindingResult().getFieldError().getDefaultMessage();
        
        ErrorDto errorDto = errorResolver.resolve(NewsAggregatorErrorCode.BAD_REQUEST, errorMessage);
        
        log.error("MethodArgumentNotValidException occurred: {}", errorDto.getMessage());
        
        ResponseDto<ErrorDto> response = new ResponseDto<>(
                ResponseStatus.ERROR,
                errorDto
        );
        
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<ErrorDto>> handleGenericException(Exception exception) {
        
        ErrorDto errorDto = errorResolver.resolve(NewsAggregatorErrorCode.INTERNAL_SERVER_ERROR);

        log.error("An unexpected error occurred: {}", errorDto.getMessage());

        ResponseDto<ErrorDto> response = new ResponseDto<>(
                ResponseStatus.ERROR,
                errorDto
        );
        
        return ResponseEntity.status(500).body(response);
    }
}
