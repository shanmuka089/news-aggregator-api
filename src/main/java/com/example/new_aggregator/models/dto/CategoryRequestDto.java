package com.example.new_aggregator.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryRequestDto
{
    private Long categoryId;
    @NotBlank(message = "Category Name cannot be blank")
    private String name;
    @NotBlank(message = "Category Description cannot be blank")
    private String description;
    @JsonProperty(defaultValue = "true")
    private boolean enabled;
    private List<TopicRequestDto> topics;
    private Date createdAt;
    private Date updatedAt;
}
