package com.gabriel.clipcrafter.application.service;

import java.io.IOException;

public interface AudioProcessingStrategy {
    boolean processAudio(String videoId) throws IOException, InterruptedException;
}