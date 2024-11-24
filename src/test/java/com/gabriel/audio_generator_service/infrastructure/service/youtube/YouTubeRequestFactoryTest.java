package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.google.api.services.youtube.YouTube;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YouTubeRequestFactoryTest {

    @Mock
    private YouTube youtube;

    @InjectMocks
    private YouTubeRequestFactory youTubeRequestFactory;

    @Test
    void shouldCreateRequest() throws IOException {
        String channelId = "channelId";
        String youtubeApiKey = "youtubeApiKey";
        YouTube.Search youtubeSearch = mock(YouTube.Search.class);
        YouTube.Search.List youtubeSearchList = mock(YouTube.Search.List.class);

        when(youtube.search()).thenReturn(youtubeSearch);
        when(youtubeSearch.list(any())).thenReturn(youtubeSearchList);

        youTubeRequestFactory.createSearchRequest(channelId, youtubeApiKey);
    }

}