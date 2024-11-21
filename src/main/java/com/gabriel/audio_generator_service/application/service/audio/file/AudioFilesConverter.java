package com.gabriel.audio_generator_service.application.service.audio.file;

import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import com.gabriel.audio_generator_service.infrastructure.utils.FileConverter;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AudioFilesConverter {

    private final UrlGenerator urlGenerator;

    public AudioFilesConverter(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    public File[] getAsFileArray(String videoId) {
        return FileConverter.getAsFileArray(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId), videoId);
    }

}
