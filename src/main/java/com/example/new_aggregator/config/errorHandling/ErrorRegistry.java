package com.example.new_aggregator.config.errorHandling;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.domain.ErrorMetadata;
import com.example.new_aggregator.utils.Constants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
public class ErrorRegistry
{

    @Value("${error.code.file.name}")
    private String errorCodeFileName;
    
    private final Properties props = new Properties();
    private final Map<String, ErrorMetadata> registry = new HashMap<>();
    
    @PostConstruct
    public void init() {
        
        var resource = new ClassPathResource(errorCodeFileName + ".properties");
        
        try {
            props.load(resource.getInputStream());

            var prefixes = props.stringPropertyNames().stream()
                    .map(s -> s.replaceAll("\\.(code|http|message)$", ""))
                    .collect(Collectors.toSet());
            
            for(String prefix : prefixes) {
                String errorCode = props.getProperty(prefix + Constants.CODE_SUFFIX);
                int httpStatus = Integer.parseInt(props.getProperty(prefix + Constants.HTTP_SUFFIX));
                String message = props.getProperty(prefix + Constants.MESSAGE_SUFFIX);

                if(errorCode != null && !errorCode.isEmpty() && 
                        httpStatus > 0 && message != null && !message.isEmpty()) {
                    ErrorMetadata metadata = new ErrorMetadata();
                    metadata.setErrorCode(errorCode);
                    metadata.setHttpStatus(httpStatus);
                    metadata.setMessage(message);
                    registry.put(prefix + Constants.CODE_SUFFIX, metadata);
                }
            }
        } catch(Exception e) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.SERVER_ERROR, 
                    "Error loading error codes from file: " + errorCodeFileName);
        }
        
    }
    
    public Optional<ErrorMetadata> getErrorMetadata(String errorCode) {
        return Optional.of(registry.get(errorCode));
    }


}
