package com.gabriel.audio_generator_service.application.command_runner;

import java.io.IOException;

public interface SyncCommandRunner extends CommandRunner {

    CommandResult execute() throws IOException, InterruptedException;

}
