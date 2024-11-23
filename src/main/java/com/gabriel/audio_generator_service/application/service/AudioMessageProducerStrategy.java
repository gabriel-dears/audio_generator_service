package com.gabriel.audio_generator_service.application.service;

import java.io.IOException;

public interface AudioMessageProducerStrategy {
    void sendMessage(String channelId, String videoId, Object audioChunk) throws IOException;
}
