package com.gabriel.audio_generator_service.application.command_runner.folder.folder_creator;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.command_runner.SyncCommandRunner;

import java.io.File;
import java.io.IOException;

public class FolderCreatorServiceImpl implements FolderCreatorService {

    private final SyncCommandRunner syncCommandRunner = new ProcessBuilderSyncCommandRunner();

    private String folderName;

    @Override
    public FolderCreatorService setBaseDirectory(File baseDirectory) {
        syncCommandRunner.directory(baseDirectory);
        return this;
    }

    @Override
    public FolderCreatorService setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    @Override
    public CommandResult run() throws IOException, InterruptedException {
        syncCommandRunner.command("mkdir", "-p", folderName);
        return syncCommandRunner.execute();
    }

}
