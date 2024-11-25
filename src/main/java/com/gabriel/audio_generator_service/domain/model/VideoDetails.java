package com.gabriel.audio_generator_service.domain.model;

import java.util.List;

public record VideoDetails(String videoId, List<String> tags, String categoryId, String channelId) {
}
