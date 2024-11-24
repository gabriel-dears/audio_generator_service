package com.gabriel.audio_generator_service.application.command_runner;

import com.gabriel.audio_generator_service.application.process.ProcessExecutor;
import com.gabriel.audio_generator_service.application.process.ProcessHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProcessBuilderSyncCommandRunner extends BaseCommandRunner implements SyncCommandRunner {
    private String[] command;
    private File directory;

    public ProcessBuilderSyncCommandRunner(ProcessExecutor processExecutor) {
        super(processExecutor);
    }

    @Override
    public CommandRunner command(String... command) {
        this.command = command;
        return this;
    }

    @Override
    public CommandRunner directory(File directory) {
        this.directory = directory;
        return this;
    }

    @Override
    public CommandResult execute() throws IOException, InterruptedException {
        Process process = getProcess(command, directory);
        return ProcessHandler.handleProcess(process);
    }

}
