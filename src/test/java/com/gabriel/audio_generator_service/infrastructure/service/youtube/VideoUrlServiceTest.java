package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.gabriel.audio_generator_service.infrastructure.config.YoutubeConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VideoUrlServiceTest {

    @Test
    void shouldContainVideosExtractedFromResponse() {
        String videoId = "videoId";
        String url = new VideoUrlService().getFullYoutubeUrl(videoId);
        assertThat(String.format("%s%s", YoutubeConstants.YOUTUBE_URL, videoId)).isEqualTo(url);
    }
}
