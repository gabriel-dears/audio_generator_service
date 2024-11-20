package com.gabriel.audio_generator_service.application.service.url.url_extractor;

import com.gabriel.audio_generator_service.application.service.url.query_param.QueryParamExtractor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UrlExtractorService {

    private final QueryParamExtractor queryParamExtractor;

    public UrlExtractorService(QueryParamExtractor queryParamExtractor) {
        this.queryParamExtractor = queryParamExtractor;
    }

    public String getVideoId(String videoUrl) {
        Map<String, String> queryParams = queryParamExtractor.getQueryParams(videoUrl);
        return queryParams.get("v");
    }

}