package pega.io;

import java.io.IOException;

public interface DataDestination {
    void write(int output) throws IOException;
    void close() throws IOException;
    DataSource createDataSource();
}
