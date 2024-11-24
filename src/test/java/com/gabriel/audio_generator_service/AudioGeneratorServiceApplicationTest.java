package com.gabriel.audio_generator_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AudioGeneratorServiceApplicationTests {

    @Test
    void contextLoads() {
        // This test will verify if the application context loads correctly.
        // If there are any issues with the context (e.g., missing beans), this test will fail.
    }

    @Test
    void shouldRunApplication() {
        // This test runs the main application class to ensure the application starts correctly.
        AudioGeneratorServiceApplication.main(new String[]{});
    }
}
