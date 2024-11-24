package com.gabriel.audio_generator_service.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioGeneratorRequestTest {

    @Test
    void testGetAndSetChannelId() {
        String testChannelId = "UC123456789";
        AudioGeneratorRequest request = new AudioGeneratorRequest();

        request.setChannelId(testChannelId);

        assertEquals(testChannelId, request.getChannelId());
    }
}
