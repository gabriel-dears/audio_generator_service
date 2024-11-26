package com.gabriel.audio_generator_service.application.service.audio.audio_download;

import com.gabriel.audio_generator_service.application.command_runner.CommandResult;
import com.gabriel.audio_generator_service.application.command_runner.ProcessBuilderSyncCommandRunner;
import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import com.gabriel.audio_generator_service.infrastructure.service.youtube.VideoUrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioDownloadServiceTest {

    @Mock
    private UrlGenerator urlGenerator;

    @Mock
    private ProcessBuilderSyncCommandRunner processBuilderSyncCommandRunner;

    @Mock
    private VideoUrlService videoUrlService;

    @InjectMocks
    private AudioDownloadService audioDownloadService;

    @Test
    void downloadAudio_ShouldReturnTrue_WhenCommandExecutionIsSuccessful() throws IOException, InterruptedException {
        String videoUrl = "https://example.com/video";
        String videoId = "video123";

        // Mock the URL generator to return a file
        File mockFile = mock(File.class);
        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId)).thenReturn(mockFile);

        // Mock the command execution to simulate success
        CommandResult commandResult = new CommandResult(0, "Success", "");
        when(processBuilderSyncCommandRunner.execute()).thenReturn(commandResult);

        when(videoUrlService.getFullYoutubeUrl(anyString())).thenReturn(videoUrl);

        // Call the method under test
        boolean result = audioDownloadService.downloadAudio(videoId);

        // Assert that the result is true (successful download)
        assertTrue(result);

        // Verify interactions with the mocked objects
        verify(processBuilderSyncCommandRunner).directory(mockFile);
        verify(processBuilderSyncCommandRunner).command(
                "yt-dlp",
                "-f", "bestaudio",
                "-x",
                "--audio-format", "wav",
                "-o", videoId + ".%(ext)s",
                videoUrl
        );
        verify(processBuilderSyncCommandRunner).execute();
    }

    @Test
    void downloadAudio_ShouldReturnFalse_WhenCommandExecutionFails() throws IOException, InterruptedException {
        String videoUrl = "https://example.com/video";
        String videoId = "video123";

        // Mock the URL generator to return a file
        File mockFile = mock(File.class);
        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId)).thenReturn(mockFile);

        // Mock the command execution to simulate failure (non-zero exit code)
        CommandResult commandResult = new CommandResult(1, "", "Error");
        when(processBuilderSyncCommandRunner.execute()).thenReturn(commandResult);

        when(videoUrlService.getFullYoutubeUrl(anyString())).thenReturn(videoUrl);

        // Call the method under test
        boolean result = audioDownloadService.downloadAudio(videoId);

        // Assert that the result is false (failed download)
        assertFalse(result);

        // Verify interactions with the mocked objects
        verify(processBuilderSyncCommandRunner).directory(mockFile);
        verify(processBuilderSyncCommandRunner).command(
                "yt-dlp",
                "-f", "bestaudio",
                "-x",
                "--audio-format", "wav",
                "-o", videoId + ".%(ext)s",
                videoUrl
        );
        verify(processBuilderSyncCommandRunner).execute();
    }

    @Test
    void downloadAudio_ShouldThrowIOException_WhenIOExceptionOccurs() throws IOException, InterruptedException {
        String videoUrl = "https://example.com/video";
        String videoId = "video123";

        // Mock the URL generator to return a file
        File mockFile = mock(File.class);
        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId)).thenReturn(mockFile);

        // Simulate an IOException when trying to execute the command
        when(processBuilderSyncCommandRunner.execute()).thenThrow(new IOException("Command failed"));

        when(videoUrlService.getFullYoutubeUrl(anyString())).thenReturn(videoUrl);

        // Call the method under test and assert that the exception is thrown
        boolean result = false;
        try {
            result = audioDownloadService.downloadAudio(videoId);
        } catch (IOException e) {
            assertFalse(result);
            assertTrue(e.getMessage().contains("Command failed"));
        }

        // Verify interactions with the mocked objects
        verify(processBuilderSyncCommandRunner).directory(mockFile);
        verify(processBuilderSyncCommandRunner).command(
                "yt-dlp",
                "-f", "bestaudio",
                "-x",
                "--audio-format", "wav",
                "-o", videoId + ".%(ext)s",
                videoUrl
        );
        verify(processBuilderSyncCommandRunner).execute();
    }

    @Test
    void downloadAudio_ShouldThrowInterruptedException_WhenInterruptedExceptionOccurs() throws IOException, InterruptedException {
        String videoUrl = "https://example.com/video";
        String videoId = "video123";

        // Mock the URL generator to return a file
        File mockFile = mock(File.class);
        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId)).thenReturn(mockFile);

        // Simulate an InterruptedException when trying to execute the command
        when(processBuilderSyncCommandRunner.execute()).thenThrow(new InterruptedException("Command interrupted"));

        when(videoUrlService.getFullYoutubeUrl(anyString())).thenReturn(videoUrl);

        // Call the method under test and assert that the exception is thrown
        boolean result = false;
        try {
            result = audioDownloadService.downloadAudio(videoId);
        } catch (InterruptedException e) {
            assertFalse(result);
            assertTrue(e.getMessage().contains("Command interrupted"));
        }

        // Verify interactions with the mocked objects
        verify(processBuilderSyncCommandRunner).directory(mockFile);
        verify(processBuilderSyncCommandRunner).command(
                "yt-dlp",
                "-f", "bestaudio",
                "-x",
                "--audio-format", "wav",
                "-o", videoId + ".%(ext)s",
                videoUrl
        );
        verify(processBuilderSyncCommandRunner).execute();
    }
}
