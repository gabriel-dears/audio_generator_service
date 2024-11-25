package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.gabriel.audio_generator_service.infrastructure.config.YoutubeConstants;
import org.springframework.stereotype.Component;

@Component
public class VideoUrlService {

    public String getFullYoutubeUrl(String videoId) {
        return String.format("%s%s", YoutubeConstants.YOUTUBE_URL, videoId);
    }
}
