package com.gabriel.audio_generator_service.application.service.audio.audio_splitting;

import com.gabriel.audio_generator_service.application.command_runner.AsyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderAsyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class AudioSplittingService {

    private final UrlGenerator urlGenerator;

    public AudioSplittingService(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    public CompletableFuture<CommandResult> splitAudio(String videoId, String inputFileName, String outputPattern) throws IOException, InterruptedException {
        return executeSplitting(videoId, inputFileName, outputPattern);
    }

    private CompletableFuture<CommandResult> executeSplitting(String videoId, String inputFileName, String outputPattern) {
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
}
