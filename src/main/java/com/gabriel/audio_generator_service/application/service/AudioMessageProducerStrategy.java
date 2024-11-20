package com.gabriel.audio_generator_service.application.service;

import java.io.File;
import java.io.IOException;

public interface AudioMessageProducerStrategy {
    void sendMessage(File file) throws IOException;
}
