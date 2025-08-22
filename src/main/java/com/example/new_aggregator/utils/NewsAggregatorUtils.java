package com.example.new_aggregator.utils;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.*;
import com.example.new_aggregator.models.entities.CategoryEntity;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.models.entities.SourceEntity;
import com.example.new_aggregator.models.entities.TopicEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NewsAggregatorUtils
{
    
    public static UserDto fetchCurrentUser() {
        UserDto userDto = new UserDto();
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof UserDto) {
                UserDto currentUser = (UserDto) details;
                userDto.setUserId(currentUser.getUserId());
                userDto.setUsername(currentUser.getUsername());
                userDto.setEmail(currentUser.getEmail());
                userDto.setRoles(currentUser.getRoles());
                userDto.setExpirationTime(currentUser.getExpirationTime());
            }
        return userDto;
    }
    
    public static String buildUserSelectedCategoriesString(List<CategoryEntity> categories) {
        StringBuilder categoriesString = new StringBuilder();
        categories.forEach(category -> {
            if (category.isEnabled()) {
                if (categoriesString.length() > 0) {
                    categoriesString.append(" OR ");
                }
                categoriesString.append(category.getName());
            }
        });
        return categoriesString.toString();
    }
    
    public static String buildUserSelectedTopicsString(List<CategoryEntity> categoryEntities) {
        StringBuilder topicsString = new StringBuilder();
        categoryEntities.forEach(category -> {
            category.getTopics().forEach(topic -> {
                if (topic.isEnabled()) {
                    if (topicsString.length() > 0) {
                        topicsString.append(" OR ");
                    }
                    topicsString.append(topic.getName());
                }
            });
        });
        return topicsString.toString();
    }

    public static QueryDto buildQuery(PreferenceEntity preference)
    {

        QueryDto queryDto = null;
        if(preference != null) {
            queryDto = QueryDto.builder()
                    .language(preference.getLanguage())
                    .country(preference.getRegion())
                    .query(NewsAggregatorUtils.buildUserSelectedTopicsString(preference.getCategories()))
                    .category(NewsAggregatorUtils.buildUserSelectedCategoriesString(preference.getCategories()))
                    .build();
        } else {
            queryDto = QueryDto.builder()
                    .language("en")
                    .country("us")
                    .query("Cricket")
                    .query("Sports")
                    .build();
        }
        return queryDto;
    }

    public static NewsResponseDto mergeNewsResponses(List<ArticleDto>... articles)
    {
        List<ArticleDto> mergedArticles = Stream.of(articles)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return new NewsResponseDto(mergedArticles);
    }

    public static void validateAndBuildQuery(QueryDto queryDto, PreferenceEntity preferenceEntity)
    {
        if (queryDto.getCategory() == null || queryDto.getCategory().isEmpty()) {
            queryDto.setCategory(NewsAggregatorUtils.buildUserSelectedCategoriesString(preferenceEntity.getCategories()));
        }
        if (queryDto.getQuery() == null || queryDto.getQuery().isEmpty()) {
            queryDto.setQuery(NewsAggregatorUtils.buildUserSelectedTopicsString(preferenceEntity.getCategories()));
        }
        if (queryDto.getLanguage() == null || queryDto.getLanguage().isEmpty()) {
            queryDto.setLanguage(preferenceEntity.getLanguage());
        }
        if (queryDto.getCountry() == null || queryDto.getCountry().isEmpty()) {
            queryDto.setCountry(preferenceEntity.getRegion());
        }   
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
