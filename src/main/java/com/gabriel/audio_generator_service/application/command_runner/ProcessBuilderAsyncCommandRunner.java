package com.gabriel.audio_generator_service.application.command_runner;

import com.gabriel.audio_generator_service.application.process.ProcessExecutor;
import com.gabriel.audio_generator_service.application.process.ProcessHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class ProcessBuilderAsyncCommandRunner extends BaseCommandRunner implements AsyncCommandRunner {
    private String[] command;
    private File directory;

    ProcessBuilderAsyncCommandRunner(ProcessExecutor processExecutor) {
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
    public CompletableFuture<CommandResult> executeAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Process process = getProcess(command, directory);
                return ProcessHandler.handleProcess(process);
            } catch (IOException | InterruptedException e) {
                return new CommandResult(-1, "", e.getMessage());
            }
        });
    }

}

