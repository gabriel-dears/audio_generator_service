package com.gabriel.audio_generator_service.infrastructure.config;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class YoutubeConstantsTest {

    @Test
    void shouldRunWithoutErrors() {
        String youtubeUrl = YoutubeConstants.YOUTUBE_URL;
        assertThat(youtubeUrl).isEqualTo("https://www.youtube.com/watch?v=");
    }

}