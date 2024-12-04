package com.gabriel.audio_generator_service.application.service.messaging.audio;

import com.gabriel.audio_generator_service.domain.model.VideoDetails;
import com.gabriel.audio_generator_service.infrastructure.utils.FileConverter;
import com.gabriel.audio_generator_service.infrastructure.utils.FileNameExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Base64StringAudioMessageProducerStrategyTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private Base64StringAudioMessageProducerStrategy base64StringAudioMessageProducerStrategy;

    @Mock
    private File mockFile;

    private final String channelId = "channel123";
    private final String videoId = "video123";

    private final VideoDetails videoDetails = new VideoDetails(videoId, null, null, channelId);

    @Test
    void shouldSendBase64MessageSuccessfully() throws IOException {
        try (MockedStatic<FileConverter> mockedFileConverter = mockStatic(FileConverter.class);
             MockedStatic<FileNameExtractor> mockedFileNameExtractor = mockStatic(FileNameExtractor.class)) {

            // Mock the static methods
            mockedFileConverter.when(() -> FileConverter.fileToBase64(mockFile)).thenReturn("base64String");
            mockedFileNameExtractor.when(() -> FileNameExtractor.extractValueFromString(anyString(), eq("_(\\d+)(?=\\.wav$)"))).thenReturn("000");

            // Mock RabbitTemplate
            RabbitTemplate rabbitTemplateMock = mock(RabbitTemplate.class);

            // Create the strategy with the mocked RabbitTemplate
            Base64StringAudioMessageProducerStrategy producerStrategy =
                    new Base64StringAudioMessageProducerStrategy(rabbitTemplateMock);

            // Call the method under test
            producerStrategy.sendMessage(videoDetails, mockFile);
        }
    }


    @Test
    void shouldNotSendBase64MessageWhenFileIsNull() throws IOException {
        try (MockedStatic<FileConverter> mockedFileConverter = mockStatic(FileConverter.class)) {
            mockedFileConverter.when(() -> FileConverter.fileToBase64(mockFile)).thenReturn("base64String");
            base64StringAudioMessageProducerStrategy.sendMessage(videoDetails, null);
        }
    }

}
