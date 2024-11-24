package com.gabriel.audio_generator_service.application.command_runner;

import com.gabriel.audio_generator_service.application.process.ProcessExecutor;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessBuilderSyncCommandRunnerTest {

    private final ProcessBuilderSyncCommandRunner builderSyncCommandRunner = new ProcessBuilderSyncCommandRunner(new ProcessExecutor(new ProcessBuilder()));

    @Test
    void testCommandSetsCommandArray() {
        builderSyncCommandRunner.command("echo", "hello");

        CommandRunner commandRunner = builderSyncCommandRunner.command("echo", "test");

        assertEquals(builderSyncCommandRunner, commandRunner); // Verify method chaining
    }

    @Test
    void testDirectorySetsWorkingDirectory() {
        File testDirectory = new File("/tmp");

        CommandRunner commandRunner = builderSyncCommandRunner.directory(testDirectory);

        assertEquals(builderSyncCommandRunner, commandRunner); // Verify method chaining
    }

}
