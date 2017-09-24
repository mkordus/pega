package pega.io;

import java.io.*;

public class FileIterableInputProvider implements IterableInputProvider {

    private final File file;
    private RandomAccessFile input;

    public FileIterableInputProvider(File file) {
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
