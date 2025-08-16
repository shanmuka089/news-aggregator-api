package com.example.new_aggregator.config;

import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Data;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerConfiguration
{

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("News Aggregator API")
                        .version("v1.2.0")
                        .description("Public API for aggregating news from multiple providers. " +
                                "This API returns curated news articles for users.")
                        .contact(new Contact()
                                .name("News Team")
                                .email("shanmuka089@gmail.com")
                                .url("https://example.com/support"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8080/").description("Production"),
                        new Server().url("http://localhost:8080/").description("Staging")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Developer Guide")
                        .url("https://example.com/docs"))
                .tags(List.of(
                        new Tag().name("News").description("Operations related to news articles"),
                        new Tag().name("Sources").description("Operations related to news sources"),
                        new Tag().name("Headlines").description("Operations related to news headlines"),
                        new Tag().name("Users").description("Operations related to user management")
                ));
    }

    
    private Map<String, ApiResponseConfig> apiResponseConfigs()
    {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("swagger-errors.json")) {
            return new ObjectMapper().readValue(
                    in,
                    new TypeReference<Map<String, ApiResponseConfig>>() {}
            );
        } catch(IOException e) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.PARSING_ERROR);
        }
    }
    
    @Bean
    public OperationCustomizer autoApiDocsCustomizer() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethod().isAnnotationPresent(ApiDocsConfig.class) ||
                    handlerMethod.getBeanType().isAnnotationPresent(ApiDocsConfig.class)) {

                apiResponseConfigs().forEach((statusCode, config) -> {
                    ApiResponse apiResponse = new ApiResponse().description(config.getDescription());

                    MediaType mediaType = new MediaType()
                            .schema(new Schema<>().$ref(config.getSchema()));

                    if (config.getExample() != null) {
                        Example example = new Example().value(config.getExample());
                        mediaType.addExamples(config.getExampleName(), example);
                    }

                    Content content = new Content()
                            .addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, mediaType);

                    apiResponse.setContent(content);

                    operation.getResponses().put(statusCode, apiResponse);
                });
            }
            return operation;
        };
    }
}

@Data
class ApiResponseConfig {
    private String description;
    private String schema;
    private String exampleName;
    private Map<String, Object> example;
}


