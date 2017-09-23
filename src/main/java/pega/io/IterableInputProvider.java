package pega.io;

import java.io.IOException;

public interface IterableInputProvider extends AutoCloseable {
    int getNext() throws IOException;
}
