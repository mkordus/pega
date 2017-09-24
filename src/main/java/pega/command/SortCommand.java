package pega.command;

import pega.io.InputProvider;
import pega.io.IterableInputProvider;
import pega.io.OutputProvider;

public class SortCommand implements Command {

    private InputProvider inputProvider;
    private OutputProvider outputProvider;

    public SortCommand(
        InputProvider inputProvider,
        OutputProvider outputProvider
    ) {
        if (inputProvider == null || outputProvider == null) {
            throw new IllegalArgumentException();
        }

        this.inputProvider = inputProvider;
        this.outputProvider = outputProvider;
    }

    public InputProvider getInputProvider() {
        return inputProvider;
    }

    public OutputProvider getOutputProvider() {
        return outputProvider;
    }

    @Override
    public IterableInputProvider getResult() {
        return outputProvider.createInput();
    }
}
