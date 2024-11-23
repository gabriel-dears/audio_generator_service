package com.gabriel.audio_generator_service.application.messaging;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AudioChunkMessageTest {

    @Test
    public void testAudioChunkMessageCreation() {
        // Given
        String channelId = "testChannel";
        String videoId = "testVideo";
        Object audioChunk = new Object();  // In a real case, this would be a more specific type

        // When
        AudioChunkMessage message = new AudioChunkMessage(channelId, videoId, audioChunk);

        // Then
        assertNotNull(message);
        assertEquals(channelId, message.channelId());
        assertEquals(videoId, message.videoId());
        assertEquals(audioChunk, message.audioChunk());
    }

    @Test
    public void testAudioChunkMessageEquality() {
        // Given
        String channelId = "testChannel";
        String videoId = "testVideo";
        Object audioChunk = new Object();  // In a real case, this would be a more specific type

        AudioChunkMessage message1 = new AudioChunkMessage(channelId, videoId, audioChunk);
        AudioChunkMessage message2 = new AudioChunkMessage(channelId, videoId, audioChunk);

        // When & Then
        assertEquals(message1, message2);  // Assert equality based on record properties
    }

    @Test
    public void testAudioChunkMessageNotEqual() {
        // Given
        String channelId1 = "testChannel1";
        String channelId2 = "testChannel2";
        String videoId = "testVideo";
        Object audioChunk = new Object();

        AudioChunkMessage message1 = new AudioChunkMessage(channelId1, videoId, audioChunk);
        AudioChunkMessage message2 = new AudioChunkMessage(channelId2, videoId, audioChunk);

        // When & Then
        assertNotEquals(message1, message2);  // Assert inequality if one field differs
    }
}
