package com.gabriel.audio_generator_service.application.service.youtube;

import com.gabriel.audio_generator_service.infrastructure.service.youtube.VideoUrlExtractor;
import com.gabriel.audio_generator_service.infrastructure.service.youtube.YouTubeApiExecutor;
import com.gabriel.audio_generator_service.infrastructure.service.youtube.YouTubeRequestFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class YouTubeService {

    private final YouTubeRequestFactory requestFactory;
    private final YouTubeApiExecutor apiExecutor;
    private final VideoUrlExtractor urlExtractor;

    @Value("${youtube-api-key}")
    private String youtubeApiKey;

    public YouTubeService(YouTubeRequestFactory requestFactory,
                          YouTubeApiExecutor apiExecutor,
                          VideoUrlExtractor urlExtractor) {
        this.requestFactory = requestFactory;
        this.apiExecutor = apiExecutor;
        this.urlExtractor = urlExtractor;
    }

    public List<String> getChannelVideos(String channelId) throws IOException {
        var request = requestFactory.createSearchRequest(channelId, youtubeApiKey);
        var response = apiExecutor.executeSearchRequest(request);
        return urlExtractor.extractVideoUrls(response);
    }
}
