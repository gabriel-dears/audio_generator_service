package com.gabriel.audio_generator_service.application.messaging;

import com.gabriel.audio_generator_service.domain.model.VideoDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AudioChunkMessageTest {

    private final String channelId = "channelId";
    private final String videoId = "videoId";
    private final VideoDetails videoDetails = new VideoDetails(videoId, null, null, channelId);

    @Test
    public void testAudioChunkMessageCreation() {
        // Given
        Object audioChunk = new Object();  // In a real case, this would be a more specific type

        // When
        AudioChunkMessage message = new AudioChunkMessage(videoDetails.channelId(), videoDetails.videoId(), audioChunk, videoDetails.tags(), videoDetails.categoryId(), null);

        // Then
        assertNotNull(message);
        assertEquals(channelId, message.channelId());
        assertEquals(videoId, message.videoId());
        assertEquals(audioChunk, message.audioChunk());
    }

    @Test
    public void testAudioChunkMessageEquality() {
        // Given
        String channelId = "channelId";
        String videoId = "videoId";
        Object audioChunk = new Object();  // In a real case, this would be a more specific type

        AudioChunkMessage message1 = new AudioChunkMessage(videoDetails.channelId(), videoDetails.videoId(), audioChunk, videoDetails.tags(), videoDetails.categoryId(), null);
        AudioChunkMessage message2 = new AudioChunkMessage(channelId, videoId, audioChunk, videoDetails.tags(), videoDetails.categoryId(), null);

        // When & Then
        assertEquals(message1, message2);  // Assert equality based on record properties
    }

    @Test
    public void testAudioChunkMessageNotEqual() {
        // Given
        String channelId1 = "testChannel1";
        String videoId = "testVideo";
        Object audioChunk = new Object();

        AudioChunkMessage message1 = new AudioChunkMessage(videoDetails.channelId(), videoDetails.videoId(), audioChunk, videoDetails.tags(), videoDetails.categoryId(), null);
        AudioChunkMessage message2 = new AudioChunkMessage(channelId1, videoId, audioChunk, videoDetails.tags(), videoDetails.categoryId(), null);

        // When & Then
        assertNotEquals(message1, message2);  // Assert inequality if one field differs
    }
}
