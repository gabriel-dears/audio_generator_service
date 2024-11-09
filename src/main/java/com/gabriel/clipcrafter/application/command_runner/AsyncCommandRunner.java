package com.gabriel.clipcrafter.application.command_runner;

import java.util.concurrent.CompletableFuture;

public interface AsyncCommandRunner extends CommandRunner {

    CompletableFuture<CommandResult> executeAsync();

}
