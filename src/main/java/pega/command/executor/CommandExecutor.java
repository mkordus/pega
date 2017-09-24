package pega.command.executor;

import pega.command.Command;

import java.io.IOException;

public interface CommandExecutor {

    boolean canExecute(Command command);

    void execute(Command command) throws IOException;
}
