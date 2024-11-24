package com.gabriel.audio_generator_service.application.process;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessHandlerTest {

    @Test
    void testHandleProcess() throws IOException, InterruptedException {
        Process process = mock(Process.class);
        when(process.waitFor()).thenReturn(0);
        when(process.getErrorStream()).thenReturn(new ByteArrayInputStream(new byte[10]));
        when(process.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[10]));
        CommandResult result = ProcessHandler.handleProcess(process);
        assertEquals(0, result.getExitCode());
    }
}
