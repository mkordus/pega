package pega.io;

import java.io.IOException;

public interface DataSource {
    int getNext() throws IOException;

    void close() throws IOException;
}
