package pega.command.executor;

import pega.command.Command;

public interface CommandExecutor {

    boolean canExecute(Command command);

    void execute(Command command);
}
