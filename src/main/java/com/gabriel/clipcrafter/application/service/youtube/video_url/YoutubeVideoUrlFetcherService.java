package com.gabriel.clipcrafter.application.service.youtube.video_url;

import com.gabriel.clipcrafter.application.service.youtube.YouTubeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class YoutubeVideoUrlFetcherService {

    private final YouTubeService youTubeService;

    public YoutubeVideoUrlFetcherService(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    public List<String> fetchVideoUrls(String channelId) throws IOException {
        return youTubeService.getChannelVideos(channelId);
    }
}
