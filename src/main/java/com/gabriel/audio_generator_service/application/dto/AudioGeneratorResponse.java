package com.gabriel.audio_generator_service.application.dto;

public class AudioGeneratorResponse {
    private String message;

    public AudioGeneratorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}