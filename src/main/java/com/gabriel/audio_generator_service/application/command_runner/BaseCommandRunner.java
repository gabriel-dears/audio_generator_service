package com.gabriel.audio_generator_service.application.command_runner;

import com.gabriel.audio_generator_service.application.process.ProcessExecutor;

import java.io.File;
import java.io.IOException;

public abstract class BaseCommandRunner implements CommandRunner {

    private final ProcessExecutor processExecutor;

    BaseCommandRunner(ProcessExecutor processExecutor) {
        this.processExecutor = processExecutor;
    }

    Process getProcess(String[] command, File workingDirectory) throws IOException {
        return processExecutor.startProcess(command, workingDirectory);
    }

}
