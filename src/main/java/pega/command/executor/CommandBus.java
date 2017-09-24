package pega.command.executor;

import pega.command.Command;

import java.io.IOException;
import java.util.List;

public class CommandBus {

    private List<CommandExecutor> executors;

    public CommandBus(List<CommandExecutor> executors) {
        this.executors = executors;
    }

    public void execute(Command command) throws ExecutorNotFoundException, IOException {
        for (CommandExecutor executor : executors) {
            if (executor.canExecute(command)) {
                executor.execute(command);

                return;
            }
        }

        throw new ExecutorNotFoundException();
    }

    public static class ExecutorNotFoundException extends Exception {
    }
}
