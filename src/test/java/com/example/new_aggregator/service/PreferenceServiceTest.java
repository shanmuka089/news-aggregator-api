package com.example.new_aggregator.service;

import com.example.new_aggregator.exception.NewsAggregatorException;
import com.example.new_aggregator.models.dto.*;
import com.example.new_aggregator.models.entities.CategoryEntity;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.models.entities.SourceEntity;
import com.example.new_aggregator.models.entities.TopicEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.impl.PreferenceServiceImpl;
import com.example.new_aggregator.utils.NewsAggregatorUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PreferenceServiceTest
{

    @Mock
    private PreferenceRepository preferenceRepository;

    @Mock
    private NewsAggregatorUtils newsAggregatorUtils;

    @InjectMocks
    PreferenceServiceImpl preferenceService;

    /**
     * Sets up the test environment by mocking the current user authentication.
     * This method is called before each test to ensure that the security context is set up correctly.
     */
    @BeforeAll
    public static void setUp()
    {
        // Mock the current user authentication
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("testUser", "password");
        UserDto userDto = new UserDto();
        userDto.setUserId(1L);
        userDto.setUsername("testUser");
        authentication.setDetails(userDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Cleans up the test environment by clearing the security context.
     * This method is called after each test to ensure that the security context does not retain any state from previous tests.
     */
    @AfterAll
    public static void cleanUp()
    {
        // Clear the security context after each test
        SecurityContextHolder.clearContext();
    }
    /**
     * Test for fetching user preferences.
     * This test verifies that the fetchPreferences method returns the correct PreferenceDto.
     */
    @Test
    void fetchPreferences() 
    {
        when(preferenceRepository.findByUserId(1L)).thenReturn(Optional.of(buildPreference()));
        
        PreferenceDto preferenceDto = preferenceService.fetchPreferences();
        
        assertNotNull(preferenceDto);
        assertTrue(preferenceDto.getUserId() == 1L);
    }
    
    /**
     * Test for updating user preferences.
     * This test verifies that the updatePreference method updates the preferences correctly.
     */
    @Test
    void updatePreference() 
    {
        PreferenceEntity existingPreference = buildPreference();
        when(preferenceRepository.findByUserId(1L)).thenReturn(Optional.of(existingPreference));
        
        PreferenceDto preferenceDto = new PreferenceDto();
        preferenceDto.setUserId(1L);
        preferenceDto.setLanguage("fr");

        doAnswer(invocation -> {
            PreferenceDto dto = invocation.getArgument(0);
            PreferenceEntity entity = invocation.getArgument(1);
            entity.setLanguage(dto.getLanguage());
            return null;
        }).when(newsAggregatorUtils).updateEntityFromDto(preferenceDto, existingPreference);
        when(preferenceRepository.save(existingPreference)).thenReturn(existingPreference);
        
        PreferenceDto updatedPreference = preferenceService.updatePreference(preferenceDto);
        
        assertNotNull(updatedPreference);
        assertTrue(updatedPreference.getLanguage().equals("fr"));
    }

    /**
     * Test if different try to modify preferences of other users.
     * @return
     */
    @Test
    void updatePreferenceForbidden() 
    {
        PreferenceDto preferenceDto = new PreferenceDto();
        preferenceDto.setUserId(2L);
        
        try {
            preferenceService.updatePreference(preferenceDto);
        } catch (Exception e) {
            assertTrue(e instanceof NewsAggregatorException);
        }
        
        verify(preferenceRepository, never()).save(any());
    }
    
    @Test
    void savePreference() {
        
        PreferenceEntity preferenceEntity = buildPreference();
        
        when(preferenceRepository.findByUserId(anyLong())).thenReturn(Optional.of(preferenceEntity));
        
        when(preferenceRepository.save(any(PreferenceEntity.class))).thenReturn(preferenceEntity);
        
        PreferenceDto preferenceDto = new PreferenceDto();
        preferenceDto.setUserId(1L);
        preferenceDto.setLanguage("en");
        preferenceDto.setRegion("us");
        
        PreferenceDto savedPreference = preferenceService.savePreferences(preferenceDto);
        
        assertNotNull(savedPreference);
        assertTrue(savedPreference.getUserId() == 1L);
        assertTrue(savedPreference.getLanguage().equals("en"));
    }


    @Test
    void savePreferenceIfNotExist() {

        PreferenceEntity preferenceEntity = buildPreference();

        when(preferenceRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        when(preferenceRepository.save(any(PreferenceEntity.class))).thenReturn(preferenceEntity);

        PreferenceDto preferenceDto = buiildPreferenceDto();

        PreferenceDto savedPreference = preferenceService.savePreferences(preferenceDto);

        assertNotNull(savedPreference);
        assertTrue(savedPreference.getUserId() == 1L);
        assertTrue(savedPreference.getLanguage().equals("en"));
    }
    
    @Test
    void testDeletePreferences() {
        doNothing().when(preferenceRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> preferenceService.deletePreferences(1L));
    }

    public PreferenceDto buiildPreferenceDto() {
        PreferenceDto preferenceDto = new PreferenceDto();
        preferenceDto.setUserId(1L);
        preferenceDto.setEnabled(true);
        preferenceDto.setLanguage("en");
        preferenceDto.setRegion("us");

        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("general");
        categoryRequestDto.setDescription("General news");
        categoryRequestDto.setEnabled(true);

        TopicRequestDto topicRequestDto = new TopicRequestDto();
        topicRequestDto.setName("technology");
        topicRequestDto.setDescription("Technology news");
        topicRequestDto.setEnabled(true);
        categoryRequestDto.setTopics(List.of(topicRequestDto));

        preferenceDto.setCategories(List.of(categoryRequestDto));

        SourceRequestDto sourceRequestDto = new SourceRequestDto();
        sourceRequestDto.setName("TechCrunch");
        sourceRequestDto.setDescription("TechCrunch news source");
        sourceRequestDto.setEnabled(true);
        sourceRequestDto.setSourceId(1L);

        preferenceDto.setSources(List.of(sourceRequestDto));

        return preferenceDto;
    }
    public PreferenceEntity buildPreference() {
        PreferenceEntity preferenceEntity = new PreferenceEntity();
        preferenceEntity.setUserId(1L);
        preferenceEntity.setEnabled(true);
        preferenceEntity.setLanguage("en");
        preferenceEntity.setRegion("us");

        CategoryEntity categoryEntity = getCategoryEntity(preferenceEntity);

        preferenceEntity.setCategories(List.of(categoryEntity));

        SourceEntity sourceEntity = new SourceEntity();
        sourceEntity.setName("TechCrunch");
        sourceEntity.setDescription("Tech news source");
        sourceEntity.setEnabled(true);
        sourceEntity.setSourceId(1L);
        sourceEntity.setPreferences(List.of(preferenceEntity));

        preferenceEntity.setSources(List.of(sourceEntity));

        return preferenceEntity;
    }

    private static CategoryEntity getCategoryEntity(PreferenceEntity preferenceEntity)
    {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("general");
        categoryEntity.setDescription("General news");
        categoryEntity.setEnabled(true);
        categoryEntity.setCategoryId(1L);
        categoryEntity.setPreferences(preferenceEntity);

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName("technology");
        topicEntity.setDescription("Technology news");
        topicEntity.setEnabled(true);
        topicEntity.setTopicId(1L);
        topicEntity.setCategories(categoryEntity);
        return categoryEntity;
    }
}
