package com.example.new_aggregator.config;

import com.example.new_aggregator.config.filter.AuthenticationFilter;
import com.example.new_aggregator.exception.NewsAggregatorErrorCode;
import com.example.new_aggregator.exception.NewsAggregatorException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig
{
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    {
        try {
            http.csrf(csrfConfigurer -> csrfConfigurer
                            .disable())
                    .authorizeHttpRequests(httpRequestsConfigure -> httpRequestsConfigure
                            .requestMatchers(HttpMethod.POST, "news-aggregator/api/v1/users")
                            .permitAll()
                            .requestMatchers(
                                    "news-aggregator/api/v1/authenticate",
                                    "news-aggregator/api/v1/verify-email",
                                    "/swagger-ui/**",         
                                    "/v3/api-docs/**",   
                                    "/swagger-resources/**",
                                    "/swagger.json")
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                    .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

            return http.build();
        } catch(Exception exception) {
            throw new NewsAggregatorException(NewsAggregatorErrorCode.FORBIDDEN);
        }
    }
}
