package com.gabriel.audio_generator_service.application.facade;

import com.gabriel.audio_generator_service.application.dto.AudioGeneratorRequest;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorResponse;
import com.gabriel.audio_generator_service.application.service.audio.audio_base_folder_creator.AudioBaseFolderCreatorService;
import com.gabriel.audio_generator_service.application.service.audio.audio_execution.AudioExecutionService;
import com.gabriel.audio_generator_service.application.service.url.url_extractor.UrlExtractorService;
import com.gabriel.audio_generator_service.application.service.youtube.YouTubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class AudioGenerationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioGenerationFacade.class);

    private final YouTubeService youTubeService;
    private final AudioExecutionService audioExecutionService;
    private final UrlExtractorService urlExtractorService;
    private final AudioBaseFolderCreatorService audioBaseFolderCreatorService;

    public AudioGenerationFacade(
            YouTubeService youTubeService,
            AudioExecutionService audioExecutionService,
            UrlExtractorService urlExtractorService,
            AudioBaseFolderCreatorService audioBaseFolderCreatorService
    ) {
        this.youTubeService = youTubeService;
        this.audioExecutionService = audioExecutionService;
        this.urlExtractorService = urlExtractorService;
        this.audioBaseFolderCreatorService = audioBaseFolderCreatorService;
    }

    public AudioGeneratorResponse executeAudioGeneration(AudioGeneratorRequest audioGeneratorRequest) {
        try {
            String channelId = audioGeneratorRequest.getChannelId();
            LOGGER.info("Starting audio generation process for channel: {}", channelId);
            List<String> videoUrls = fetchVideoUrls(channelId);
            processVideos(videoUrls, channelId);
            LOGGER.info("Audio generation process completed successfully.");
            return new AudioGeneratorResponse("Audios processed successfully");
        } catch (Exception e) {
            LOGGER.error("Error during audio generation: {}", e.getMessage(), e);
            return new AudioGeneratorResponse("Error while processing audio chunks: " + e.getMessage());
        }
    }

    private List<String> fetchVideoUrls(String channelId) throws IOException {
        LOGGER.info("Fetching video URLs for channel ID: {}", channelId);
        return youTubeService.getChannelVideos(channelId);
    }

    private void processVideos(List<String> videoUrls, String channelId) throws IOException, InterruptedException, URISyntaxException {
        for (String videoUrl : videoUrls) {
            processSingleVideo(videoUrl, channelId);
        }
    }

    private void processSingleVideo(String videoUrl, String channelId) throws IOException, InterruptedException, URISyntaxException {
        String videoId = extractVideoId(videoUrl);
        if (isBaseFolderCreated(videoId)) {
            LOGGER.info("Processing audio for video ID: {}", videoId);
            audioExecutionService.handleAudioExecution(videoUrl, videoId, channelId);
        } else {
            LOGGER.warn("Skipping audio processing for video ID: {} - Base folder creation failed.", videoId);
        }
    }

    private String extractVideoId(String videoUrl) throws URISyntaxException {
        LOGGER.info("Extracting video ID from URL: {}", videoUrl);
        return urlExtractorService.getVideoId(videoUrl);
    }

    private boolean isBaseFolderCreated(String videoId) throws IOException, InterruptedException {
        LOGGER.info("Creating base folder for video ID: {}", videoId);
        return audioBaseFolderCreatorService.createBaseFolder(videoId);
    }
}
