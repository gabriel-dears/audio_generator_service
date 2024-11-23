package com.gabriel.audio_generator_service.application.service.messaging.audio;

import com.gabriel.audio_generator_service.application.messaging.AudioChunkMessage;
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
    public void sendMessage(String channelId, String videoId, Object audioChunk) throws IOException {
        if (audioChunk instanceof File audioChunkFile) {
            handleBase64Submission(channelId, videoId, audioChunkFile);
        }
    }

    private void handleBase64Submission(String channelId, String videoId, File audioChunkFile) throws IOException {
        String base64Chunk = getBase64ChunkAsBase64(audioChunkFile);
        sendStringMessage(channelId, videoId, base64Chunk);
    }

    private static String getBase64ChunkAsBase64(File audioChunkFile) throws IOException {
        return FileConverter.fileToBase64(audioChunkFile);
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

    public void sendStringMessage(String channelId, String videoId, String base64Chunk) {
        AudioChunkMessage audioChunkMessage = new AudioChunkMessage(channelId, videoId, base64Chunk);
        rabbitTemplate.convertAndSend(exchange, routingKey, audioChunkMessage);
        System.out.println("Sent message: " + audioChunkMessage);
    }
}

