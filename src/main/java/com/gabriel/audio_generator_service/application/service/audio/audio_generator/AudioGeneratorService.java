package com.gabriel.audio_generator_service.application.service.audio.audio_generator;

import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.SyncCommandRunner;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorRequest;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorResponse;
import com.gabriel.audio_generator_service.application.service.AudioProcessingStrategy;
import com.gabriel.audio_generator_service.application.service.audio.audio_download.AudioDownloadService;
import com.gabriel.audio_generator_service.application.service.audio.audio_splitting.AudioSplittingService;
import com.gabriel.audio_generator_service.application.service.messaging.audio.AudioMessageProducer;
import com.gabriel.audio_generator_service.application.service.url.query_param.QueryParamExtractor;
import com.gabriel.audio_generator_service.application.service.youtube.YouTubeService;
import com.gabriel.audio_generator_service.infrastructure.utils.UrlGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AudioGeneratorService {

    private final YouTubeService youTubeService;
    private final AudioDownloadService audioDownloadService;
    private final AudioProcessingStrategy audioProcessingStrategy;
    private final AudioSplittingService audioSplittingService;
    private final QueryParamExtractor queryParamExtractor;
    private final AudioMessageProducer audioMessageProducer;
    private final UrlGenerator urlGenerator;

    public AudioGeneratorService(
            YouTubeService youTubeService,
            AudioDownloadService audioDownloadService,
            AudioProcessingStrategy audioProcessingStrategy,
            AudioSplittingService audioSplittingService,
            QueryParamExtractor queryParamExtractor, AudioMessageProducer audioMessageProducer, UrlGenerator urlGenerator
    ) {
        this.youTubeService = youTubeService;
        this.audioDownloadService = audioDownloadService;
        this.audioProcessingStrategy = audioProcessingStrategy;
        this.audioSplittingService = audioSplittingService;
        this.queryParamExtractor = queryParamExtractor;
        this.audioMessageProducer = audioMessageProducer;
        this.urlGenerator = urlGenerator;
    }

    public AudioGeneratorResponse generateAudios(AudioGeneratorRequest audioGeneratorRequest) {
        try {
            List<String> videos = youTubeService.getChannelVideos(audioGeneratorRequest.getChannelId());
            handleVideosUrlsFromResponse(videos);
            return new AudioGeneratorResponse("Clips created successfully!");
        } catch (IOException | InterruptedException e) {
            return new AudioGeneratorResponse("Error creating clips: " + e.getMessage());
        }
    }

    private void handleVideosUrlsFromResponse(List<String> videos) throws IOException, InterruptedException {
        for (String videoUrl : videos) {
            getVideoFromUrlAndHandleAudio(videoUrl);
        }
    }

    private void getVideoFromUrlAndHandleAudio(String videoUrl) throws IOException, InterruptedException {
        String videoId = getVideoId(videoUrl);
        SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();
        syncCommandRunner.directory(urlGenerator.getBaseUrlAsFile());
        syncCommandRunner.command("mkdir", "-p", "clips_" + videoId);
        syncCommandRunner.execute();
        if (isAudioDownloaded(videoUrl, videoId)) {
            processAndSplitAudio(videoId);
        }
    }

    private boolean isAudioDownloaded(String videoUrl, String videoId) throws IOException, InterruptedException {
        return audioDownloadService.downloadAudio(videoUrl, videoId);
    }

    private void processAndSplitAudio(String videoId) throws IOException, InterruptedException {
        if (isAudioProcessedAudioGenerated(videoId)) {
            audioSplittingService.splitAudio(videoId, videoId + "_processed.wav", videoId + "_%03d.wav");
        }
    }

    private boolean isAudioProcessedAudioGenerated(String videoId) throws IOException, InterruptedException {
        return audioProcessingStrategy.processAudio(videoId);
    }

    private String getVideoId(String videoUrl) {
        Map<String, String> queryParams = queryParamExtractor.getQueryParams(videoUrl);
        return queryParams.get("v");
    }

    public AudioGeneratorResponse test(AudioGeneratorRequest audioGeneratorRequest) {
        audioMessageProducer.sendMessage("bla bla bla");
        return null;
    }
}
