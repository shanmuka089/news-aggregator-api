package com.example.new_aggregator.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SourceRequestDto
{

    private Long sourceId;
    @NotBlank(message = "Source Name cannot be blank")
    private String name;
    @NotBlank(message = "Source Description cannot be blank")
    private String description;
    @JsonProperty(defaultValue = "true")
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;
}
