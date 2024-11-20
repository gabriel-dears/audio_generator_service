package com.gabriel.audio_generator_service.application.service.audio.audio_splitting;

import com.gabriel.audio_generator_service.application.command_runner.AsyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderAsyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.messaging.audio.AudioMessageProducer;
import com.gabriel.audio_generator_service.infrastructure.utils.UrlGenerator;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AudioSplittingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioSplittingService.class);

    private final AudioMessageProducer audioMessageProducer;
    private final UrlGenerator urlGenerator;

    public AudioSplittingService(AudioMessageProducer audioMessageProducer, UrlGenerator urlGenerator) {
        this.audioMessageProducer = audioMessageProducer;
        this.urlGenerator = urlGenerator;
    }

    public void splitAudio(String videoId, String inputFileName, String outputPattern) throws IOException, InterruptedException {
        CompletableFuture<CommandResult> commandResultCompletableFuture = executeSplitting(videoId, inputFileName, outputPattern);
        handleProcessingAfterSplitting(videoId, outputPattern, commandResultCompletableFuture);
    }

    private void handleProcessingAfterSplitting(String videoId, String outputPattern, CompletableFuture<CommandResult> commandResultCompletableFuture) {
        commandResultCompletableFuture.thenRun(() -> handlePartitionedAudios(videoId, outputPattern));
    }

    private void handlePartitionedAudios(String videoId, String outputPattern) {
        try {
            sendAudiosToTheQueue(videoId, outputPattern);
        } catch (IOException e) {
            LOGGER.error("Error while splitting {} audios and sending to the queue: {}", videoId, e.getMessage(), e);
        }
    }

    private CompletableFuture<CommandResult> executeSplitting(String videoId, String inputFileName, String outputPattern) throws IOException, InterruptedException {
        AsyncCommandRunner asyncCommandRunner = new ProcessBuilderAsyncCommandRunner();
        asyncCommandRunner.directory(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId));
        asyncCommandRunner.command(
                "ffmpeg",
                "-i", inputFileName,
                "-f", "segment",
                "-segment_time", "30",
                "-c", "copy",
                "-reset_timestamps", "1",
                outputPattern
        );

        return asyncCommandRunner.executeAsync();
    }

    private void sendAudiosToTheQueue(String videoId, String outputPattern) throws IOException {
        // Collect all chunk files based on the output pattern
        File clipsFolder = new File(urlGenerator.getClipsUrlBasedOnVideoId(videoId));
        if (clipsFolder.isDirectory()) {
            Optional<File[]> optChunkFiles = getAudiosAsChunkFiles(outputPattern, clipsFolder);
            if (optChunkFiles.isPresent()) {
                iterateOverChunkFiles(optChunkFiles.get());
            }
        }
    }

    private void iterateOverChunkFiles(File[] chunkFiles) throws IOException {
        for (File chunkFile : chunkFiles) {
            sendAudioToTheQueue(chunkFile);
        }
    }

    private static Optional<File[]> getAudiosAsChunkFiles(String outputPattern, File clipsFolder) {
        // Regex for matching file pattern
        return Optional.ofNullable(clipsFolder.listFiles((dir, name) -> name.matches(outputPattern.replace("%03d", "\\d{3}"))));
    }

    private void sendAudioToTheQueue(File chunkFile) throws IOException {
        String base64Chunk = fileToBase64(chunkFile);
        audioMessageProducer.sendMessage(base64Chunk);
    }

    private String fileToBase64(File file) throws IOException {
        byte[] fileBytes = FileUtils.readFileToByteArray(file);
        return Base64.getEncoder().encodeToString(fileBytes);
    }
}
