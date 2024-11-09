package com.gabriel.clipcrafter.application.command_runner;

import java.io.IOException;

public interface SyncCommandRunner extends CommandRunner {

    CommandResult execute() throws IOException, InterruptedException;

}
