package com.gabriel.audio_generator_service.application.service.url.url_generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UrlGeneratorTest {

    private UrlGenerator urlGenerator;

    @BeforeEach
    void setUp() {
        urlGenerator = new UrlGenerator("/mock/base/url");
    }

    @Test
    void shouldGenerateClipsUrlBasedOnVideoId() {
        String videoId = "video123";
        String expectedUrl = "/mock/base/url" + File.separator + "clips_video123";

        // Ensure the method works correctly
        assertEquals(expectedUrl, urlGenerator.getClipsUrlBasedOnVideoId(videoId));
    }

    @Test
    void shouldGenerateClipsUrlBasedOnVideoIdAsFile() {
        String videoId = "video123";
        File expectedFile = new File("/mock/base/url" + File.separator + "clips_video123");

        // Ensure the file is created correctly
        assertEquals(expectedFile, urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId));
    }

    @Test
    void shouldReturnBaseUrl() {
        // Ensure baseUrl is correctly returned
        assertEquals("/mock/base/url", urlGenerator.getBaseUrl());
    }

    @Test
    void shouldReturnBaseUrlAsFile() {
        File expectedFile = new File("/mock/base/url");

        // Ensure base URL as file is returned correctly
        assertEquals(expectedFile, urlGenerator.getBaseUrlAsFile());
    }
}
