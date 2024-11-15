package com.gabriel.audio_generator_service.application.service.audio.audio_splitting;

import com.gabriel.audio_generator_service.application.command_runner.AsyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderAsyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.messaging.audio.AudioMessageProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AudioSplittingService {

    @Value("${CLIPS_DIR}")
    private String CLIPS_DIR;

    private final AudioMessageProducer audioMessageProducer;

    public AudioSplittingService(AudioMessageProducer audioMessageProducer) {
        this.audioMessageProducer = audioMessageProducer;
    }

    public void splitAudio(String audioFilePath, String outputPattern) throws IOException, InterruptedException {
        // Initialize ProcessBuilder
        AsyncCommandRunner asyncCommandRunner = new ProcessBuilderAsyncCommandRunner();
        asyncCommandRunner.directory(new File(CLIPS_DIR));

        // Command to split audio
        asyncCommandRunner.command(
                "ffmpeg",
                "-i", audioFilePath,                    // Input audio file
                "-f", "segment",                        // Set output format to segment
                "-segment_time", "30",                  // Set maximum segment duration to 30 seconds
                "-c", "copy",                           // Copy codec without re-encoding
                "-reset_timestamps", "1",               // Reset timestamps for each segment
                outputPattern                           // Output naming pattern, e.g., "output%03d.wav"
        );

        // Execute command and return exit code
        asyncCommandRunner.executeAsync();

        audioMessageProducer.sendMessage("message");

    }
}
