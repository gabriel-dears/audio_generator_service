package com.gabriel.audio_generator_service.application.service.audio.audio_deletion;

import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudioDeletionServiceTest {

    @Mock
    private UrlGenerator urlGenerator;

    @InjectMocks
    private AudioDeletionService audioDeletionService;

    @Test
    void shouldAddAudioToDelete() {
        String videoId = UUID.randomUUID().toString();
        File file = mock(File.class);
        when(urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId)).thenReturn(file);
        audioDeletionService.addAudioToDelete(videoId);

        verify(urlGenerator, times(1)).getClipsUrlBasedOnVideoIdAsFile(videoId);
    }

    @Test
    void shouldDeleteAudios() {
        assertThatNoException().isThrownBy(() -> audioDeletionService.deleteDirectoriesOnceADay());
    }

}