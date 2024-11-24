package com.gabriel.audio_generator_service.application.service.audio.audio_submission;

import com.gabriel.audio_generator_service.application.service.AudioMessageProducerStrategy;
import com.gabriel.audio_generator_service.application.service.audio.file.AudioFilesConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioSubmissionServiceTest {

    @Mock
    private AudioFilesConverter audioFilesConverter;

    @Mock
    private AudioMessageProducerStrategy audioMessageProducerStrategy;

    @InjectMocks
    private AudioSubmissionService audioSubmissionService;

    private final String videoId = "video123";
    private final String channelId = "channel456";

    @Test
    void shouldSubmitAllAudioChunksToQueue() throws IOException {
        // Arrange
        File[] mockFiles = {mock(File.class), mock(File.class)};
        when(audioFilesConverter.getAsFileArray(videoId)).thenReturn(mockFiles);

        // Act
        audioSubmissionService.submitAudio(videoId, channelId);

        // Assert
        for (File file : mockFiles) {
            verify(audioMessageProducerStrategy).sendMessage(channelId, videoId, file);
        }
    }

    @Test
    void shouldLogErrorWhenIOExceptionOccurs() throws IOException {
        // Arrange
        File[] mockFiles = {mock(File.class)};
        when(audioFilesConverter.getAsFileArray(videoId)).thenReturn(mockFiles);
        doThrow(new IOException("Test exception")).when(audioMessageProducerStrategy).sendMessage(anyString(), anyString(), any(File.class));

        // Act
        audioSubmissionService.submitAudio(videoId, channelId);

        // Assert
        verify(audioMessageProducerStrategy).sendMessage(channelId, videoId, mockFiles[0]);
        // Ensure no further interactions when an exception occurs
        verifyNoMoreInteractions(audioMessageProducerStrategy);
    }

    @Test
    void shouldHandleEmptyFileArrayGracefully() throws IOException {
        // Arrange
        when(audioFilesConverter.getAsFileArray(videoId)).thenReturn(new File[0]);

        // Act
        audioSubmissionService.submitAudio(videoId, channelId);

        // Assert
        verify(audioMessageProducerStrategy, never()).sendMessage(anyString(), anyString(), any(File.class));
    }
}
