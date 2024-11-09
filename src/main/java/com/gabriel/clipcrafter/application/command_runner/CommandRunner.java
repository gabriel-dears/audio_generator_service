package com.gabriel.clipcrafter.application.command_runner;

import java.io.File;

public interface CommandRunner {

    CommandRunner command(String... command);

    CommandRunner directory(File directory);

}
