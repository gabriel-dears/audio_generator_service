package com.gabriel.audio_generator_service.application.service.youtube;

import com.gabriel.audio_generator_service.domain.model.VideoDetails;
import com.gabriel.audio_generator_service.infrastructure.service.youtube.YouTubeApiExecutor;
import com.gabriel.audio_generator_service.infrastructure.service.youtube.YouTubeRequestFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class YouTubeService {

    private final YouTubeRequestFactory requestFactory;
    private final YouTubeApiExecutor apiExecutor;

    @Value("${youtube-api-key}")
    private String youtubeApiKey;

    public YouTubeService(YouTubeRequestFactory requestFactory,
                          YouTubeApiExecutor apiExecutor) {
        this.requestFactory = requestFactory;
        this.apiExecutor = apiExecutor;
    }

    public List<String> getChannelVideosIds(String channelId) throws IOException {
        var request = requestFactory.createSearchVideosByChannelRequest(channelId, youtubeApiKey);
        var response = apiExecutor.executeSearchRequest(request);
        return getVideoIdsList(response);
    }

    public List<VideoDetails> getVideosDetails(List<String> videoIdList) throws IOException {
        YouTube.Videos.List request = requestFactory.createVideoDetailsByVideosRequest(videoIdList, youtubeApiKey);
        var response = request.execute();
        return getVideoDetailsList(response);
    }

    private static List<VideoDetails> getVideoDetailsList(VideoListResponse response) {
        return response.getItems().stream().map(YouTubeService::getVideoDetails).toList();
    }

    private static VideoDetails getVideoDetails(Video item) {
        return new VideoDetails(item.getId(), item.getSnippet().getTags(), item.getSnippet().getCategoryId(), item.getSnippet().getChannelId());
    }


    private static List<String> getVideoIdsList(SearchListResponse response) {
        return response.getItems().stream()
                .map(item -> item.getId().getVideoId()) // Assuming it's a video result
                .toList();
    }
}
