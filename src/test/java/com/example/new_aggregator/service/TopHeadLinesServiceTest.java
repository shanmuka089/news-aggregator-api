package com.example.new_aggregator.service;

import com.example.new_aggregator.client.GNewsHeadLinesClient;
import com.example.new_aggregator.client.NewsApiClientSource;
import com.example.new_aggregator.client.NewsApiHeadLinesClient;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsArticle;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsResponse;
import com.example.new_aggregator.models.domain.gNewsApiClient.GNewsSource;
import com.example.new_aggregator.models.domain.newsApiClient.*;
import com.example.new_aggregator.models.dto.NewsResponseDto;
import com.example.new_aggregator.models.dto.QueryDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.models.dto.UserDto;
import com.example.new_aggregator.models.entities.CategoryEntity;
import com.example.new_aggregator.models.entities.PreferenceEntity;
import com.example.new_aggregator.models.entities.SourceEntity;
import com.example.new_aggregator.models.entities.TopicEntity;
import com.example.new_aggregator.repository.PreferenceRepository;
import com.example.new_aggregator.service.impl.TopHeadLinesServiceImpl;
import com.example.new_aggregator.utils.ResponseStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopHeadLinesServiceTest
{

    @Mock
    private PreferenceRepository preferenceRepository;

    @Mock
    private GNewsHeadLinesClient gNewsHeadLinesClient;

    @Mock
    private NewsApiClientSource newsApiClientSource;

    @Mock
    private NewsApiHeadLinesClient newsApiHeadLinesClient;
    
    @InjectMocks
    private TopHeadLinesServiceImpl topHeadLinesService;

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
    
    @Test
    void fetchHeadLinesFromAllSources() {
        
        when(preferenceRepository.findByUserId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(buildPreference()));
        
        when(gNewsHeadLinesClient.fetchHeadlinesByQuery(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildGNewsResponse()));
        when(newsApiHeadLinesClient.fetchHeadlinesByQuery(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildNewsApiResponse()));
        
        ResponseDto<NewsResponseDto> response = topHeadLinesService.fetchHeadLinesFromAllSources();
        
        assertEquals(ResponseStatus.SUCCESS, response.getStatus());
        assertFalse(response.getData().getArticles().isEmpty());
        assertEquals(2, response.getData().getArticles().size());
    }
    
    @Test
    void fetchTopHeadlines() {

        QueryDto queryDto = QueryDto.builder()
                        .query("technology")
                        .language("en")
                        .country("us")
                        .category("general")
                        .build();
        when(preferenceRepository.findByUserId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(buildPreference()));
        
        when(gNewsHeadLinesClient.fetchHeadlinesByQuery(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildGNewsResponse()));
        when(newsApiHeadLinesClient.fetchHeadlinesByQuery(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildNewsApiResponse()));
        
        ResponseDto<NewsResponseDto> response = topHeadLinesService.fetchTopHeadlines(queryDto);
        
        assertEquals(ResponseStatus.SUCCESS, response.getStatus());
        assertFalse(response.getData().getArticles().isEmpty());
        assertEquals(2, response.getData().getArticles().size());
    }
    
    @Test
    void fetchHeadlinesFromPreferredSources() {

        when(preferenceRepository.findByUserId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(buildPreference()));
        
        when(newsApiClientSource.fetchNewsBySources(ArgumentMatchers.any())).thenReturn(ResponseDto.success(buildSourceResponse()));
        
        ResponseDto<SourcesResponseDto> response = topHeadLinesService.fetchHeadlinesFromPreferredSources();
        
        assertEquals(ResponseStatus.SUCCESS, response.getStatus());
        assertFalse(response.getData().getSources().isEmpty());
        assertEquals(1, response.getData().getSources().size());
    }
    
    
    public SourcesResponseDto buildSourceResponse() {
        SourcesResponseDto sourcesResponseDto = new SourcesResponseDto();
        Source source = new Source();
        source.setName("TechCrunch");
        source.setDescription("Tech news source");
        source.setUrl("https://techcrunch.com");
        source.setLanguage("en");
        source.setCountry("us");
        source.setCategory("technology");
        source.setId("1");
        sourcesResponseDto.setSources(List.of(source));
        return sourcesResponseDto;
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
