package com.gabriel.audio_generator_service.application.messaging;

public record AudioChunkMessage(String channelId, String videoId, Object audioChunk) {
}
