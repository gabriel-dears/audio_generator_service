package com.gabriel.audio_generator_service.application.service;

import com.gabriel.audio_generator_service.domain.model.VideoDetails;

import java.io.IOException;

public interface AudioMessageProducerStrategy {
    void sendMessage(VideoDetails videoDetails, Object audioChunk) throws IOException;
}
