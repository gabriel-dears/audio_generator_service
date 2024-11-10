package com.gabriel.audio_generator_service.application.command_runner;

import java.io.File;

public interface CommandRunner {

    CommandRunner command(String... command);

    CommandRunner directory(File directory);

}
