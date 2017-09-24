package pega.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileDataDestination implements DataDestination {

    private final File file;
    private RandomAccessFile output;

    public FileDataDestination(File file) {
        this.file = file;
    }

    @Override
    public void write(int element) throws IOException {
        if (output == null) {
            output = new RandomAccessFile(file, "rw");
            output.seek(output.length());
        }

        output.writeInt(element);
    }

    @Override
    public void close() throws IOException {
        if (output != null) {
            output.close();
        }
    }

    @Override
    public DataSource createDataSource() {
        return new FileDataSource(file);
    }
}
