package com.example.new_aggregator.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicRequestDto
{

    private Long topicId;
    @NotBlank(message = "Topic Name cannot be blank")
    private String name;
    @NotBlank(message = "Topic Description cannot be blank")
    private String description;
    @JsonProperty(defaultValue = "true")
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;
}
