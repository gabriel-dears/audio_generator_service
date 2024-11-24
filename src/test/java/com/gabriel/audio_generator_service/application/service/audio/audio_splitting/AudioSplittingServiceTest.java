package com.gabriel.audio_generator_service.application.service.audio.audio_splitting;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.CommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderAsyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioSplittingServiceTest {

    @Mock
    private UrlGenerator urlGenerator;

    @Mock
    private ProcessBuilderAsyncCommandRunner processBuilderAsyncCommandRunner;

    @InjectMocks
    private AudioSplittingService audioSplittingService;

    @Test
    void shouldExecuteAudioSplittingCommandSuccessfully() throws InterruptedException, ExecutionException {
        CommandResult commandResult = new CommandResult(0, "output", "");
        CommandRunner commandRunner = mock(CommandRunner.class);
        File file = mock(File.class);

        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(any())).thenReturn(file);
        when(processBuilderAsyncCommandRunner.directory(any())).thenReturn(commandRunner);
        when(processBuilderAsyncCommandRunner.command(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(commandRunner);
        when(processBuilderAsyncCommandRunner.executeAsync()).thenReturn(CompletableFuture.completedFuture(commandResult));

        String videoId = "video123";
        String inputFileName = "input.mp3";
        String outputPattern = "output_%03d.mp3";
        CompletableFuture<CommandResult> commandResultCompletableFuture = audioSplittingService.splitAudio(videoId, inputFileName, outputPattern);

        verify(processBuilderAsyncCommandRunner).directory(any());
        verify(processBuilderAsyncCommandRunner).command(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        verify(processBuilderAsyncCommandRunner).executeAsync();

        assertThat(commandResultCompletableFuture).isNotNull();
        assertThat(commandResultCompletableFuture.get()).isNotNull();
        assertThat(commandResultCompletableFuture.get()).isEqualTo(commandResult);

    }

}
