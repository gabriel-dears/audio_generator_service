package com.gabriel.clipcrafter.application.service.audio.audio_download;

import com.gabriel.clipcrafter.application.command_runner.CommandResult;
import com.gabriel.clipcrafter.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.clipcrafter.application.command_runner.SyncCommandRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class AudioDownloadService {

    private final String downloadDirectory;

    public AudioDownloadService(@Value("${CLIPS_DIR}") String downloadDirectory) {
        this.downloadDirectory = downloadDirectory;
    }

    public boolean downloadAudio(String videoUrl, String videoId) throws IOException, InterruptedException {
        SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();
        syncCommandRunner.directory(new File(downloadDirectory));
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
