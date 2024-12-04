package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.google.api.services.youtube.YouTube;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class YouTubeRequestFactory {

    private final YouTube youtube;

    public YouTubeRequestFactory(YouTube youtube) {
        this.youtube = youtube;
    }

    public YouTube.Search.List createSearchVideosByChannelRequest(String channelId, String youtubeApiKey) throws IOException {
        YouTube.Search.List request = youtube.search().list(List.of("id", "snippet"));
        request.setChannelId(channelId);
        request.setMaxResults(10L);
        request.setOrder("date");
        request.setKey(youtubeApiKey);
        return request;
    }

    public YouTube.Videos.List createVideoDetailsByVideosRequest(List<String> videoIds, String youtubeApiKey) throws IOException {
        YouTube.Videos.List request = youtube.videos().list(List.of("snippet", "contentDetails", "statistics"));
        request.setId(videoIds); // Pass video IDs here
        request.setKey(youtubeApiKey);
        return request;
    }

    public YouTube.VideoCategories.List createVideoCategoriesRequest(String categoryId, String youtubeApiKey) throws IOException {
        // Fetch details of a specific video category by its ID
        YouTube.VideoCategories.List request = youtube.videoCategories().list(List.of("snippet"));
        request.setId(List.of(categoryId));
        request.setKey(youtubeApiKey);
        request.setHl("pt");
        return request;
    }


}
