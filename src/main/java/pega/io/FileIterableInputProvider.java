package pega.io;

import java.io.*;

public class FileIterableInputProvider implements IterableInputProvider {

    private final File file;
    private final int startPosition;
    private final int intSize = 4;
    private RandomAccessFile input;

    public FileIterableInputProvider(File file, int startPosition) {
        this.file = file;
        this.startPosition = startPosition;
    }

    @Override
    public int getNext() throws IOException {
        if (input == null) {
            input = new RandomAccessFile(file, "r");
            input.seek(startPosition * intSize);
        }

        return input.readInt();
    }

    @Override
    public void close() throws IOException {
        if (input != null) {
            input.close();
        }
    }
}
