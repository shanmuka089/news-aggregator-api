package com.example.new_aggregator.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PreferenceDto
{
    private Long preferenceId;
    private Long userId;
    
    @NotBlank(message = "Category cannot be blank")
    private List<CategoryRequestDto> categories;
    
    @NotBlank(message = "Source cannot be blank")
    private List<SourceRequestDto> sources;
    
    @JsonProperty(defaultValue = "en")
    private String language;
    @JsonProperty(defaultValue = "in")
    private String region;
    
    @JsonProperty(defaultValue = "true")
    private boolean enabled;
    
    private Date createdAt;
    private Date updatedAt;
}
