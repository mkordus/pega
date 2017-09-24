package pega.io;

import java.io.IOException;

public interface IterableInputProvider {
    int getNext() throws IOException;

    void close() throws IOException;
}
