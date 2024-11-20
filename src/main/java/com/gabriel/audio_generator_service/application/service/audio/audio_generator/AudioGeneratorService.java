package com.gabriel.audio_generator_service.application.service.audio.audio_generator;

import com.gabriel.audio_generator_service.application.dto.AudioGeneratorRequest;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorResponse;
import com.gabriel.audio_generator_service.application.service.audio.audio_base_folder_creator.AudioBaseFolderCreatorService;
import com.gabriel.audio_generator_service.application.service.audio.audio_execution.AudioExecutionService;
import com.gabriel.audio_generator_service.application.service.url.url_extractor.UrlExtractorService;
import com.gabriel.audio_generator_service.application.service.youtube.YouTubeService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AudioGeneratorService {

    private final YouTubeService youTubeService;
    private final AudioExecutionService audioExecutionService;
    private final UrlExtractorService urlExtractorService;
    private final AudioBaseFolderCreatorService audioBaseFolderCreatorService;

    public AudioGeneratorService(
            YouTubeService youTubeService,
            AudioExecutionService audioExecutionService,
            UrlExtractorService urlExtractorService, AudioBaseFolderCreatorService audioBaseFolderCreatorService
    ) {
        this.youTubeService = youTubeService;
        this.audioExecutionService = audioExecutionService;
        this.urlExtractorService = urlExtractorService;
        this.audioBaseFolderCreatorService = audioBaseFolderCreatorService;
    }

    public AudioGeneratorResponse executeAudioGeneration(AudioGeneratorRequest audioGeneratorRequest) {
        try {
            List<String> videos = getYoutubeVideosFromSpecificChannel(audioGeneratorRequest);
            handleAudioGeneration(videos);
            return new AudioGeneratorResponse("Audios processed successfully");
        } catch (IOException | InterruptedException e) {
            return new AudioGeneratorResponse("Error while processing audio chunks: " + e.getMessage());
        }
    }

    private List<String> getYoutubeVideosFromSpecificChannel(AudioGeneratorRequest audioGeneratorRequest) throws IOException {
        return youTubeService.getChannelVideos(audioGeneratorRequest.getChannelId());
    }

    private void handleAudioGeneration(List<String> videos) throws IOException, InterruptedException {
        for (String videoUrl : videos) {
            String videoId = urlExtractorService.getVideoId(videoUrl);
            if (couldCreateBaseFolder(videoId)) {
                audioExecutionService.handleAudioExecution(videoUrl, videoId);
            }
        }
    }

    private boolean couldCreateBaseFolder(String videoId) throws IOException, InterruptedException {
        return audioBaseFolderCreatorService.createBaseFolder(videoId);
    }

}
