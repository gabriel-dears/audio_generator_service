package com.gabriel.audio_generator_service.application.service.messaging.audio;

import com.gabriel.audio_generator_service.infrastructure.utils.FileConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class Base64StringAudioMessageProducerStrategyTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private Base64StringAudioMessageProducerStrategy base64StringAudioMessageProducerStrategy;

    @Mock
    private File mockFile;

    @Test
    void shouldSendBase64MessageSuccessfully() throws IOException {
        try (MockedStatic<FileConverter> mockedFileConverter = mockStatic(FileConverter.class)) {
            String channelId = "channel123";
            String videoId = "video123";
            mockedFileConverter.when(() -> FileConverter.fileToBase64(mockFile)).thenReturn("base64String");
            base64StringAudioMessageProducerStrategy.sendMessage(channelId, videoId, mockFile);
        }
    }

    @Test
    void shouldNotSendBase64MessageWhenFileIsNull() throws IOException {
        try (MockedStatic<FileConverter> mockedFileConverter = mockStatic(FileConverter.class)) {
            String channelId = "channel123";
            String videoId = "video123";
            mockedFileConverter.when(() -> FileConverter.fileToBase64(mockFile)).thenReturn("base64String");
            base64StringAudioMessageProducerStrategy.sendMessage(channelId, videoId, null);
        }
    }

}
