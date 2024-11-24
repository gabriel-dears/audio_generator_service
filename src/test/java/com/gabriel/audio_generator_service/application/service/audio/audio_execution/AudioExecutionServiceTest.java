package com.gabriel.audio_generator_service.application.service.audio.audio_execution;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.service.AudioProcessingStrategy;
import com.gabriel.audio_generator_service.application.service.audio.audio_deletion.AudioDeletionService;
import com.gabriel.audio_generator_service.application.service.audio.audio_download.AudioDownloadService;
import com.gabriel.audio_generator_service.application.service.audio.audio_splitting.AudioSplittingService;
import com.gabriel.audio_generator_service.application.service.audio.audio_submission.AudioSubmissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class AudioExecutionServiceTest {

    @Mock
    private AudioDownloadService audioDownloadService;

    @Mock
    private AudioProcessingStrategy audioProcessingStrategy;

    @Mock
    private AudioSplittingService audioSplittingService;

    @Mock
    private AudioSubmissionService audioSubmissionService;

    @Mock
    private AudioDeletionService audioDeletionService;

    @InjectMocks
    private AudioExecutionService audioExecutionService;

    private String videoUrl;
    private String videoId;
    private String channelId;

    @BeforeEach
    void setUp() {
        videoUrl = "https://example.com/video";
        videoId = "video123";
        channelId = "channel123";
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldHandleAudioExecutionSuccessfully() throws IOException, InterruptedException {
        // Mock behavior for each service method
        when(audioDownloadService.downloadAudio(videoUrl, videoId)).thenReturn(true);
        when(audioProcessingStrategy.processAudio(videoId)).thenReturn(true);
        when(audioSplittingService.splitAudio(videoId, videoId + "_processed.wav", videoId + "_%03d.wav"))
                .thenReturn(CompletableFuture.completedFuture(new CommandResult(0, "Audio processed and split", "")));

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoUrl, videoId, channelId);

        // Verify the interactions
        verify(audioDownloadService).downloadAudio(videoUrl, videoId);
        verify(audioProcessingStrategy).processAudio(videoId);
        verify(audioSplittingService).splitAudio(videoId, videoId + "_processed.wav", videoId + "_%03d.wav");
        verify(audioSubmissionService).submitAudio(videoId, channelId);
        verify(audioDeletionService).addAudioToDelete(videoId);
    }

    @Test
    void shouldNotSubmitAudioIfDownloadFails() throws IOException, InterruptedException {
        // Mock download failure
        when(audioDownloadService.downloadAudio(videoUrl, videoId)).thenReturn(false);

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoUrl, videoId, channelId);

        // Verify no further actions were taken
        verify(audioDownloadService).downloadAudio(videoUrl, videoId);
        verify(audioProcessingStrategy, never()).processAudio(videoId);
        verify(audioSplittingService, never()).splitAudio(any(), any(), any());
        verify(audioSubmissionService, never()).submitAudio(any(), any());
        verify(audioDeletionService, never()).addAudioToDelete(any());
    }

    @Test
    void shouldNotProcessAudioIfProcessingFails() throws IOException, InterruptedException {
        // Mock successful download but failed audio processing
        when(audioDownloadService.downloadAudio(videoUrl, videoId)).thenReturn(true);
        when(audioProcessingStrategy.processAudio(videoId)).thenReturn(false);

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoUrl, videoId, channelId);

        // Verify no splitting and no further actions
        verify(audioDownloadService).downloadAudio(videoUrl, videoId);
        verify(audioProcessingStrategy).processAudio(videoId);
        verify(audioSplittingService, never()).splitAudio(any(), any(), any());
        verify(audioSubmissionService, never()).submitAudio(any(), any());
        verify(audioDeletionService, never()).addAudioToDelete(any());
    }

    @Test
    void shouldHandleAudioExecutionWithNullProcessingResult() throws IOException, InterruptedException {
        // Mock download success, but null processing result (no audio generated)
        when(audioDownloadService.downloadAudio(videoUrl, videoId)).thenReturn(true);
        when(audioProcessingStrategy.processAudio(videoId)).thenReturn(true);
        when(audioSplittingService.splitAudio(videoId, videoId + "_processed.wav", videoId + "_%03d.wav"))
                .thenReturn(null);

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoUrl, videoId, channelId);

        // Verify no further actions since splitting did not proceed
        verify(audioDownloadService).downloadAudio(videoUrl, videoId);
        verify(audioProcessingStrategy).processAudio(videoId);
        verify(audioSplittingService).splitAudio(videoId, videoId + "_processed.wav", videoId + "_%03d.wav");
        verify(audioSubmissionService, never()).submitAudio(any(), any());
        verify(audioDeletionService, never()).addAudioToDelete(any());
    }
}
