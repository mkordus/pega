package pega.command;

import pega.io.DataSource;
import pega.io.DataDestination;

public class MergeCommand implements Command {

    private DataSource fistDataSource;
    private DataSource secondDataSource;
    private DataDestination output;

    public MergeCommand(DataSource fistDataSource, DataSource secondInputProvider, DataDestination output) {
        this.fistDataSource = fistDataSource;
        this.secondDataSource = secondInputProvider;
        this.output = output;
    }

    public DataSource getFistDataSource() {
        return fistDataSource;
    }

    public DataSource getSecondDataSource() {
        return secondDataSource;
    }

    public DataDestination getOutput() {
        return output;
    }

    @Override
    public DataSource getResult() {
        return output.createDataSource();
    }
}
