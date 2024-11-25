package com.gabriel.audio_generator_service.application.service.audio.audio_submission;

import com.gabriel.audio_generator_service.application.service.AudioMessageProducerStrategy;
import com.gabriel.audio_generator_service.application.service.audio.file.AudioFilesConverter;
import com.gabriel.audio_generator_service.domain.model.VideoDetails;
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
    private final VideoDetails videoDetails = new VideoDetails(videoId, null, null, channelId);

    @Test
    void shouldSubmitAllAudioChunksToQueue() throws IOException {
        // Arrange
        File[] mockFiles = {mock(File.class), mock(File.class)};
        when(audioFilesConverter.getAsFileArray(videoId)).thenReturn(mockFiles);

        // Act
        audioSubmissionService.submitAudio(videoDetails);

        // Assert
        for (File file : mockFiles) {
            verify(audioMessageProducerStrategy).sendMessage(videoDetails, file);
        }
    }

    @Test
    void shouldLogErrorWhenIOExceptionOccurs() throws IOException {
        // Arrange
        File[] mockFiles = {mock(File.class)};
        when(audioFilesConverter.getAsFileArray(videoId)).thenReturn(mockFiles);
        doThrow(new IOException("Test exception")).when(audioMessageProducerStrategy).sendMessage(any(), any(File.class));

        // Act
        audioSubmissionService.submitAudio(videoDetails);

        // Assert
        verify(audioMessageProducerStrategy).sendMessage(videoDetails, mockFiles[0]);
        // Ensure no further interactions when an exception occurs
        verifyNoMoreInteractions(audioMessageProducerStrategy);
    }

    @Test
    void shouldHandleEmptyFileArrayGracefully() throws IOException {
        // Arrange
        when(audioFilesConverter.getAsFileArray(videoId)).thenReturn(new File[0]);

        // Act
        audioSubmissionService.submitAudio(videoDetails);

        // Assert
        verify(audioMessageProducerStrategy, never()).sendMessage(any(), any(File.class));
    }
}
