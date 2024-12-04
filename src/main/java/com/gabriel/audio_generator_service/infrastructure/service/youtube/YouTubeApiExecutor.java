package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.VideoCategoryListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YouTubeApiExecutor {

    public SearchListResponse executeSearchVideosByChannel(YouTube.Search.List request) throws IOException {
        return request.execute();
    }

    public VideoListResponse executeSearchVideoDetailsByVideo(YouTube.Videos.List request) throws IOException {
        return request.execute();
    }

    public VideoCategoryListResponse executeSearchCategoryDetailsByCategory(YouTube.VideoCategories.List request) throws IOException {
        return request.execute();
    }
}
