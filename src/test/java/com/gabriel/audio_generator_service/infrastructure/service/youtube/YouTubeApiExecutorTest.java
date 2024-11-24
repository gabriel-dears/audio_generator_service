package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class YouTubeApiExecutorTest {

    @Test
    void shouldExecute() throws IOException {
        YouTube.Search.List request = mock(YouTube.Search.List.class);

        SearchListResponse searchListResponse = mock(SearchListResponse.class);
        when(searchListResponse.getItems()).thenReturn(List.of(mock(SearchResult.class)));
        when(request.execute()).thenReturn(searchListResponse);

        YouTubeApiExecutor executor = new YouTubeApiExecutor();
        SearchListResponse searchListResponseAfterExecuting = executor.executeSearchRequest(request);
        assertThat(searchListResponse).isNotNull();
        assertThat(searchListResponseAfterExecuting.getItems()).hasSize(1);
        assertThat(searchListResponseAfterExecuting.getItems().get(0)).isEqualTo(searchListResponse.getItems().get(0));
    }

}