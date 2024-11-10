package com.gabriel.audio_generator_service.application.command_runner;

import java.util.concurrent.CompletableFuture;

public interface AsyncCommandRunner extends CommandRunner {

    CompletableFuture<CommandResult> executeAsync();

}
