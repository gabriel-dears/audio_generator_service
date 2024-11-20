package com.gabriel.audio_generator_service.application.command_runner.folder.folder_creator;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public interface FolderCreatorService {

    FolderCreatorService setBaseDirectory(File baseDirectory);
    FolderCreatorService setFolderName(String folderName);
    CommandResult run() throws IOException, InterruptedException;

}
