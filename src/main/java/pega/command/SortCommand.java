package pega.command;

import pega.io.DataSource;
import pega.io.DataSourceWithDefinedSize;
import pega.io.DataDestination;

public class SortCommand implements Command {

    private DataSourceWithDefinedSize input;
    private DataDestination output;

    public SortCommand(
        DataSourceWithDefinedSize input,
        DataDestination output
    ) {
        if (input == null || output == null) {
            throw new IllegalArgumentException();
        }

        this.input = input;
        this.output = output;
    }

    public DataSourceWithDefinedSize getInputProvider() {
        return input;
    }

    public DataDestination getOutput() {
        return output;
    }

    @Override
    public DataSource getResult() {
        return output.createDataSource();
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t" + input + "\n\t" + output;
    }
}
