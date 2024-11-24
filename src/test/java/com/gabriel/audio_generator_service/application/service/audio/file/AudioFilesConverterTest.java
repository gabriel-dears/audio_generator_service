package com.gabriel.audio_generator_service.application.service.audio.file;

import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import com.gabriel.audio_generator_service.infrastructure.utils.FileConverter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioFilesConverterTest {

    private static MockedStatic<FileConverter> fileConverterMock;

    private AudioFilesConverter audioFilesConverter;

    @Mock
    private UrlGenerator urlGenerator;

    @BeforeAll
    static void setUpStaticMock() {
        // Initialize static mock once for all tests
        fileConverterMock = Mockito.mockStatic(FileConverter.class);
    }

    @AfterAll
    static void tearDownStaticMock() {
        // Close the static mock after all tests
        fileConverterMock.close();
    }

    @BeforeEach
    void setUp() {
        audioFilesConverter = new AudioFilesConverter(urlGenerator);
    }

    @Test
    void shouldReturnFileArrayWhenValidVideoIdIsProvided() {
        // Given
        String videoId = "sampleVideoId";
        File mockFile = mock(File.class);
        File[] expectedFiles = {mockFile, mockFile};

        // Mock UrlGenerator behavior
        File clipsDirectory = mock(File.class);
        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId)).thenReturn(clipsDirectory);

        // Mock FileConverter behavior
        fileConverterMock.when(() -> FileConverter.getAsFileArray(clipsDirectory, videoId)).thenReturn(expectedFiles);

        // When
        File[] actualFiles = audioFilesConverter.getAsFileArray(videoId);

        // Then
        assertThat(actualFiles).isNotNull();
        assertThat(actualFiles).hasSize(2);
        assertThat(actualFiles).containsExactly(mockFile, mockFile);

        // Verify interactions
        verify(urlGenerator, times(1)).getClipsUrlBasedOnVideoIdAsFile(videoId);
    }

    @Test
    void shouldReturnEmptyArrayWhenNoFilesExist() {
        // Given
        String videoId = "emptyVideoId";

        // Mock UrlGenerator behavior
        File clipsDirectory = mock(File.class);
        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId)).thenReturn(clipsDirectory);

        // Mock FileConverter behavior
        fileConverterMock.when(() -> FileConverter.getAsFileArray(clipsDirectory, videoId)).thenReturn(new File[0]);

        // When
        File[] actualFiles = audioFilesConverter.getAsFileArray(videoId);

        // Then
        assertThat(actualFiles).isNotNull();
        assertThat(actualFiles).isEmpty();

        // Verify interactions
        verify(urlGenerator, times(1)).getClipsUrlBasedOnVideoIdAsFile(videoId);
    }
}
