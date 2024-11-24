package com.gabriel.audio_generator_service.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AudioGeneratorResponseTest {

    @Test
    void testConstructorAndGetters() {
        String testMessage = "Audio generation successful.";
        AudioGeneratorResponse response = new AudioGeneratorResponse(testMessage);

        assertEquals(testMessage, response.getMessage());
    }

    @Test
    void testSetMessage() {
        String initialMessage = "Initial message.";
        String updatedMessage = "Updated message.";
        AudioGeneratorResponse response = new AudioGeneratorResponse(initialMessage);

        response.setMessage(updatedMessage);

        assertEquals(updatedMessage, response.getMessage());
    }
}
