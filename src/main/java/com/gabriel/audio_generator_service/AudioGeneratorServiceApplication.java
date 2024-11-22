package com.gabriel.audio_generator_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AudioGeneratorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioGeneratorServiceApplication.class, args);
    }

}
