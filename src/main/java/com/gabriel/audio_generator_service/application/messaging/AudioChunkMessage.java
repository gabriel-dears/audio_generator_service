package com.gabriel.audio_generator_service.application.messaging;

import java.io.Serializable;

public record AudioChunkMessage(String channelId, String videoId, Object audioChunk) implements Serializable {
}
