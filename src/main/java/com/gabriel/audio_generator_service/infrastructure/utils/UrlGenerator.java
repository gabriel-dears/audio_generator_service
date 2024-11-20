package com.gabriel.audio_generator_service.infrastructure.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UrlGenerator {

    private final String baseUrl;

    public UrlGenerator(@Value("${CLIPS_DIR}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getClipsUrlBasedOnVideoId(String videoId) {
        String folderNameForSpecificVideo = "clips_" + videoId;
        return String.format("%s" + File.separator + "%s", baseUrl, folderNameForSpecificVideo);
    }

    public File getClipsUrlBasedOnVideoIdAsFile(String videoId) {
        return new File(getClipsUrlBasedOnVideoId(videoId));
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public File getBaseUrlAsFile() {
        return new File(getBaseUrl());
    }
}
