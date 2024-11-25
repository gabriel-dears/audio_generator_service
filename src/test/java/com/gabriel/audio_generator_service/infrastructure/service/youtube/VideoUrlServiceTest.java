package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoUrlServiceTest {

    @Mock
    private SearchListResponse searchListResponse;

    @Mock
    private SearchResult searchResult;

    @Mock
    private ResourceId resourceId;

    @Test
    void shouldContainVideosExtractedFromResponse() {
        when(searchResult.getId()).thenReturn(resourceId);
        when(resourceId.getVideoId()).thenReturn("RANDOM_VIDEO_ID");
        when(searchListResponse.getItems()).thenReturn(List.of(searchResult));
    }
}
