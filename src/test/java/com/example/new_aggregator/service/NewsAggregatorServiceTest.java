package com.example.new_aggregator.service;

import com.example.new_aggregator.client.GNewsClient;
import com.example.new_aggregator.client.NewsApiClient;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsArticle;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsSource;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiArticle;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiResponse;
import com.example.new_aggregator.models.domain.newsApiClient.NewsApiSource;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.CategoryEntity;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.models.entities.SourceEntity;
import com.example.new_aggregator.models.entities.TopicEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.utils.ResponseStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Test class for NewsAggregatorService.
 * This class uses Mockito to mock dependencies and Spring Boot's testing framework to load the application context.
 * It is designed to test the functionality of the NewsAggregatorService without requiring a running server.
 * * @see NewsAggregatorService
 */
@SpringBootTest
public class NewsAggregatorServiceTest
{

    @MockitoBean
    private PreferenceRepository preferenceRepository;

    @MockitoBean
    private GNewsClient gNewsClient;

    @MockitoBean
    private NewsApiClient newsApiClient;

    @Autowired
    private NewsAggregatorService newsAggregatorService;
    
    /**
     * Sets up the test environment by mocking the current user authentication.
     * This method is called before each test to ensure that the security context is set up correctly.
     */
    @BeforeEach
    public void setUp()
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
    @AfterEach
    public void cleanUp()
    {
        // Clear the security context after each test
        SecurityContextHolder.clearContext();
    }
    
    /**
     * Test method to verify the retrieval of news from various sources.
     * This method will call the retrieveNewsFromVariousSources method of the NewsAggregatorService
     * and assert that the response is not null.
     */
    @Test
    void retrieveNewsFromVariousSources() {
        
        when(preferenceRepository.findByUserId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(buildPreference()));
        when(gNewsClient.fetchNewsByPreference(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildGNewsResponse()));
        when(newsApiClient.fetchNewsByPreference(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildNewsApiResponse()));
        
        ResponseDto<NewsResponseDto> response = newsAggregatorService.retrieveNewsFromVariousSources();
        
        assertTrue(response != null);
        assertTrue(response.getStatus() == ResponseStatus.SUCCESS);
    }
    
    @Test
    void retriveNewsByQueryBasedOnUserInput() {
        
        when(preferenceRepository.findByUserId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(buildPreference()));
        when(gNewsClient.fetchNewsByPreference(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildGNewsResponse()));
        when(newsApiClient.fetchNewsByPreference(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildNewsApiResponse()));
        
        QueryDto queryDto = QueryDto.builder()
                                    .category("general")
                                    .language("en")
                                    .country("us")
                                    .query("technology")
                                    .source("TechCrunch")
                                    .build();
        
        ResponseDto<NewsResponseDto> response = newsAggregatorService.retriveNewsByQueryBasedOnUserInput(queryDto);
        
        assertTrue(response != null);
        assertTrue(response.getStatus() == ResponseStatus.SUCCESS);
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
    
    public GNewsResponse buildGNewsResponse() {
        GNewsResponse gNewsResponse = new GNewsResponse();
        GNewsArticle articleDto = new GNewsArticle();
        articleDto.setTitle("Sample GNews Article");
        articleDto.setDescription("This is a sample article from GNews.");
        articleDto.setUrl("https://example.com/sample-gnews-article");
        articleDto.setPublishedAt("2023-10-01T12:00:00Z");
        articleDto.setSource(new GNewsSource("13","Sample Source", "https://example.com/source-logo.png"));
        gNewsResponse.setArticles(List.of(articleDto));
        return gNewsResponse;
    }
    
    public NewsApiResponse buildNewsApiResponse() {
        NewsApiResponse newsApiResponse = new NewsApiResponse();
        NewsApiArticle article = new NewsApiArticle();
        article.setTitle("Sample News API Article");
        article.setDescription("This is a sample article from News API.");
        article.setUrl("https://example.com/sample-news-api-article");
        article.setPublishedAt("2023-10-01T12:00:00Z");
        article.setSource(new NewsApiSource("1", "Sample News API Source", "https://example.com/news-api-source-logo.png"));
        newsApiResponse.setArticles(List.of(article));
        return newsApiResponse;
    }
    
    

}
