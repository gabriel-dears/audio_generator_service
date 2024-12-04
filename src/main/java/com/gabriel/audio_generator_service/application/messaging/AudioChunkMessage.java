package com.gabriel.audio_generator_service.application.messaging;

import java.io.Serializable;
import java.util.List;

public record AudioChunkMessage(String channelId, String videoId, Object audioChunk, List<String> tags, String category, String audioPart) implements Serializable {
}
