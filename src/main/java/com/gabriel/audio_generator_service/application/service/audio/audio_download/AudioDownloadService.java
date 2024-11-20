package com.gabriel.audio_generator_service.application.service.audio.audio_download;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.SyncCommandRunner;
import com.gabriel.audio_generator_service.infrastructure.utils.UrlGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AudioDownloadService {

    private final UrlGenerator urlGenerator;

    public AudioDownloadService(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    public boolean downloadAudio(String videoUrl, String videoId) throws IOException, InterruptedException {
        SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();
        syncCommandRunner.directory(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId));
        syncCommandRunner.command(
                "yt-dlp",
                "-f", "bestaudio",
                "-x",
                "--audio-format", "wav",
                "-o", videoId + ".%(ext)s",
                videoUrl
        );
        CommandResult downloadResult = syncCommandRunner.execute();
        return downloadResult.getExitCode() == 0;
    }
}
