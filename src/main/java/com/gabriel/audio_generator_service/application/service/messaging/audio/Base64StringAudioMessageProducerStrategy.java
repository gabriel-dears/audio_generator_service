package com.gabriel.audio_generator_service.application.service.messaging.audio;

import com.gabriel.audio_generator_service.application.messaging.AudioChunkMessage;
import com.gabriel.audio_generator_service.application.service.AudioMessageProducerStrategy;
import com.gabriel.audio_generator_service.domain.model.VideoDetails;
import com.gabriel.audio_generator_service.infrastructure.utils.FileConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Base64StringAudioMessageProducerStrategy implements AudioMessageProducerStrategy {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public Base64StringAudioMessageProducerStrategy(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = "audio_exchange";       // Replace with actual exchange name
        this.routingKey = "audio.routing.key";  // Replace with actual routing key
    }

    @Override
    public void sendMessage(VideoDetails videoDetails, Object audioChunk) throws IOException {
        if (audioChunk instanceof File audioChunkFile) {
            handleBase64Submission(videoDetails, audioChunkFile);
        }
    }

    private void handleBase64Submission(VideoDetails videoDetails, File audioChunkFile) throws IOException {
        String base64Chunk = getBase64ChunkAsBase64(audioChunkFile);
        sendStringMessage(videoDetails, base64Chunk);
    }

    private static String getBase64ChunkAsBase64(File audioChunkFile) throws IOException {
        return FileConverter.fileToBase64(audioChunkFile);
    }

    private void sendStringMessage(VideoDetails videoDetails, String base64Chunk) {
        AudioChunkMessage audioChunkMessage = new AudioChunkMessage(videoDetails.channelId(), videoDetails.videoId(), base64Chunk, videoDetails.tags(), videoDetails.categoryId());
        rabbitTemplate.convertAndSend(exchange, routingKey, audioChunkMessage);
        System.out.println("Sent message: " + audioChunkMessage);
    }
}

