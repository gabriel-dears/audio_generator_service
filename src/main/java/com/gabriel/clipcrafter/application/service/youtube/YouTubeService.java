package com.gabriel.clipcrafter.application.service.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YouTubeService {

    private final YouTube youtube;

    @Autowired
    public YouTubeService(YouTube youtube) {
        this.youtube = youtube;
    }

    public List<String> getChannelVideos(String channelId) throws IOException {
        YouTube.Search.List request = youtube.search().list(List.of("id", "snippet"));
        request.setChannelId(channelId);
        request.setMaxResults(10L);
        request.setOrder("date");
        request.setKey("AIzaSyCa1r6Kd291vLPmyc8Z-4fer38tSg-OPNk"); // AIzaSyCa1r6Kd291vLPmyc8Z-4fer38tSg-OPNk

        SearchListResponse response = request.execute();
        List<String> videoUrls = new ArrayList<>();

        for (SearchResult video : response.getItems()) {
            videoUrls.add("https://www.youtube.com/watch?v=" + video.getId().getVideoId());
        }
        return videoUrls;
    }
}

