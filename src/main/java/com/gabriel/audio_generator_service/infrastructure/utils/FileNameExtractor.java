package com.gabriel.audio_generator_service.infrastructure.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameExtractor {
    public static String extractValueFromString(String fileName, String regex) {
        // Regular expression to capture the digits before .wav
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            return matcher.group(1); // This will extract the "000"
        }

        return "";
    }
}

