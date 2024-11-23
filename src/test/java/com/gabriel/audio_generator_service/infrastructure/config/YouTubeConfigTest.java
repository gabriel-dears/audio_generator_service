package com.gabriel.audio_generator_service.infrastructure.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringJUnitConfig
public class YouTubeConfigTest {
    
    @Test
    public void shouldUseMockedGoogleNetHttpTransport() throws Exception {
        try (var mockedTransport = Mockito.mockStatic(GoogleNetHttpTransport.class)) {
            mockedTransport.when(GoogleNetHttpTransport::newTrustedTransport).thenReturn(mock(NetHttpTransport.class));
            GsonFactory mockedFactory = mock(GsonFactory.class);
            YouTube youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), mockedFactory, httpRequest -> {
            }).setApplicationName("YouTubeClipCreator").build();
            assertThat(youtube).isNotNull();
        }
    }
}
