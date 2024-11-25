package com.gabriel.audio_generator_service.application.facade;

import com.gabriel.audio_generator_service.application.dto.AudioGeneratorRequest;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorResponse;
import com.gabriel.audio_generator_service.application.service.audio.audio_base_folder_creator.AudioBaseFolderCreatorService;
import com.gabriel.audio_generator_service.application.service.audio.audio_execution.AudioExecutionService;
import com.gabriel.audio_generator_service.application.service.url.url_extractor.UrlExtractorService;
import com.gabriel.audio_generator_service.application.service.youtube.YouTubeService;
import com.gabriel.audio_generator_service.domain.model.VideoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            List<String> videosIds = fetchVideosIds(channelId);
            List<VideoDetails> videosDetails = youTubeService.getVideosDetails(videosIds);
            processVideos(videosDetails);
            LOGGER.info("Audio generation process completed successfully.");
            return new AudioGeneratorResponse("Audios processed successfully");
        } catch (Exception e) {
            LOGGER.error("Error during audio generation: {}", e.getMessage(), e);
            return new AudioGeneratorResponse("Error while processing audio chunks: " + e.getMessage());
        }
    }

    private List<String> fetchVideosIds(String channelId) throws IOException {
        LOGGER.info("Fetching video URLs for channel ID: {}", channelId);
        return youTubeService.getChannelVideosIds(channelId);
    }

    private void processVideos(List<VideoDetails> videoDetailsList) throws IOException, InterruptedException {
        for (VideoDetails videoDetails : videoDetailsList) {
            processSingleVideo(videoDetails);
        }
    }

    private void processSingleVideo(VideoDetails videoDetails) throws IOException, InterruptedException {
        if (isBaseFolderCreated(videoDetails.videoId())) {
            LOGGER.info("Processing audio for video ID: {}", videoDetails);
            audioExecutionService.handleAudioExecution(videoDetails);
        } else {
            LOGGER.warn("Skipping audio processing for video ID: {} - Base folder creation failed.", videoDetails);
        }
    }

    private boolean isBaseFolderCreated(String videoId) throws IOException, InterruptedException {
        LOGGER.info("Creating base folder for video ID: {}", videoId);
        return audioBaseFolderCreatorService.createBaseFolder(videoId);
    }
}
