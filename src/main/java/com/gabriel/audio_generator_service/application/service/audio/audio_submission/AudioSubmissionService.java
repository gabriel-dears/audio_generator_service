package com.gabriel.audio_generator_service.application.service.audio.audio_submission;

import com.gabriel.audio_generator_service.application.service.AudioMessageProducerStrategy;
import com.gabriel.audio_generator_service.application.service.audio.file.AudioFilesConverter;
import com.gabriel.audio_generator_service.domain.model.VideoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AudioSubmissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioSubmissionService.class);
    private final AudioFilesConverter audioFilesConverter;
    private final AudioMessageProducerStrategy audioMessageProducerStrategy;

    public AudioSubmissionService(AudioFilesConverter audioFilesConverter, AudioMessageProducerStrategy audioMessageProducerStrategy) {
        this.audioFilesConverter = audioFilesConverter;
        this.audioMessageProducerStrategy = audioMessageProducerStrategy;
    }

    public void submitAudio(VideoDetails videoDetails) {
        handlePartitionedAudios(videoDetails);
    }

    private void handlePartitionedAudios(VideoDetails videoDetails) {
        try {
            handleSubmissionProcess(videoDetails);
        } catch (IOException e) {
            LOGGER.error("Error while splitting {} audios and sending to the queue: {}", videoDetails.videoId(), e.getMessage(), e);
        }
    }

    private void handleSubmissionProcess(VideoDetails videoDetails) throws IOException {
        File[] chunkFiles = getAudioChunksAsFiles(videoDetails.videoId());
        sendAudiosChunksToTheQueue(chunkFiles, videoDetails);
    }

    private void sendAudiosChunksToTheQueue(File[] chunkFiles, VideoDetails videoDetails) throws IOException {
        for (File chunkFile : chunkFiles) {
            sendAudioToTheQueue(chunkFile, videoDetails);
        }
    }

    private File[] getAudioChunksAsFiles(String videoId) {
        return audioFilesConverter.getAsFileArray(videoId);
    }

    private void sendAudioToTheQueue(File chunkFile, VideoDetails videoDetails) throws IOException {
        audioMessageProducerStrategy.sendMessage(videoDetails, chunkFile);
    }

}
