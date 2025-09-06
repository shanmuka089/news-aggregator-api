package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.errorHandling.ErrorResolver;
import com.example.new_aggregator.models.dto.CategoryRequestDto;
import com.example.new_aggregator.models.dto.PreferenceDto;
import com.example.new_aggregator.models.dto.SourceRequestDto;
import com.example.new_aggregator.models.dto.TopicRequestDto;
import com.example.new_aggregator.service.PreferenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PreferencesController.class)
public class PreferenceControllerTest
{
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private PreferenceService preferenceService;
    
    @MockitoBean
    private ErrorResolver errorResolver;
    
    @Test
    void testSavePreferences() {

        PreferenceDto preferenceDto = buildPreferenceDto();
        
        when(preferenceService.savePreferences(any(PreferenceDto.class)))
                .thenReturn(preferenceDto);
        try
        {
            mockMvc.perform(post("/news-aggregator/api/v1/preferences")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(preferenceDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("SUCCESS"))
                    .andExpect(jsonPath("$.data.userId").value(1))
                    .andExpect(jsonPath("$.data.categories").isArray());
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    void testFetchPreferences() {
        
        when(preferenceService.fetchPreferences())
                .thenReturn(buildPreferenceDto());
        
        try {
            mockMvc.perform(get("/news-aggregator/api/v1/preferences")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("SUCCESS"))
                    .andExpect(jsonPath("$.data.userId").value(1))
                    .andExpect(jsonPath("$.data.categories").isArray());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void testUpdatePreferences() {
        
        when(preferenceService.updatePreference(any(PreferenceDto.class)))
                .thenReturn(buildPreferenceDto());
        try {
            mockMvc.perform(put("/news-aggregator/api/v1/preferences")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(buildPreferenceDto())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("SUCCESS"))
                    .andExpect(jsonPath("$.data").isNotEmpty());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    void testDeletePreferences() {
        
        doNothing().when(preferenceService).deletePreferences(1L);
        
        try {
            mockMvc.perform(delete("/news-aggregator/api/v1/preferences/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("SUCCESS"))
                    .andExpect(jsonPath("$.data").value("Preferences deleted successfully"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @TestConfiguration
    static class TestSecurityConfig {
        
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
        {
            httpSecurity
                    .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                    .authorizeHttpRequests(
                    auth -> auth.anyRequest().permitAll()
            );
            return httpSecurity.build();
        }
    }
    
    PreferenceDto buildPreferenceDto() {
        PreferenceDto preferenceDto = new PreferenceDto();
        preferenceDto.setUserId(1L);
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Technology");
        categoryRequestDto.setDescription("Technology news");
        TopicRequestDto topicRequestDto = new TopicRequestDto();
        topicRequestDto.setName("Specific Topic");
        topicRequestDto.setDescription("Specific topic description");
        categoryRequestDto.setTopics(List.of(topicRequestDto));
        SourceRequestDto sourceRequestDto = new SourceRequestDto();
        sourceRequestDto.setName("TechCrunch");
        sourceRequestDto.setDescription("TechCrunch news source");
        preferenceDto.setSources(List.of(sourceRequestDto));
        preferenceDto.setCategories(List.of(categoryRequestDto));
        return preferenceDto;
    }
}
