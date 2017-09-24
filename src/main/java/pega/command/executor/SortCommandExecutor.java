package pega.command.executor;

import pega.command.Command;
import pega.command.SortCommand;

import java.io.IOException;
import java.util.Arrays;

public class SortCommandExecutor implements CommandExecutor {

    @Override
    public boolean canExecute(Command command) {
        return command instanceof SortCommand;
    }

    public void execute(Command command) throws IOException {
        SortCommand sortCommand = (SortCommand) command;

        int[] input = sortCommand.getInputProvider().getInput();

        Arrays.parallelSort(input);

        sortCommand.getOutputProvider().write(input);
    }
}
