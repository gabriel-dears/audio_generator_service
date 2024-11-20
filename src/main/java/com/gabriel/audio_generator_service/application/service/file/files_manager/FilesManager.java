package com.gabriel.audio_generator_service.application.service.file.files_manager;

import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class FilesManager {

    private final UrlGenerator urlGenerator;

    public FilesManager(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    public File[] getAsFileArray(String videoId) {
        File clipsFolder = new File(urlGenerator.getClipsUrlBasedOnVideoId(videoId));
        if (clipsFolder.isDirectory()) {
            Optional<File[]> optChunkFiles = getFilesAsChunkFiles(videoId + "_%03d.wav", clipsFolder);
            return optChunkFiles.orElseGet(this::getEmptyFileArray);
        }
        return getEmptyFileArray();
    }

    private File[] getEmptyFileArray() {
        return new File[0];
    }

    private Optional<File[]> getFilesAsChunkFiles(String outputPattern, File clipsFolder) {
        return Optional.ofNullable(clipsFolder.listFiles((dir, name) -> name.matches(outputPattern.replace("%03d", "\\d{3}"))));
    }

}
