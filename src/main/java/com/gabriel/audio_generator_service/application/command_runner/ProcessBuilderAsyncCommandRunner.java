package com.gabriel.audio_generator_service.application.command_runner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ProcessBuilderAsyncCommandRunner implements AsyncCommandRunner {
    private String[] command;
    private File directory;

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
                Process process = getProcess();

                int exitCode = process.waitFor();
                String output = new String(process.getInputStream().readAllBytes());
                String errorOutput = new String(process.getErrorStream().readAllBytes());

                return new CommandResult(exitCode, output, errorOutput);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return new CommandResult(-1, "", e.getMessage());
            }
        });
    }

    private Process getProcess() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        if (directory != null) {
            processBuilder.directory(directory);
        }
        processBuilder.inheritIO();
        return processBuilder.start();
    }
}

