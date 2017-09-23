package pega.command.executor;

import pega.command.SortCommand;

import java.io.IOException;
import java.util.Arrays;

public class SortCommandExecutor {

    public void execute(SortCommand command) throws IOException {
        int[] input = command.getInputProvider().getInput();

        Arrays.parallelSort(input);

        command.getOutputProvider().write(input);
    }
}
