package com.gabriel.audio_generator_service.application.service.audio.audio_download;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AudioDownloadService {

    private final UrlGenerator urlGenerator;
    private final ProcessBuilderSyncCommandRunner processBuilderSyncCommandRunner;

    public AudioDownloadService(UrlGenerator urlGenerator, ProcessBuilderSyncCommandRunner processBuilderSyncCommandRunner) {
        this.urlGenerator = urlGenerator;
        this.processBuilderSyncCommandRunner = processBuilderSyncCommandRunner;
    }

    public boolean downloadAudio(String videoUrl, String videoId) throws IOException, InterruptedException {
        processBuilderSyncCommandRunner.directory(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId));
        processBuilderSyncCommandRunner.command(
                "yt-dlp",
                "-f", "bestaudio",
                "-x",
                "--audio-format", "wav",
                "-o", videoId + ".%(ext)s",
                videoUrl
        );
        CommandResult downloadResult = processBuilderSyncCommandRunner.execute();
        return downloadResult.getExitCode() == 0;
    }
}
