package com.gabriel.audio_generator_service.application.service.youtube;

import com.gabriel.audio_generator_service.infrastructure.service.youtube.VideoUrlService;
import com.gabriel.audio_generator_service.infrastructure.service.youtube.YouTubeApiExecutor;
import com.gabriel.audio_generator_service.infrastructure.service.youtube.YouTubeRequestFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class YouTubeServiceTest {

    @Mock
    private YouTubeRequestFactory requestFactory;

    @Mock
    private YouTubeApiExecutor apiExecutor;

    @Mock
    private VideoUrlService urlExtractor;

    @InjectMocks
    private YouTubeService youTubeService;

    @Test
    public void testGetChannelVideosIds() throws IOException {
        when(requestFactory.createSearchVideosByChannelRequest(any(), any())).thenReturn(mock(YouTube.Search.List.class));
        when(apiExecutor.executeSearchRequest(any())).thenReturn(mock(SearchListResponse.class));

        String channelId = "sampleChannelId";
        List<String> videoUrls = youTubeService.getChannelVideosIds(channelId);

        verify(requestFactory).createSearchVideosByChannelRequest(any(), any());
        verify(apiExecutor).executeSearchRequest(any());

        assertThat(3).isEqualTo(videoUrls.size());
        assertThat("url1").isEqualTo(videoUrls.get(0));
        assertThat("url2").isEqualTo(videoUrls.get(1));
        assertThat("url3").isEqualTo(videoUrls.get(2));
    }
}


