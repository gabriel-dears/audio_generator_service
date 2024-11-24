package com.gabriel.audio_generator_service.application.command_runner.folder.folder_creator;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FolderCreatorServiceImpl implements FolderCreatorService {

    private final ProcessBuilderSyncCommandRunner processBuilderSyncCommandRunner;

    private String folderName;

    public FolderCreatorServiceImpl(ProcessBuilderSyncCommandRunner processBuilderSyncCommandRunner) {
        this.processBuilderSyncCommandRunner = processBuilderSyncCommandRunner;
    }

    @Override
    public FolderCreatorService setBaseDirectory(File baseDirectory) {
        processBuilderSyncCommandRunner.directory(baseDirectory);
        return this;
    }

    @Override
    public FolderCreatorService setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    @Override
    public CommandResult run() throws IOException, InterruptedException {
        processBuilderSyncCommandRunner.command("mkdir", "-p", folderName);
        return processBuilderSyncCommandRunner.execute();
    }

}
