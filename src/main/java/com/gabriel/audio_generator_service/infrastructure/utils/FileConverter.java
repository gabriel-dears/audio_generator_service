package com.gabriel.audio_generator_service.infrastructure.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class FileConverter {

    public static String fileToBase64(File file) throws IOException {
        byte[] fileBytes = FileUtils.readFileToByteArray(file);
        return Base64.getEncoder().encodeToString(fileBytes);
    }

}
