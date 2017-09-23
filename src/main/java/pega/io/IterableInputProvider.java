package pega.io;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IterableInputProvider {
    int getNext() throws IOException;
}
