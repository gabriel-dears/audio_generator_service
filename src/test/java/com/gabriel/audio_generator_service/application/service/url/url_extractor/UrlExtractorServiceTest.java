package com.gabriel.audio_generator_service.application.service.url.url_extractor;

import com.gabriel.audio_generator_service.application.service.url.query_param.QueryParamExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlExtractorServiceTest {

    @Mock
    private QueryParamExtractor queryParamExtractor;

    @InjectMocks
    private UrlExtractorService urlExtractorService;

    @BeforeEach
    void setUp() {
        // Initialize the UrlExtractorService with mock dependencies
    }

    @Test
    void shouldExtractVideoIdFromUrl() throws URISyntaxException {
        String videoUrl = "https://www.youtube.com/watch?v=video123";
        Map<String, String> queryParams = Map.of("v", "video123");

        // Mocking the queryParamExtractor to return predefined query parameters
        when(queryParamExtractor.getQueryParams(videoUrl)).thenReturn(queryParams);

        String videoId = urlExtractorService.getVideoId(videoUrl);

        // Verify that the video ID is extracted correctly
        assertEquals("video123", videoId);
    }

    @Test
    void shouldReturnNullIfVideoIdNotPresent() throws URISyntaxException {
        String videoUrl = "https://www.youtube.com/watch?someotherparam=abc";
        Map<String, String> queryParams = Map.of("someotherparam", "abc");

        // Mocking the queryParamExtractor to return predefined query parameters
        when(queryParamExtractor.getQueryParams(videoUrl)).thenReturn(queryParams);

        String videoId = urlExtractorService.getVideoId(videoUrl);

        // Verify that null is returned when video ID is not found
        assertNull(videoId);
    }

    @Test
    void shouldThrowExceptionIfUrlIsInvalid() throws URISyntaxException {
        String invalidUrl = "invalid-url";

        // Mocking the queryParamExtractor to throw an exception for invalid URL
        when(queryParamExtractor.getQueryParams(invalidUrl)).thenThrow(URISyntaxException.class);

        // Verify that an exception is thrown for invalid URLs
        assertThrows(URISyntaxException.class, () -> urlExtractorService.getVideoId(invalidUrl));
    }

}
