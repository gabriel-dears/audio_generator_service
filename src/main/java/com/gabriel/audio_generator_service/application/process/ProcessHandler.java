package com.gabriel.audio_generator_service.application.process;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;

import java.io.IOException;

public class ProcessHandler {

    public static CommandResult handleProcess(Process process) throws IOException, InterruptedException {
        int exitCode = process.waitFor();
        String output = new String(process.getInputStream().readAllBytes());
        String errorOutput = new String(process.getErrorStream().readAllBytes());
        return new CommandResult(exitCode, output, errorOutput);
    }
}
