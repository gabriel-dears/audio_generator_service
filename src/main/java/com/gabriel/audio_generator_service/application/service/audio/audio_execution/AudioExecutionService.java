package com.gabriel.audio_generator_service.application.service.audio.audio_execution;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.service.AudioProcessingStrategy;
import com.gabriel.audio_generator_service.application.service.audio.audio_deletion.AudioDeletionService;
import com.gabriel.audio_generator_service.application.service.audio.audio_download.AudioDownloadService;
import com.gabriel.audio_generator_service.application.service.audio.audio_splitting.AudioSplittingService;
import com.gabriel.audio_generator_service.application.service.audio.audio_submission.AudioSubmissionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class AudioExecutionService {

    private final AudioDownloadService audioDownloadService;
    private final AudioProcessingStrategy audioProcessingStrategy;
    private final AudioSplittingService audioSplittingService;
    private final AudioSubmissionService audioSubmissionService;
    private final AudioDeletionService audioDeletionService;

    public AudioExecutionService(AudioDownloadService audioDownloadService, AudioProcessingStrategy audioProcessingStrategy, AudioSplittingService audioSplittingService, AudioSubmissionService audioSubmissionService, AudioDeletionService audioDeletionService) {
        this.audioDownloadService = audioDownloadService;
        this.audioProcessingStrategy = audioProcessingStrategy;
        this.audioSplittingService = audioSplittingService;
        this.audioSubmissionService = audioSubmissionService;
        this.audioDeletionService = audioDeletionService;
    }

    public void handleAudioExecution(String videoUrl, String videoId, String channelId) throws IOException, InterruptedException {
        if (isAudioDownloaded(videoUrl, videoId)) {
            CompletableFuture<CommandResult> commandResultCompletableFuture = processAndSplitAudio(videoId);
            if (commandResultCompletableFuture != null) {
                commandResultCompletableFuture.thenRun(() -> {
                    audioSubmissionService.submitAudio(videoId, channelId);
                    audioDeletionService.addAudioToDelete(videoId);
                }).join();
            }
        }
    }

    private boolean isAudioDownloaded(String videoUrl, String videoId) throws IOException, InterruptedException {
        return audioDownloadService.downloadAudio(videoUrl, videoId);
    }

    private CompletableFuture<CommandResult> processAndSplitAudio(String videoId) throws IOException, InterruptedException {
        if (isAudioProcessedAudioGenerated(videoId)) {
            return audioSplittingService.splitAudio(videoId, videoId + "_processed.wav", videoId + "_%03d.wav");
        }
        return null;
    }

    private boolean isAudioProcessedAudioGenerated(String videoId) throws IOException, InterruptedException {
        return audioProcessingStrategy.processAudio(videoId);
    }

}
