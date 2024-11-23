package com.gabriel.audio_generator_service.application.service.url.url_generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class UrlGenerator {

    private final String baseUrl;

    public UrlGenerator(@Value("${clips-dir}") String baseUrl) {
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
