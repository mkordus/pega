package pega.io;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface DataSource {
    int getNext() throws IOException, ExecutionException, InterruptedException;

    void close() throws IOException;
}
