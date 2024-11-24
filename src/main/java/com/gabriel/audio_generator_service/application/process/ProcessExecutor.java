package com.gabriel.audio_generator_service.application.process;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProcessExecutor {

    private final ProcessBuilder processBuilder;

    public ProcessExecutor(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    public Process startProcess(String[] command, File directory) throws IOException {
        processBuilder.command(command);
        if (directory != null) {
            processBuilder.directory(directory);
        }
        processBuilder.inheritIO();
        return processBuilder.start();
    }

}
