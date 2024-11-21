package com.gabriel.audio_generator_service.application.service.audio.audio_base_folder_creator;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.folder.folder_creator.FolderCreatorService;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AudioBaseFolderCreatorServiceTest {

    @Mock
    private FolderCreatorService folderCreatorService;

    @Mock
    private UrlGenerator urlGenerator;

    private AudioBaseFolderCreatorService audioBaseFolderCreatorService;

    @BeforeEach
    void setUp() {
        audioBaseFolderCreatorService = new AudioBaseFolderCreatorService(folderCreatorService, urlGenerator);
    }

    @Test
    void createBaseFolder_shouldReturnTrueWhenExitCodeIsZero() throws IOException, InterruptedException {
        // Arrange
        String videoId = "12345";
        File mockBaseDir = new File("/mock/base/dir");
        when(urlGenerator.getBaseUrlAsFile()).thenReturn(mockBaseDir);
        when(folderCreatorService.setBaseDirectory(mockBaseDir)).thenReturn(folderCreatorService);
        when(folderCreatorService.setFolderName("clips_" + videoId)).thenReturn(folderCreatorService);
        when(folderCreatorService.run()).thenReturn(new CommandResult(0, "", ""));

        // Act
        boolean result = audioBaseFolderCreatorService.createBaseFolder(videoId);

        // Assert
        assertTrue(result);
        verify(urlGenerator).getBaseUrlAsFile();
        verify(folderCreatorService).setBaseDirectory(mockBaseDir);
        verify(folderCreatorService).setFolderName("clips_" + videoId);
        verify(folderCreatorService).run();
    }

    @Test
    void createBaseFolder_shouldReturnFalseWhenExitCodeIsNotZero() throws IOException, InterruptedException {
        // Arrange
        String videoId = "12345";
        File mockBaseDir = new File("/mock/base/dir");
        when(urlGenerator.getBaseUrlAsFile()).thenReturn(mockBaseDir);
        when(folderCreatorService.setBaseDirectory(mockBaseDir)).thenReturn(folderCreatorService);
        when(folderCreatorService.setFolderName("clips_" + videoId)).thenReturn(folderCreatorService);
        when(folderCreatorService.run()).thenReturn(new CommandResult(1, "", "Error creating folder"));

        // Act
        boolean result = audioBaseFolderCreatorService.createBaseFolder(videoId);

        // Assert
        assertFalse(result);
        verify(urlGenerator).getBaseUrlAsFile();
        verify(folderCreatorService).setBaseDirectory(mockBaseDir);
        verify(folderCreatorService).setFolderName("clips_" + videoId);
        verify(folderCreatorService).run();
    }
}
