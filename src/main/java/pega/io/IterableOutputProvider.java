package pega.io;

import java.io.IOException;

public interface IterableOutputProvider {
    void write(int output) throws IOException;
    void close() throws IOException;
    IterableInputProvider createInput();
}
