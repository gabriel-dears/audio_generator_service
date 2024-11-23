package com.gabriel.audio_generator_service.presentation.controller;

import com.gabriel.audio_generator_service.application.dto.AudioGeneratorRequest;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorResponse;
import com.gabriel.audio_generator_service.application.facade.AudioGenerationFacade;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AudioGeneratorControllerTest {

    @Test
    void shouldGenerateAudios() {
        // Arrange: Set up the necessary test data, mocks, and class instances
        AudioGeneratorRequest audioGeneratorRequest = new AudioGeneratorRequest();
        audioGeneratorRequest.setChannelId("channelId"); // Setting channel ID for the request

        // Create a mock for the AudioGenerationFacade
        AudioGenerationFacade facade = mock(AudioGenerationFacade.class);

        // Define the expected response for the mock method execution
        AudioGeneratorResponse audioGeneratorResponse = new AudioGeneratorResponse("Testing response");

        // Set up the mock to return the predefined response when the executeAudioGeneration method is called
        when(facade.executeAudioGeneration(audioGeneratorRequest)).thenReturn(audioGeneratorResponse);

        // Act: Call the method under test
        AudioGeneratorController audioGeneratorController = new AudioGeneratorController(facade);
        AudioGeneratorResponse response = audioGeneratorController.generateAudios(audioGeneratorRequest); // Calling the controller method

        // Assert: Verify that the method behaved as expected
        assertThat(response).isNotNull(); // Ensure the response is not null
        assertThat(response).isSameAs(audioGeneratorResponse); // Verify that the response matches the expected mock response
    }
}
