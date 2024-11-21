package com.gabriel.audio_generator_service.infrastructure.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class FileConverter {

    public static String fileToBase64(File file) throws IOException {
        byte[] fileBytes = FileUtils.readFileToByteArray(file);
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    public static File[] getAsFileArray(File pathAsFile, String videoId) {
        if (pathAsFile.isDirectory()) {
            Optional<File[]> optChunkFiles = getFilesAsChunkFiles(videoId + "_%03d.wav", pathAsFile);
            return optChunkFiles.orElseGet(FileConverter::getEmptyFileArray);
        }
        return getEmptyFileArray();
    }

    private static File[] getEmptyFileArray() {
        return new File[0];
    }

    private static Optional<File[]> getFilesAsChunkFiles(String outputPattern, File clipsFolder) {
        return Optional.ofNullable(clipsFolder.listFiles((dir, name) -> name.matches(outputPattern.replace("%03d", "\\d{3}"))));
    }

}
