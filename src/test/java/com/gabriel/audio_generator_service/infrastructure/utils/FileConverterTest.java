package com.gabriel.audio_generator_service.infrastructure.utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FileConverterTest {

    @Test
    void shouldConvertFileToBase64() throws IOException {
        // Arrange
        File mockFile = mock(File.class);
        byte[] fileBytes = "test content".getBytes();

        // Use mockStatic to mock the static method call
        try (var mockedFileUtils = Mockito.mockStatic(FileUtils.class)) {
            mockedFileUtils.when(() -> FileUtils.readFileToByteArray(mockFile)).thenReturn(fileBytes);

            // Act
            String base64String = FileConverter.fileToBase64(mockFile);

            // Assert
            String expectedBase64 = Base64.getEncoder().encodeToString(fileBytes);
            assertThat(base64String).isEqualTo(expectedBase64);
        }
    }

    @Test
    void shouldReturnEmptyFileArrayWhenNotADirectory() {
        // Arrange
        File mockFile = mock(File.class);
        when(mockFile.isDirectory()).thenReturn(false);

        // Act
        File[] files = FileConverter.getAsFileArray(mockFile, "testVideo");

        // Assert
        assertThat(files).isEmpty();
    }

    @Test
    void shouldReturnEmptyFileArrayWhenDirectoryExists() {
        // Arrange
        File mockFile = mock(File.class);
        when(mockFile.isDirectory()).thenReturn(true);

        // Act
        File[] files = FileConverter.getAsFileArray(mockFile, "testVideo");

        // Assert
        assertThat(files).isEmpty();
    }

}
