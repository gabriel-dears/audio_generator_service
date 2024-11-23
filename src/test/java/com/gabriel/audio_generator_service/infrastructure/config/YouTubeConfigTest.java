package com.gabriel.audio_generator_service.infrastructure.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@SpringJUnitConfig
public class YouTubeConfigTest {

    @Test
    public void shouldUseMockedGoogleNetHttpTransport() throws Exception {
        try (
                MockedStatic<GoogleNetHttpTransport> mockedTransport = mockStatic(GoogleNetHttpTransport.class);
                MockedStatic<GsonFactory> mockedGsonFactory = mockStatic(GsonFactory.class)
        ) {
            // Mock static methods
            mockedTransport.when(GoogleNetHttpTransport::newTrustedTransport).thenReturn(new NetHttpTransport());
            mockedGsonFactory.when(GsonFactory::getDefaultInstance).thenReturn(mock(GsonFactory.class));

            // Call the method under test
            YouTube youtube = new YouTubeConfig().youtube();

            // Assert
            assertThat(youtube).isNotNull();
        }
    }
}
