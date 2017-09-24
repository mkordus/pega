package pega.command;

import pega.io.IterableInputProvider;
import pega.io.OutputProvider;

public class MergeCommand implements Command {

    private IterableInputProvider fistInputProvider;
    private IterableInputProvider secondInputProvider;
    private OutputProvider outputProvider;

    public MergeCommand(IterableInputProvider fistInputProvider, IterableInputProvider secondInputProvider, OutputProvider outputProvider) {
        this.fistInputProvider = fistInputProvider;
        this.secondInputProvider = secondInputProvider;
        this.outputProvider = outputProvider;
    }

    public IterableInputProvider getFistInputProvider() {
        return fistInputProvider;
    }

    public IterableInputProvider getSecondInputProvider() {
        return secondInputProvider;
    }

    public OutputProvider getOutputProvider() {
        return outputProvider;
    }
}
