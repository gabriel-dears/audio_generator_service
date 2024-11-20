package com.gabriel.audio_generator_service.application.service.audio.audio_base_folder_creator;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.folder.folder_creator.FolderCreatorService;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AudioBaseFolderCreatorService {

    private final FolderCreatorService folderCreatorService;
    private final UrlGenerator urlGenerator;

    public AudioBaseFolderCreatorService(FolderCreatorService folderCreatorService, UrlGenerator urlGenerator) {
        this.folderCreatorService = folderCreatorService;
        this.urlGenerator = urlGenerator;
    }

    public boolean createBaseFolder(String videoId) throws IOException, InterruptedException {
        CommandResult run = folderCreatorService
                .setBaseDirectory(urlGenerator.getBaseUrlAsFile())
                .setFolderName("clips_" + videoId)
                .run();
        return run.getExitCode() == 0;
    }

}
