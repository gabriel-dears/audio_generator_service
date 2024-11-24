package com.gabriel.audio_generator_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessConfig {

    @Bean
    public ProcessBuilder getProcessBuilder() {
        return new ProcessBuilder();
    }

}
