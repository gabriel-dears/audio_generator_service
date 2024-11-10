package com.gabriel.audio_generator_service.presentation.controller;

import com.gabriel.audio_generator_service.application.dto.AudioGeneratorRequest;
import com.gabriel.audio_generator_service.application.dto.AudioGeneratorResponse;
import com.gabriel.audio_generator_service.application.service.audio.audio_generator.AudioGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/generate-audio")
public class AudioGeneratorController {

    @Autowired
    private AudioGeneratorService audioGeneratorService;

    @PostMapping("/test")
    public AudioGeneratorResponse generateAudios2(@RequestBody AudioGeneratorRequest audioGeneratorRequest) throws IOException {
        return audioGeneratorService.test(audioGeneratorRequest);
    }

    @PostMapping("/channel")
    public AudioGeneratorResponse generateAudios(@RequestBody AudioGeneratorRequest audioGeneratorRequest) throws IOException {
        return audioGeneratorService.generateAudios(audioGeneratorRequest);
    }
}

