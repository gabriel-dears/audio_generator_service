package com.gabriel.audio_generator_service.infrastructure.service.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class YouTubeApiExecutor {

    public SearchListResponse executeSearchRequest(YouTube.Search.List request) throws IOException {
        return request.execute();
    }
}
