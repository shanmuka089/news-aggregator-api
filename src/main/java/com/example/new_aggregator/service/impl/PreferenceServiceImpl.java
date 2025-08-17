package com.example.new_aggregator.service.impl;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.mapper.PreferenceDtoMapper;
import com.example.new_aggregator.models.dto.PreferenceDto;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.PreferenceService;
import com.example.new_aggregator.utils.NewsAggregatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenceServiceImpl implements PreferenceService
{
    @Autowired
    private PreferenceRepository preferenceRepository;

    @Override
    public PreferenceDto fetchPreferences()
    {
        Long userid = NewsAggregatorUtils.fetchCurrentUser().getUserId();
        PreferenceEntity preferenceEntity = preferenceRepository.findByUserId(userid)
                .orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.PREFERENCE_NOT_FOUND));
        
        PreferenceDto preferenceDto = PreferenceDtoMapper.INSTANCE.toDto(preferenceEntity);
        return preferenceDto;
    }

    @Override
    public PreferenceDto updatePreference(PreferenceDto preferenceDto)
    {
        Long userid = NewsAggregatorUtils.fetchCurrentUser().getUserId();
        
        if(userid != preferenceDto.getUserId()) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.FORBIDDEN);
        }
        
        PreferenceEntity preferenceEntity = preferenceRepository.findByUserId(userid).orElseThrow(() -> new NewsAggregatorException(NewsAggregatorErrorCode.PREFERENCE_NOT_FOUND));
        
        NewsAggregatorUtils.updateEntityFromDto(preferenceDto, preferenceEntity);

        PreferenceEntity updatedPreference = preferenceRepository.save(preferenceEntity);
        
        PreferenceDto updatedPreferenceDto = PreferenceDtoMapper.INSTANCE.toDto(updatedPreference);
        
        return updatedPreferenceDto;
    }

    @Override
    public PreferenceDto savePreferences(PreferenceDto preferenceDto)
    {

        PreferenceEntity preferenceEntity = PreferenceDtoMapper.INSTANCE.toEntity(preferenceDto);
        Long userid = NewsAggregatorUtils.fetchCurrentUser().getUserId();
        preferenceEntity.setUserId(userid);
        PreferenceEntity savedPreference = preferenceRepository.save(preferenceEntity);
        PreferenceDto savedPreferenceDto = PreferenceDtoMapper.INSTANCE.toDto(savedPreference);
        
        return savedPreferenceDto;
    }

    @Override
    public void deletePreferences(Long preferenceId)
    {
        preferenceRepository.deleteById(preferenceId);
    }

}
