package pega.command.executor;

import pega.command.Command;
import pega.command.SortCommand;
import pega.io.DataSourceWithDefinedSize;
import pega.io.DataDestination;

import java.io.IOException;
import java.util.Arrays;

public class SortCommandExecutor implements CommandExecutor {

    @Override
    public boolean canExecute(Command command) {
        return command instanceof SortCommand;
    }

    public void execute(Command command) throws IOException {
        SortCommand sortCommand = (SortCommand) command;

        DataSourceWithDefinedSize input = sortCommand
            .getInputProvider();
        DataDestination output = sortCommand.getOutput();

        int size = input.getSize();
        int[] data = new int[size];

        for (int i = 0; i < size; i++) {
            data[i] = input.getNext();
        }
        input.close();

        Arrays.sort(data);

        for (int i = 0; i < size; i++) {
            output.write(data[i]);
        }
        output.close();
    }
}
