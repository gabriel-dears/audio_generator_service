package com.gabriel.audio_generator_service.application.service;

import java.io.IOException;

public interface AudioProcessingStrategy {
    boolean processAudio(String videoId) throws IOException, InterruptedException;
}