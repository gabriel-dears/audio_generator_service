package com.gabriel.audio_generator_service.application.service.youtube;

import com.gabriel.audio_generator_service.infrastructure.config.YoutubeConstants;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeService {

    private final YouTube youtube;

    @Value("${youtube-api-key}")
    private String youtubeApiKey;

    public YouTubeService(YouTube youtube) {
        this.youtube = youtube;
    }

    public List<String> getChannelVideos(String channelId) throws IOException {
        YouTube.Search.List request = createSearchRequest(channelId);
        SearchListResponse response = executeSearchRequest(request);
        return extractVideoUrls(response);
    }

    // Factory method pattern: Create YouTube.Search.List request
    private YouTube.Search.List createSearchRequest(String channelId) throws IOException {
        YouTube.Search.List request = youtube.search().list(List.of("id", "snippet"));
        request.setChannelId(channelId);
        request.setMaxResults(10L);
        request.setOrder("date");
        request.setKey(youtubeApiKey);
        return request;
    }

    // Template method pattern: Search request execution
    private SearchListResponse executeSearchRequest(YouTube.Search.List request) throws IOException {
        return request.execute(); // Can be overridden or extended if needed (e.g., logging, error handling)
    }

    // Strategy pattern: Handle the result differently (e.g., extracting URLs or transforming them)
    private List<String> extractVideoUrls(SearchListResponse response) {
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
