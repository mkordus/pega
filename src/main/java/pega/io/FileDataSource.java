package pega.io;

import java.io.*;

public class FileDataSource implements DataSource {

    private final File file;
    private RandomAccessFile input;

    public FileDataSource(File file) {
        this.file = file;
    }

    @Override
    public int getNext() throws IOException {
        if (input == null) {
            input = new RandomAccessFile(file, "r");
        }

        return input.readInt();
    }

    public void close() throws IOException {
        if (input != null) {
            input.close();
        }
    }
}
