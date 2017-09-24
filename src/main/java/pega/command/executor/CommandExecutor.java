package pega.command.executor;

import pega.command.Command;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface CommandExecutor {

    boolean canExecute(Command command);

    void execute(Command command) throws IOException, ExecutionException, InterruptedException;
}
