package com.example.new_aggregator.mapper;

import com.example.new_aggregator.models.dto.CategoryRequestDto;
import com.example.new_aggregator.models.dto.PreferenceDto;
import com.example.new_aggregator.models.dto.SourceRequestDto;
import com.example.new_aggregator.models.dto.TopicRequestDto;
import com.example.new_aggregator.models.entities.CategoryEntity;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.models.entities.SourceEntity;
import com.example.new_aggregator.models.entities.TopicEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PreferenceDtoMapper
{
    PreferenceDtoMapper INSTANCE = Mappers.getMapper(PreferenceDtoMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    PreferenceEntity toEntity(PreferenceDto preferenceDto);
    
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TopicEntity toTopicEntity(TopicRequestDto requestDto);
    TopicRequestDto toTopicRequestDto(TopicEntity topicEntity);
    
    @Mapping(target = "preferences", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CategoryEntity toCategoryEntity(CategoryRequestDto requestDto);
    CategoryRequestDto toCategoryRequestDto(CategoryEntity categoryEntity);
    
    @Mapping(target = "preferences", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    SourceEntity toSourceEntity(SourceRequestDto requestDto);
    SourceRequestDto toSourceRequestDto(SourceEntity sourceEntity);
    
    PreferenceDto toDto(PreferenceEntity preferenceEntity);
}
