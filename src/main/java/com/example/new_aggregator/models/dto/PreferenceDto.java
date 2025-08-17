package com.example.new_aggregator.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PreferenceDto
{
    private Long preferenceId;
    private Long userId;
    private String category;
    private String source;
    private String language;
    private String region;
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;
}
