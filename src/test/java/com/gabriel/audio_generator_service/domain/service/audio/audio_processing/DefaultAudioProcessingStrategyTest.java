package com.gabriel.audio_generator_service.domain.service.audio.audio_processing;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class DefaultAudioProcessingStrategyTest {

    @Mock
    private UrlGenerator urlGenerator;

    @Mock
    private ProcessBuilderSyncCommandRunner processBuilderSyncCommandRunner;

    @Mock
    private CommandResult commandResult;

    @InjectMocks
    private DefaultAudioProcessingStrategy audioProcessingStrategy;

    @BeforeEach
    public void setUp() {
        // Prepare mocks
        Mockito.when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(anyString()))
                .thenReturn(new java.io.File("dummyDir"));
    }

    @Test
    public void testProcessAudioSuccess() throws IOException, InterruptedException {
        // Arrange: mock successful command execution
        Mockito.when(processBuilderSyncCommandRunner.execute()).thenReturn(commandResult);
        Mockito.when(commandResult.getExitCode()).thenReturn(0);  // Simulate successful execution

        // Act: process the audio
        boolean result = audioProcessingStrategy.processAudio("video123");

        // Assert: verify success
        assertTrue(result, "Audio processing should succeed");
    }

    @Test
    public void testProcessAudioFailure() throws IOException, InterruptedException {
        // Arrange: mock failed command execution
        Mockito.when(processBuilderSyncCommandRunner.execute()).thenReturn(commandResult);
        Mockito.when(commandResult.getExitCode()).thenReturn(1);  // Simulate failure

        // Act: process the audio
        boolean result = audioProcessingStrategy.processAudio("video123");

        // Assert: verify failure
        assertFalse(result, "Audio processing should fail");
    }

    @Test
    public void testProcessAudioIOException() throws IOException, InterruptedException {
        // Arrange: mock IOException
        Mockito.when(processBuilderSyncCommandRunner.execute()).thenThrow(new IOException("Command failed"));

        // Act & Assert: expect an IOException to be thrown
        try {
            audioProcessingStrategy.processAudio("video123");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Command failed"), "IOException should be thrown");
        }
    }
}
