package pega.command;

import pega.io.IterableInputProvider;

public interface Command {
    IterableInputProvider getResult();
}
