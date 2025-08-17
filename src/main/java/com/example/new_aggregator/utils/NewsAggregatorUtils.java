package com.example.new_aggregator.utils;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.*;
import com.example.new_aggregator.models.entities.PreferenceEntity;

public class NewsAggregatorUtils
{
    
    public static UserDto fetchCurrentUser() {
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        return userDto;
    }
    
    public static void updateEntityFromDto(PreferenceDto preferenceDto, PreferenceEntity preferenceEntity) {
        
        preferenceEntity.setEnabled(preferenceDto.isEnabled());
        preferenceEntity.setRegion(preferenceDto.getRegion());
        preferenceEntity.setLanguage(preferenceDto.getLanguage());
        
        preferenceEntity.getCategories()
                .forEach(category -> {
                    CategoryRequestDto categoryDto = preferenceDto.getCategories()
                            .stream()
                            .filter(cat -> cat.getCategoryId().equals(category.getCategoryId()))
                            .findFirst()
                            .orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.FORBIDDEN));
                    
                    category.setName(categoryDto.getName());
                    category.setDescription(categoryDto.getDescription());
                    category.setEnabled(categoryDto.isEnabled());
                    
                    category.getTopics().forEach(topic -> {
                        TopicRequestDto topicRequestDto = categoryDto.getTopics()
                                .stream()
                                .filter(top -> top.getTopicId().equals(topic.getTopicId()))
                                .findFirst()
                                .orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.FORBIDDEN));
                        
                        topic.setName(topicRequestDto.getName());
                        topic.setDescription(topicRequestDto.getDescription());
                        topic.setEnabled(topicRequestDto.isEnabled());
                    });
                });

        preferenceEntity.getSources()
                .forEach(source -> {
                    SourceRequestDto sourceRequestDto = preferenceDto.getSources()
                            .stream()
                            .filter(sour -> sour.getSourceId().equals(source.getSourceId()))
                            .findFirst()
                            .orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.FORBIDDEN));

                    source.setName(sourceRequestDto.getName());
                    source.setDescription(sourceRequestDto.getDescription());
                    source.setEnabled(sourceRequestDto.isEnabled());
                });
    }
}
