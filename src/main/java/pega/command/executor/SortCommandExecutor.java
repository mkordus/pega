package pega.command.executor;

import pega.command.SortCommand;

import java.util.Arrays;

public class SortCommandExecutor {

    public void execute(SortCommand command) {
        int[] input = command.getInputProvider().getInput();

        Arrays.parallelSort(input);

        command.getOutputProvider().setOutput(input);
    }
}
