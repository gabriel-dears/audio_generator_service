package com.gabriel.audio_generator_service.application.service.audio.audio_processing;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.SyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.AudioProcessingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class DefaultAudioProcessingStrategy implements AudioProcessingStrategy {

    @Value("${audio.ffmpeg.command:ffmpeg}")
    private String ffmpegCommand;

    @Value("${audio.sampleRate:16000}")
    private int sampleRate;

    @Value("${audio.channels:1}")
    private int channels;

    @Value("${audio.audioEncoding:pcm_s16le}")
    private String audioEncoding;

    @Value("${audio.audioFormat:loudnorm}")
    private String audioFormat;

    @Value("${CLIPS_DIR}")
    private String CLIPS_DIR;

    @Override
    public boolean processAudio(String videoId) throws IOException, InterruptedException {
        SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();
        syncCommandRunner.directory(new File(CLIPS_DIR));

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
