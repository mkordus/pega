package pega.command;

import pega.io.DataSource;

public interface Command {
    DataSource getResult();
}
