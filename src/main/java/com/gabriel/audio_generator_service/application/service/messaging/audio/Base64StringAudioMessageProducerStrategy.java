package com.gabriel.audio_generator_service.application.service.messaging.audio;

import com.gabriel.audio_generator_service.application.service.AudioMessageProducerStrategy;
import com.gabriel.audio_generator_service.infrastructure.utils.FileConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Base64StringAudioMessageProducerStrategy implements AudioMessageProducerStrategy {
    @Override
    public void sendMessage(File file) throws IOException {
        String base64Chunk = FileConverter.fileToBase64(file);
        sendStringMessage(base64Chunk);
    }

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    @Autowired
    public Base64StringAudioMessageProducerStrategy(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = "audio_exchange";       // Replace with actual exchange name
        this.routingKey = "audio.routing.key";  // Replace with actual routing key
    }

    public void sendStringMessage(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Sent message: " + message);
    }
}

