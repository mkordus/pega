package pega.command;

import pega.io.IterableInputProvider;
import pega.io.IterableOutputProvider;

public class MergeCommand implements Command {

    private IterableInputProvider fistInputProvider;
    private IterableInputProvider secondInputProvider;
    private IterableOutputProvider outputProvider;

    public MergeCommand(IterableInputProvider fistInputProvider, IterableInputProvider secondInputProvider, IterableOutputProvider outputProvider) {
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

    public IterableOutputProvider getOutputProvider() {
        return outputProvider;
    }
}
