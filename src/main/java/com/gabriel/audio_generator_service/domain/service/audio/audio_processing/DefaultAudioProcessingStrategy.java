package com.gabriel.audio_generator_service.domain.service.audio.audio_processing;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.SyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.AudioProcessingStrategy;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DefaultAudioProcessingStrategy implements AudioProcessingStrategy {

    @Value("${audio.ffmpeg.command}")
    private String ffmpegCommand;

    @Value("${audio.sample-rate}")
    private int sampleRate;

    @Value("${audio.channels}")
    private int channels;

    @Value("${audio.audio-encoding}")
    private String audioEncoding;

    @Value("${audio.audio-format}")
    private String audioFormat;

    private final UrlGenerator urlGenerator;

    public DefaultAudioProcessingStrategy(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    @Override
    public boolean processAudio(String videoId) throws IOException, InterruptedException {
        SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();
        syncCommandRunner.directory(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId));

        String inputAudioFile = videoId + ".wav";
        String outputAudioFile = videoId + "_processed.wav";

        syncCommandRunner.command(
                ffmpegCommand,
                "-i", inputAudioFile,
                "-ar", String.valueOf(sampleRate),
                "-ac", String.valueOf(channels),
                "-c:a", audioEncoding,
                "-filter:a", audioFormat,
                "-q:a", "0",
                "-map", "a",
                outputAudioFile,
                "-y"
        );

        CommandResult commandResult = syncCommandRunner.execute();
        return commandResult.getExitCode() == 0;
    }
}
