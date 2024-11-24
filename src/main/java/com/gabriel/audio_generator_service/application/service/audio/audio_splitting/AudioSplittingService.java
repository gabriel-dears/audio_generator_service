package com.gabriel.audio_generator_service.application.service.audio.audio_splitting;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderAsyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AudioSplittingService {

    private final UrlGenerator urlGenerator;
    private final ProcessBuilderAsyncCommandRunner processBuilderAsyncCommandRunner;

    public AudioSplittingService(UrlGenerator urlGenerator, ProcessBuilderAsyncCommandRunner processBuilderAsyncCommandRunner) {
        this.urlGenerator = urlGenerator;
        this.processBuilderAsyncCommandRunner = processBuilderAsyncCommandRunner;
    }

    public CompletableFuture<CommandResult> splitAudio(String videoId, String inputFileName, String outputPattern) {
        return executeSplitting(videoId, inputFileName, outputPattern);
    }

    private CompletableFuture<CommandResult> executeSplitting(String videoId, String inputFileName, String outputPattern) {
        processBuilderAsyncCommandRunner.directory(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId));
        processBuilderAsyncCommandRunner.command(
                "ffmpeg",
                "-i", inputFileName,
                "-f", "segment",
                "-segment_time", "30",
                "-c", "copy",
                "-reset_timestamps", "1",
                outputPattern
        );

        return processBuilderAsyncCommandRunner.executeAsync();
    }
}
