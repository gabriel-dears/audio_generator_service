package com.gabriel.audio_generator_service.presentation.controller;

import com.gabriel.audio_generator_service.application.dto.AudioGeneratorRequest;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorResponse;
import com.gabriel.audio_generator_service.application.facade.AudioGenerationFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate-audio")
public class AudioGeneratorController {

    private final AudioGenerationFacade audioGenerationFacade;

    public AudioGeneratorController(AudioGenerationFacade audioGenerationFacade) {
        this.audioGenerationFacade = audioGenerationFacade;
    }

    @PostMapping("/channel")
    public AudioGeneratorResponse generateAudios(@RequestBody AudioGeneratorRequest audioGeneratorRequest) {
        return audioGenerationFacade.executeAudioGeneration(audioGeneratorRequest);
    }
}

