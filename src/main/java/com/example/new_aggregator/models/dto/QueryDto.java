package com.example.new_aggregator.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class QueryDto
{
    private String language;
    private String country;
    private String query;
    private String category;
    private String source;
    
    public static QueryDtoBuilder builder() {
        return new QueryDtoBuilder();
    }
    
    public static class QueryDtoBuilder
    {
        private String language;
        private String country;
        private String query;
        private String category;
        private String source;

        public QueryDtoBuilder language(String language) {
            this.language = language;
            return this;
        }

        public QueryDtoBuilder country(String country) {
            this.country = country;
            return this;
        }

        public QueryDtoBuilder query(String query) {
            this.query = query;
            return this;
        }

        public QueryDtoBuilder category(String category) {
            this.category = category;
            return this;
        }

        public QueryDtoBuilder source(String source) {
            this.source = source;
            return this;
        }

        public QueryDto build() {
            return new QueryDto(language, country, query, category, source);
        }
    }
}
