package com.gabriel.audio_generator_service.application.service.audio.audio_submission;

import com.gabriel.audio_generator_service.application.service.AudioMessageProducerStrategy;
import com.gabriel.audio_generator_service.application.service.audio.file.AudioFilesConverter;
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

    public void submitAudio(String videoId, String channelId) {
        handlePartitionedAudios(videoId, channelId);
    }

    private void handlePartitionedAudios(String videoId, String channelId) {
        try {
            handleSubmissionProcess(videoId, channelId);
        } catch (IOException e) {
            LOGGER.error("Error while splitting {} audios and sending to the queue: {}", videoId, e.getMessage(), e);
        }
    }

    private void handleSubmissionProcess(String videoId, String channelId) throws IOException {
        File[] chunkFiles = getAudioChunksAsFiles(videoId);
        sendAudiosChunksToTheQueue(chunkFiles, videoId, channelId);
    }

    private void sendAudiosChunksToTheQueue(File[] chunkFiles, String videoId, String channelId) throws IOException {
        for (File chunkFile : chunkFiles) {
            sendAudioToTheQueue(chunkFile, videoId, channelId);
        }
    }

    private File[] getAudioChunksAsFiles(String videoId) {
        return audioFilesConverter.getAsFileArray(videoId);
    }

    private void sendAudioToTheQueue(File chunkFile, String videoId, String channelId) throws IOException {
        audioMessageProducerStrategy.sendMessage(channelId, videoId, chunkFile);
    }

}
