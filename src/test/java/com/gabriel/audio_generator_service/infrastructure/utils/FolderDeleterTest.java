package com.gabriel.audio_generator_service.infrastructure.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.mockito.Mockito.*;

class FolderDeleterTest {

    @Test
    void shouldNotDeleteNonExistentFolder() {
        // Arrange
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);

        // Act
        FolderDeleter.deleteFolder(mockFolder);

        // Assert
        verify(mockFolder, never()).delete();
    }

    @Test
    void shouldDeleteEmptyFolder() {
        // Arrange
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(true);
        when(mockFolder.isDirectory()).thenReturn(true);
        when(mockFolder.listFiles()).thenReturn(new File[0]);
        when(mockFolder.delete()).thenReturn(true);

        // Act
        FolderDeleter.deleteFolder(mockFolder);

        // Assert
        verify(mockFolder).delete();
    }

    @Test
    void shouldDeleteFolderWithFiles() {
        // Arrange
        File mockFolder = mock(File.class);
        File mockFile = mock(File.class);
        File[] mockFiles = new File[]{mockFile};

        when(mockFolder.exists()).thenReturn(true);
        when(mockFolder.isDirectory()).thenReturn(true);
        when(mockFolder.listFiles()).thenReturn(mockFiles);
        when(mockFolder.delete()).thenReturn(true);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isDirectory()).thenReturn(false);
        when(mockFile.delete()).thenReturn(true);

        // Act
        FolderDeleter.deleteFolder(mockFolder);

        // Assert
        verify(mockFolder).delete();
        verify(mockFile).delete();
    }

    @Test
    void shouldHandleIOExceptionWhenDeleting() {
        // Arrange
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(true);
        when(mockFolder.isDirectory()).thenReturn(true);
        File mockFile = mock(File.class);
        when(mockFolder.listFiles()).thenReturn(new File[]{mockFile});
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.isDirectory()).thenReturn(false);
        when(mockFile.delete()).thenReturn(false);

        // Act
        FolderDeleter.deleteFolder(mockFolder);

        // Assert
        verify(mockFolder).delete();
        verify(mockFile).delete();
    }

    @Test
    void shouldNotDeleteFolderIfDirectoryIsNull() {
        // Arrange
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(true);
        when(mockFolder.isDirectory()).thenReturn(true);
        when(mockFolder.listFiles()).thenReturn(null);

        // Act
        FolderDeleter.deleteFolder(mockFolder);

        // Assert
        verify(mockFolder).delete();
    }
}
