package com.gabriel.audio_generator_service.application.service.youtube;

import com.gabriel.audio_generator_service.infrastructure.service.youtube.VideoUrlExtractor;
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
    private VideoUrlExtractor urlExtractor;

    @InjectMocks
    private YouTubeService youTubeService;

    @Test
    public void testGetChannelVideos() throws IOException {
        when(requestFactory.createSearchRequest(any(), any())).thenReturn(mock(YouTube.Search.List.class));
        when(apiExecutor.executeSearchRequest(any())).thenReturn(mock(SearchListResponse.class));
        when(urlExtractor.extractVideoUrls(any())).thenReturn(List.of("url1", "url2", "url3"));

        String channelId = "sampleChannelId";
        List<String> videoUrls = youTubeService.getChannelVideos(channelId);

        verify(requestFactory).createSearchRequest(any(), any());
        verify(apiExecutor).executeSearchRequest(any());
        verify(urlExtractor).extractVideoUrls(any());

        assertThat(3).isEqualTo(videoUrls.size());
        assertThat("url1").isEqualTo(videoUrls.get(0));
        assertThat("url2").isEqualTo(videoUrls.get(1));
        assertThat("url3").isEqualTo(videoUrls.get(2));
    }
}


