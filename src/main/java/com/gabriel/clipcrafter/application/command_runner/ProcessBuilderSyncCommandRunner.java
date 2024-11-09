package com.gabriel.clipcrafter.application.command_runner;

import java.io.File;
import java.io.IOException;

public class ProcessBuilderSyncCommandRunner implements SyncCommandRunner {
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
    public CommandResult execute() throws IOException, InterruptedException {
        Process process = getProcess();
        int exitCode = process.waitFor();
        String output = new String(process.getInputStream().readAllBytes());
        String errorOutput = new String(process.getErrorStream().readAllBytes());

        return new CommandResult(exitCode, output, errorOutput);
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
