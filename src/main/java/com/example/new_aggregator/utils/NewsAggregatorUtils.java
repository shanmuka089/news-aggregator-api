package com.example.new_aggregator.utils;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.*;
import com.example.new_aggregator.models.entities.CategoryEntity;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.models.entities.SourceEntity;
import com.example.new_aggregator.models.entities.TopicEntity;
import org.springframework.stereotype.Component;

@Component
public class NewsAggregatorUtils
{
    
    public static UserDto fetchCurrentUser() {
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        return userDto;
    }
    
    public void updateEntityFromDto(PreferenceDto preferenceDto, PreferenceEntity preferenceEntity) {
        
        preferenceEntity.setEnabled(preferenceDto.isEnabled());
        preferenceEntity.setRegion(preferenceDto.getRegion());
        preferenceEntity.setLanguage(preferenceDto.getLanguage());

        updateCategoriesToPreference(preferenceDto, preferenceEntity);

        updateSourcesToPreference(preferenceDto, preferenceEntity);
    }

    private void updateCategoriesToPreference(PreferenceDto preferenceDto, PreferenceEntity preferenceEntity)
    {

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

                    updateTopicsToCategory(category, categoryDto);
                });
    }

    private void updateTopicsToCategory(CategoryEntity category, CategoryRequestDto categoryDto)
    {

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
    }

    private void updateSourcesToPreference(PreferenceDto preferenceDto, PreferenceEntity preferenceEntity)
    {

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

    public void addCategoriesAndSourcesToExistingPreference(PreferenceDto preferenceDto, PreferenceEntity preferenceEntity)
    {
        preferenceDto.getCategories().forEach(categoryDto -> {
            CategoryEntity existingCategory = preferenceEntity.getCategories()
                    .stream()
                    .filter(cat -> cat.getName().equals(categoryDto.getName()))
                    .findFirst()
                    .orElse(null);
            if(existingCategory == null) {
                addCategoryToExistingPreference(preferenceEntity, categoryDto);
            } else {
                addTopicsToExistingCategory(categoryDto, existingCategory);
            }
        });
        addSourcesToExistingPreference(preferenceDto, preferenceEntity);
    }
    

    private void addCategoryToExistingPreference(PreferenceEntity preferenceEntity, CategoryRequestDto categoryDto)
    {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDto.getName());
        categoryEntity.setDescription(categoryDto.getDescription());
        categoryEntity.setEnabled(categoryDto.isEnabled());
        categoryEntity.setPreferences(preferenceEntity);

        categoryDto.getTopics().forEach(topic -> {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setName(topic.getName());
            topicEntity.setDescription(topic.getDescription());
            topicEntity.setEnabled(topic.isEnabled());
            topicEntity.setCategories(categoryEntity);
            categoryEntity.getTopics().add(topicEntity);
        });
        preferenceEntity.getCategories().add(categoryEntity);
    }

    private void addTopicsToExistingCategory(CategoryRequestDto categoryDto, CategoryEntity existingCategory)
    {

        categoryDto.getTopics().forEach(topicDto -> {
            
            TopicEntity existingTopic = existingCategory.getTopics()
                    .stream()
                    .filter(top -> top.getName().equals(topicDto.getName()))
                    .findFirst()
                    .orElse(null);
            
            if (existingTopic == null) {
                TopicEntity topicEntity = new TopicEntity();
                topicEntity.setName(topicDto.getName());
                topicEntity.setDescription(topicDto.getDescription());
                topicEntity.setEnabled(topicDto.isEnabled());
                topicEntity.setCategories(existingCategory);
                existingCategory.getTopics().add(topicEntity);
            }
        });
    }

    private void addSourcesToExistingPreference(PreferenceDto preferenceDto, PreferenceEntity preferenceEntity)
    {

        preferenceDto.getSources().forEach(sourceDto -> {
            SourceEntity existingSource = preferenceEntity.getSources()
                    .stream()
                    .filter(sour -> sour.getName().equals(sourceDto.getName()))
                    .findFirst()
                    .orElse(null);
            
            if (existingSource == null) {
                SourceEntity sourceEntity = new SourceEntity();
                sourceEntity.setName(sourceDto.getName());
                sourceEntity.setDescription(sourceDto.getDescription());
                sourceEntity.setEnabled(sourceDto.isEnabled());
                sourceEntity.getPreferences().add(preferenceEntity);
                preferenceEntity.getSources().add(sourceEntity);
            }
        });
    }

}
