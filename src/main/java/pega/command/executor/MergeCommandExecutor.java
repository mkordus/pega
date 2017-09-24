package pega.command.executor;

import pega.command.Command;
import pega.command.MergeCommand;
import pega.io.DataSource;
import pega.io.DataDestination;

import java.io.EOFException;
import java.io.IOException;

public class MergeCommandExecutor implements CommandExecutor {

    @Override
    public boolean canExecute(Command command) {
        return command instanceof MergeCommand;
    }

    @Override
    public void execute(Command command) throws IOException {
        MergeCommand mergeCommand = (MergeCommand) command;
        boolean firstHasElements = true;
        boolean secondHasElements = true;
        int first;
        int second;

        DataSource firstInput = mergeCommand.getFistDataSource();
        DataSource secondInput = mergeCommand.getSecondDataSource();
        DataDestination output = mergeCommand.getOutput();

        first = firstInput.getNext();
        second = secondInput.getNext();

        while (firstHasElements || secondHasElements) {
            if (firstHasElements && secondHasElements) {
                if (first < second) {
                    output.write(first);

                    try {
                        first = firstInput.getNext();
                    } catch (EOFException ignored) {
                        firstHasElements = false;
                        firstInput.close();
                    }
                } else {
                    output.write(second);

                    try {
                        second = secondInput.getNext();
                    } catch (EOFException ignored) {
                        secondHasElements = false;
                        secondInput.close();
                    }
                }
            } else if (firstHasElements) {
                output.write(first);
                writeUntilEnd(firstInput, output);
                firstHasElements = false;
            } else {
                output.write(second);
                writeUntilEnd(secondInput, output);
                secondHasElements = false;
            }
        }

        output.close();
    }

    private void writeUntilEnd(DataSource input, DataDestination output) throws IOException {
        try {
            while (true) {
                output.write(input.getNext());
            }
        } catch (EOFException ignored) {
            input.close();
        }
    }
}
