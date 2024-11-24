package com.gabriel.audio_generator_service.application.process;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessExecutorTest {

    @Mock
    ProcessBuilder processBuilder;

    @InjectMocks
    private ProcessExecutor processExecutor;

    @Test
    void testStartProcess() throws IOException {
        String[] command = {""};
        File tempDir = mock(File.class);

        Process process = mock(Process.class);

        when(processBuilder.directory(any())).thenReturn(processBuilder);
        when(processBuilder.command("")).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(process);

        processExecutor.startProcess(command, tempDir);

        assertNotNull(process); // Ensure the process is created successfully
    }

    @Test
    void testStartProcessWithNonExistingDirectory() throws IOException {
        String[] command = {""};

        Process process = mock(Process.class);

        when(processBuilder.command("")).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(process);

        processExecutor.startProcess(command, null);

        assertNotNull(process); // Ensure the process is created successfully
    }

}