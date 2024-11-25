package com.gabriel.audio_generator_service.application.service.audio.audio_execution;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.service.AudioProcessingStrategy;
import com.gabriel.audio_generator_service.application.service.audio.audio_deletion.AudioDeletionService;
import com.gabriel.audio_generator_service.application.service.audio.audio_download.AudioDownloadService;
import com.gabriel.audio_generator_service.application.service.audio.audio_splitting.AudioSplittingService;
import com.gabriel.audio_generator_service.application.service.audio.audio_submission.AudioSubmissionService;
import com.gabriel.audio_generator_service.domain.model.VideoDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    private VideoDetails videoDetails;

    @BeforeEach
    void setUp() {
        String videoId = "video123";
        String channelId = "channel123";
        videoDetails = new VideoDetails(videoId, null, null, channelId);
    }

    @Test
    void shouldHandleAudioExecutionSuccessfully() throws IOException, InterruptedException {
        // Mock behavior for each service method
        when(audioDownloadService.downloadAudio(videoDetails.videoId())).thenReturn(true);
        when(audioProcessingStrategy.processAudio(videoDetails.videoId())).thenReturn(true);
        when(audioSplittingService.splitAudio(videoDetails.videoId(), videoDetails.videoId() + "_processed.wav", videoDetails.videoId() + "_%03d.wav"))
                .thenReturn(CompletableFuture.completedFuture(new CommandResult(0, "Audio processed and split", "")));

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoDetails);

        // Verify the interactions
        verify(audioDownloadService).downloadAudio(videoDetails.videoId());
        verify(audioProcessingStrategy).processAudio(videoDetails.videoId());
        verify(audioSplittingService).splitAudio(videoDetails.videoId(), videoDetails.videoId() + "_processed.wav", videoDetails.videoId() + "_%03d.wav");
        verify(audioSubmissionService).submitAudio(videoDetails);
        verify(audioDeletionService).addAudioToDelete(videoDetails.videoId());
    }

    @Test
    void shouldNotSubmitAudioIfDownloadFails() throws IOException, InterruptedException {
        // Mock download failure
        when(audioDownloadService.downloadAudio(videoDetails.videoId())).thenReturn(false);

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoDetails);

        // Verify no further actions were taken
        verify(audioDownloadService).downloadAudio(videoDetails.videoId());
        verify(audioProcessingStrategy, never()).processAudio(videoDetails.videoId());
        verify(audioSplittingService, never()).splitAudio(any(), any(), any());
        verify(audioSubmissionService, never()).submitAudio(any());
        verify(audioDeletionService, never()).addAudioToDelete(any());
    }

    @Test
    void shouldNotProcessAudioIfProcessingFails() throws IOException, InterruptedException {
        // Mock successful download but failed audio processing
        when(audioDownloadService.downloadAudio(videoDetails.videoId())).thenReturn(true);
        when(audioProcessingStrategy.processAudio(videoDetails.videoId())).thenReturn(false);

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoDetails);

        // Verify no splitting and no further actions
        verify(audioDownloadService).downloadAudio(videoDetails.videoId());
        verify(audioProcessingStrategy).processAudio(videoDetails.videoId());
        verify(audioSplittingService, never()).splitAudio(any(), any(), any());
        verify(audioSubmissionService, never()).submitAudio(any());
        verify(audioDeletionService, never()).addAudioToDelete(any());
    }

    @Test
    void shouldHandleAudioExecutionWithNullProcessingResult() throws IOException, InterruptedException {
        // Mock download success, but null processing result (no audio generated)
        when(audioDownloadService.downloadAudio(videoDetails.videoId())).thenReturn(true);
        when(audioProcessingStrategy.processAudio(videoDetails.videoId())).thenReturn(true);
        when(audioSplittingService.splitAudio(videoDetails.videoId(), videoDetails.videoId() + "_processed.wav", videoDetails.videoId() + "_%03d.wav"))
                .thenReturn(null);

        // Run the audio execution
        audioExecutionService.handleAudioExecution(videoDetails);

        // Verify no further actions since splitting did not proceed
        verify(audioDownloadService).downloadAudio(videoDetails.videoId());
        verify(audioProcessingStrategy).processAudio(videoDetails.videoId());
        verify(audioSplittingService).splitAudio(videoDetails.videoId(), videoDetails.videoId() + "_processed.wav", videoDetails.videoId() + "_%03d.wav");
        verify(audioSubmissionService, never()).submitAudio(any());
        verify(audioDeletionService, never()).addAudioToDelete(any());
    }
}
