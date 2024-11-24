package com.gabriel.audio_generator_service.application.service.url.query_param;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QueryParamExtractorTest {

    private QueryParamExtractor queryParamExtractor;

    @BeforeEach
    void setUp() {
        queryParamExtractor = new QueryParamExtractor();
    }

    @Test
    void shouldExtractQueryParamsFromValidUrl() throws URISyntaxException {
        // Given a URL with query parameters
        String url = "https://example.com/search?q=java&lang=en";

        // When extracting query parameters
        Map<String, String> queryParams = queryParamExtractor.getQueryParams(url);

        // Then the map should contain the correct key-value pairs
        assertThat(queryParams).hasSize(2);
        assertThat(queryParams).containsEntry("q", "java");
        assertThat(queryParams).containsEntry("lang", "en");
    }

    @Test
    void shouldReturnEmptyMapForUrlWithNoQueryParams() throws URISyntaxException {
        // Given a URL with no query parameters
        String url = "https://example.com/search";

        // When extracting query parameters
        Map<String, String> queryParams = queryParamExtractor.getQueryParams(url);

        // Then the map should be empty
        assertThat(queryParams).isEmpty();
    }

    @Test
    void shouldHandleMalformedQueryString() throws URISyntaxException {
        // Given a URL with a malformed query parameter (missing '=')
        String url = "https://example.com/search?qjava&lang";

        // When extracting query parameters
        Map<String, String> queryParams = queryParamExtractor.getQueryParams(url);

        // Then the map should be empty, as no valid key-value pairs can be extracted
        assertThat(queryParams).isEmpty();
    }

    @Test
    void shouldHandleEmptyQueryString() throws URISyntaxException {
        // Given a URL with an empty query string
        String url = "https://example.com/search?";

        // When extracting query parameters
        Map<String, String> queryParams = queryParamExtractor.getQueryParams(url);

        // Then the map should be empty
        assertThat(queryParams).isEmpty();
    }


}
