package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.gabriel.audio_generator_service.infrastructure.config.YoutubeConstants;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VideoUrlExtractor {

    public List<String> extractVideoUrls(SearchListResponse response) {
        List<String> videoUrls = new ArrayList<>();
        for (SearchResult video : response.getItems()) {
            videoUrls.add(getFullYoutubeUrl(video));
        }
        return videoUrls;
    }

    private String getFullYoutubeUrl(SearchResult video) {
        return String.format("%s%s", YoutubeConstants.YOUTUBE_URL, video.getSnippet().getTitle());
    }
}
