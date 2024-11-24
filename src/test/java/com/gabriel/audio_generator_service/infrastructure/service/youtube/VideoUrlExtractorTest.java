package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoUrlExtractorTest {

    @Mock
    private SearchListResponse searchListResponse;

    @Mock
    private SearchResult searchResult;

    @Mock
    private SearchResultSnippet searchResultSnippet;

    @Test
    void shouldContainVideosExtractedFromResponse() {
        when(searchResult.getSnippet()).thenReturn(searchResultSnippet);
        when(searchResultSnippet.getTitle()).thenReturn("Testing Title From Youtube Api");
        when(searchListResponse.getItems()).thenReturn(List.of(searchResult));

        VideoUrlExtractor videoUrlExtractor = new VideoUrlExtractor();
        List<String> strings = videoUrlExtractor.extractVideoUrls(searchListResponse);

        assertThat(strings.size()).isEqualTo(1);
        assertThat(strings).contains("https://www.youtube.com/watch?v=Testing Title From Youtube Api");
    }
}
